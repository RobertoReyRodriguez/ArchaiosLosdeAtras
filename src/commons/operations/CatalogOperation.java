package commons.operations;

import commons.repositories.RegionRepository;
import helpers.MenuHelper;
import helpers.TreasureHelper;
import java.util.List;
import regions.Region;
import treasures.Treasure;

/**
 * Opción "Catálogo de tesoros" del menú principal: la información individual de cada tesoro.
 */
public class CatalogOperation implements IOperation {

	@Override
	public String getName() {
		return "Catálogo de tesoros";
	}

	@Override
	public void operation() {
		boolean back = false;
		while (!back) {
			Region region = chooseRegion();
			if (region == null) {
				back = true;
			} else {
				back = showTreasures(region);
			}
		}
	}

	/**
	 * Muestra las regiones para que el usuario elija una.
	 * @return La región elegida, o null si elige volver al menú.
	 */
	private Region chooseRegion() {
		System.out.println("Regiones:");
		return MenuHelper.choose(RegionRepository.getAll(), Region::getName, "Elige una región: ", "Volver al menú");
	}

	/**
	 * Deja consultar los tesoros de una región uno a uno.
	 * @param region La región.
	 * @return true si el usuario quiere volver al menú principal;
	 * false si vuelve atrás desde la lista de tesoros para elegir otra región.
	 */
	private boolean showTreasures(Region region) {
		while (true) {
			System.out.println();
			System.out.println("Tesoros de " + region.getName() + ":");
			Treasure treasure = MenuHelper.choose(region.getTreasures(), Treasure::getName, "Elige un tesoro: ");
			if (treasure == null) {
				System.out.println();
				return false;
			}
			System.out.println();
			System.out.println(TreasureHelper.getDetail(treasure));
			System.out.println();
			String again = "Consultar otro tesoro de " + region.getName();
			if (MenuHelper.choose(List.of(again), option -> option, "Elige una opción: ", "Volver al menú") == null) {
				return true;
			}
		}
	}
}
