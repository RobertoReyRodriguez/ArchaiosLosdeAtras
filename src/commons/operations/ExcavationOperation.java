package commons.operations;

import java.util.List;

import commons.Archaios;
import commons.repositories.RegionRepository;
import excavation.Excavation;
import excavation.ETerrain;
import helpers.MenuHelper;
import helpers.TextHelper;
import helpers.TreasureHelper;
import messages.ResponseWrapper;
import people.Archeologist;
import regions.Region;
import treasures.Treasure;

/**
 * Opción "Excavar" del menú principal.
 */
public class ExcavationOperation implements IOperation {

	/** Separador entre las partes del resumen final. */
	private static final String SEPARATOR = "-".repeat(40);

	/** La partida, para consultar y modificar sus datos. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida en curso.
	 */
	public ExcavationOperation(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Excavar";
	}

	/**
	 * Pide región, arqueólogo y terreno, en ese orden. La opción 0 de cada lista vuelve a la anterior.
	 */
	@Override
	public void operation() {
		Region region = chooseRegion();
		while (region != null) {
			Archeologist archeologist = chooseArcheologist();
			while (archeologist != null) {
				ETerrain terrain = chooseTerrain(archeologist);
				if (terrain != null) {
					Excavation excavation = new Excavation(region, archeologist, terrain);
					collect(excavation, excavation.excavate());
					return;
				}
				archeologist = chooseArcheologist();
			}
			region = chooseRegion();
		}
	}

	/**
	 * @return La región elegida, o null si el usuario vuelve al menú.
	 */
	private Region chooseRegion() {
		System.out.println();
		System.out.println("Regiones:");
		return MenuHelper.choose(RegionRepository.getAll(), Region::getName, "Elige una región: ", "Volver al menú");
	}

	/**
	 * @return El arqueólogo elegido, mostrado con su nombre y nivel, o null si el usuario vuelve atrás.
	 */
	private Archeologist chooseArcheologist() {
		System.out.println();
		System.out.println("Arqueólogos:");
		return MenuHelper.choose(archaios.getArcheologists(), Archeologist::toString, "Elige un arqueólogo: ");
	}

	/**
	 * @param archeologist El arqueólogo que va a excavar.
	 * @return El terreno elegido entre los que puede excavar, o null si el usuario vuelve atrás.
	 */
	private ETerrain chooseTerrain(Archeologist archeologist) {
		System.out.println();
		System.out.println("Terrenos:");
		return MenuHelper.choose(archeologist.getAvailableTerrains(), ETerrain::toString, "Elige un terreno: ");
	}

	/**
	 * Recoge lo que devuelve la excavación: muestra los tesoros encontrados, los registra
	 * en las estadísticas, los envía al Museo y reparte los puntos y la experiencia.
	 * @param excavation La excavación terminada.
	 * @param codes Los códigos completos de los tesoros encontrados.
	 */
	private void collect(Excavation excavation, List<String> codes) {
		Region region = excavation.getRegion();
		Archeologist archeologist = excavation.getArcheologist();
		archaios.getStats().registerPlace(region.getCode());

		System.out.println(TextHelper.header("Tesoros encontrados"));
		int points = 0;
		if (codes.isEmpty()) {
			System.out.println("No se ha encontrado ningún tesoro.");
		}
		for (int i = 0; i < codes.size(); i++) {
			Treasure treasure = region.getTreasure(codes.get(i));
			if (i > 0) {
				System.out.println();
			}
			System.out.println(TreasureHelper.getFoundDetail(treasure, codes.get(i)));
			archaios.getStats().registerTreasure(codes.get(i));
			points += treasure.getPoints();
		}

		System.out.println(SEPARATOR);
		if (codes.isEmpty()) {
			System.out.println("No hay tesoros que enviar al Museo.");
		} else {
			ResponseWrapper<String> response = archaios.getZeraf().sendData(codes);
			System.out.println(response.getCode().getMessage());
		}
		System.out.println(SEPARATOR);

		// Si se termina antes de tiempo no se da el extra del tamaño, para que no se pueda
		// subir de nivel empezando y dejando excavaciones sin excavar nada.
		int exp = points + (excavation.isAbandoned() ? 0 : excavation.getTerrain().getExtraExp());
		archaios.addPoints(points);
		System.out.println("Puntos conseguidos: " + points);
		System.out.println("El arqueólogo " + archeologist.getName() + " ha conseguido " + exp + " puntos de experiencia.");
		archeologist.addExp(exp);
	}
}
