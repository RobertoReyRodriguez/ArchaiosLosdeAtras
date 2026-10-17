package regions;

import java.util.List;

import commons.operations.IOperation;
import commons.repositories.RegionRepository;
import helpers.MenuHelper;
import helpers.TextHelper;
import helpers.TreasureHelper;
import treasures.Treasure;

/**
 * Opción "Atlas" del menú principal: información descriptiva de las regiones
 * con todos sus tesoros posibles, su nombre, tipo y rareza.
 */
public class Atlas implements IOperation {

	@Override
	public String getName() {
		return "Atlas";
	}

	@Override
	public void operation() {
		Region region = chooseRegion();
		while (region != null) {
			System.out.println();
			showRegion(region);
			System.out.println();
			if (askAgain()) {
				System.out.println();
				region = chooseRegion();
			} else {
				region = null;
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
	 * Muestra todos los tesoros de una región, con el nombre alineado en una columna.
	 * @param region La región.
	 */
	private void showRegion(Region region) {
		List<Treasure> treasures = region.getTreasures();
		int width = 0;
		for (Treasure treasure : treasures) {
			width = Math.max(width, treasure.getName().length());
		}

		System.out.println(TextHelper.header(region.getName()));
		for (Treasure treasure : treasures) {
			System.out.println("  " + TextHelper.padRight(treasure.getName(), width) + "   "
					+ TreasureHelper.getTypeAndRarity(treasure));
		}
	}

	/**
	 * Pregunta al usuario si quiere volver al atlas o al menú.
	 * @return true si quiere volver al atlas.
	 */
	private boolean askAgain() {
		return MenuHelper.choose(List.of("Volver al atlas"), option -> option, "Elige una opción: ", "Volver al menú") != null;
	}
}
