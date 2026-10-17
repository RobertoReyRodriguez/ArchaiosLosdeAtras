package stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import commons.operations.IOperation;
import commons.repositories.RegionRepository;
import commons.repositories.TreasureRepository;
import helpers.CodeHelper;
import helpers.MenuHelper;
import helpers.TextHelper;
import helpers.UtilsHelper;
import regions.Region;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Estadísticas de la partida y opción "Estadísticas" del menú principal:
 * estadísticas de lugares y de tesoros (por región o por tipo).
 */
public class Stats implements IOperation {

	/** Las opciones del primer menú de estadísticas. */
	private static final List<String> OPTIONS = List.of("Lugares", "Tesoros");
	/** Las opciones del menú de estadísticas de tesoros. */
	private static final List<String> TREASURE_OPTIONS = List.of("Por región", "Por tipo");
	/** La sangría de los códigos de las partes. */
	private static final String PART_INDENT = "    ";

	/** Veces que se ha encontrado cada tesoro, por código completo y en orden alfabético. */
	private Map<String, Integer> treasures = new TreeMap<>();
	/** Veces que se ha excavado en cada región, por código de región. */
	private Map<String, Integer> places = new TreeMap<>();

	/**
	 * Apunta que se ha encontrado un tesoro.
	 * @param code El código completo del tesoro encontrado. 
	 */
	public void registerTreasure(String code) {
		treasures.merge(code, 1, Integer::sum);
	}

	/**
	 * Apunta que se ha excavado en una región.
	 * @param regionCode El código de la región. 
	 */
	public void registerPlace(String regionCode) {
		places.merge(regionCode, 1, Integer::sum);
	}

	/**
	 * @return Las veces que se ha encontrado cada tesoro. No se puede modificar.
	 */
	public Map<String, Integer> getTreasures() {
		return Collections.unmodifiableMap(treasures);
	}

	/**
	 * @return Las veces que se ha excavado en cada región. No se puede modificar.
	 */
	public Map<String, Integer> getPlaces() {
		return Collections.unmodifiableMap(places);
	}

	@Override
	public String getName() {
		return "Estadísticas";
	}

	/**
	 * Deja elegir entre estadísticas de lugares o de tesoros. Tras ver una, vuelve a este menú.
	 */
	@Override
	public void operation() {
		String option = MenuHelper.choose(OPTIONS, o -> o, "Elige una opción: ");
		while (option != null) {
			System.out.println();
			if (option.equals("Lugares")) {
				showPlaces();
			} else {
				chooseTreasureStats();
			}
			System.out.println();
			option = MenuHelper.choose(OPTIONS, o -> o, "Elige una opción: ");
		}
	}

	// --- Lugares ---

	/**
	 * Muestra, por cada región excavada, las veces que se ha visitado, los tesoros distintos
	 * encontrados frente al total con su porcentaje y el total de tesoros excavados.
	 */
	private void showPlaces() {
		if (places.isEmpty()) {
			System.out.println("Todavía no se ha excavado en ninguna región.");
			return;
		}
		for (Map.Entry<String, Integer> place : places.entrySet()) {
			Region region = RegionRepository.get(place.getKey());
			Set<String> distinct = new HashSet<>();
			int excavated = 0;
			for (Map.Entry<String, Integer> treasure : treasures.entrySet()) {
				if (CodeHelper.getRegion(treasure.getKey()).equals(region.getCode())) {
					distinct.add(CodeHelper.getFullTreasure(treasure.getKey()));
					excavated += treasure.getValue();
				}
			}
			int total = region.getTreasures().size();
			System.out.println(TextHelper.header(region.getName()));
			System.out.println("Veces visitada: " + place.getValue());
			System.out.println("Tesoros encontrados: " + distinct.size() + "/" + total
					+ " (" + UtilsHelper.getPercent(distinct.size(), total) + "%)");
			System.out.println("Total de tesoros excavados: " + excavated);
		}
	}

	// --- Tesoros ---

	/**
	 * Deja elegir cómo ver los tesoros encontrados: por región o por tipo.
	 */
	private void chooseTreasureStats() {
		String option = MenuHelper.choose(TREASURE_OPTIONS, o -> o, "Elige una opción: ");
		if (option == null) {
			return;
		}
		System.out.println();
		if (treasures.isEmpty()) {
			System.out.println("Todavía no se ha encontrado ningún tesoro.");
		} else if (option.equals("Por región")) {
			showByRegion();
		} else {
			showByType();
		}
	}

	/**
	 * Muestra los tesoros encontrados divididos por región, por orden alfabético de código.
	 */
	private void showByRegion() {
		for (Region region : RegionRepository.getAll()) {
			List<String> codes = new ArrayList<>();
			for (String code : treasures.keySet()) {
				if (CodeHelper.getRegion(code).equals(region.getCode())) {
					codes.add(code);
				}
			}
			printGroup(region.getName(), codes);
		}
	}

	/**
	 * Muestra los tesoros encontrados divididos por tipo y luego por orden alfabético de código.
	 */
	private void showByType() {
		for (ETreasureType type : ETreasureType.values()) {
			List<String> codes = new ArrayList<>();
			for (String code : treasures.keySet()) {
				if (TreasureRepository.get(code).getType() == type) {
					codes.add(code);
				}
			}
			printGroup(UtilsHelper.ucfirst(type.toString()), codes);
		}
	}

	/**
	 * Muestra un grupo de tesoros encontrados con una cabecera. 
	 * No muestra nada si el grupo está vacío.
	 * @param title El título de la cabecera.
	 * @param codes Los códigos completos de las partes encontradas, por orden alfabético.
	 */
	private void printGroup(String title, List<String> codes) {
		if (codes.isEmpty()) {
			return;
		}
		System.out.println(TextHelper.header(title));
		String previous = null;
		for (String code : codes) {
			Treasure treasure = TreasureRepository.get(code);
			if (treasure.getParts() == 1) {
				System.out.println("-" + treasure.getName() + ": " + treasures.get(code));
			} else {
				if (!treasure.getCode().equals(previous)) {
					System.out.println("-" + treasure.getName() + ":");
				}
				System.out.println(PART_INDENT + code + ": " + treasures.get(code));
			}
			previous = treasure.getCode();
		}
	}
}
