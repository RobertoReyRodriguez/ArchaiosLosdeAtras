package commons.operations;

import commons.Archaios;
import commons.repositories.RegionRepository;
import excavation.ETerrain;
import helpers.InputHelper;
import helpers.MenuHelper;
import helpers.TextHelper;
import helpers.UtilsHelper;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import people.Archeologist;
import regions.Region;

/**
 * Opción "Licencias" del menú principal: muestra las licencias de cada arqueólogo, es decir,
 * las formas de terreno que puede excavar y las regiones difíciles que tiene desbloqueadas,
 * y permite comprar licencias de región.
 */
public class LicensesOperation implements IOperation {

	/** Las opciones del submenú de licencias. */
	private static final List<String> OPTIONS = List.of("Ver licencias", "Comprar licencia");

	/** La partida, para consultar sus arqueólogos. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida en curso.
	 */
	public LicensesOperation(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Licencias";
	}

	@Override
	public void operation() {
		String option = MenuHelper.choose(OPTIONS, o -> o, "Elige una opción: ");
		while (option != null) {
			System.out.println();
			if (option.equals("Ver licencias")) {
				show();
			} else {
				buy();
			}
			System.out.println();
			option = MenuHelper.choose(OPTIONS, o -> o, "Elige una opción: ");
		}
	}

	// --- Ver licencias ---

	/**
	 * Muestra las licencias de todos los arqueólogos.
	 */
	private void show() {
		for (Archeologist archeologist : archaios.getArcheologists()) {
			System.out.println(TextHelper.header(archeologist.toString()));
			Set<String> shapes = new LinkedHashSet<>();
			for (ETerrain terrain : archeologist.getTerrains()) {
				shapes.add(terrain.getShapeName());
			}
			for (String shape : shapes) {
				System.out.println("  -" + UtilsHelper.ucfirst(shape));
			}
			printRegionLicenses(archeologist);
		}
	}

	/**
	 * Muestra las regiones para las que el arqueólogo ha comprado licencia. No muestra nada si no tiene ninguna.
	 * @param archeologist El arqueólogo.
	 */
	private void printRegionLicenses(Archeologist archeologist) {
		if (archeologist.getRegionLicenses().isEmpty()) {
			return;
		}
		System.out.println("Regiones:");
		for (String code : archeologist.getRegionLicenses()) {
			System.out.println("  -" + RegionRepository.get(code).getName());
		}
	}

	// --- Comprar licencia ---

	/**
	 * Pide un arqueólogo y le deja comprar la licencia de una región difícil que todavía no tenga.
	 * Se pide primero el arqueólogo porque el coste depende de las licencias que ya tiene.
	 */
	private void buy() {
		Archeologist archeologist = chooseArcheologist();
		if (archeologist == null) {
			return;
		}
		List<Region> regions = archeologist.getUnlicensedRegions();
		if (regions.isEmpty()) {
			System.out.println(archeologist.getName() + " ya tiene licencia para todas las regiones difíciles.");
			return;
		}
		int cost = archeologist.getRegionLicenseCost();
		System.out.println("Tienes " + archaios.getPoints() + " puntos. La siguiente licencia de "
				+ archeologist.getName() + " cuesta " + cost + " puntos.");
		if (archaios.getPoints() < cost) {
			System.out.println("No tienes puntos suficientes.");
			return;
		}
		System.out.println();
		System.out.println("Regiones difíciles sin licencia:");
		Region region = MenuHelper.choose(regions, Region::getName, "Elige una región: ");
		if (region == null) {
			return;
		}
		if (!InputHelper.readYesNo("¿Quieres comprar la licencia de " + region.getName() + " para "
				+ archeologist.getName() + " por " + cost + " puntos?")) {
			return;
		}
		archaios.spendPoints(cost);
		archeologist.addRegionLicense(region);
		System.out.println("Has comprado la licencia de " + region.getName() + " para " + archeologist.getName()
				+ ". Te quedan " + archaios.getPoints() + " puntos.");
	}

	/**
	 * Muestra los arqueólogos con su nivel para que el usuario elija uno.
	 * @return El arqueólogo elegido, o null si elige volver.
	 */
	private Archeologist chooseArcheologist() {
		System.out.println("Arqueólogos:");
		return MenuHelper.choose(archaios.getArcheologists(), Archeologist::toString, "Elige un arqueólogo: ");
	}
}