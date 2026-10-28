package commons.operations;

import commons.Archaios;
import excavation.ETerrain;
import helpers.TextHelper;
import helpers.UtilsHelper;
import java.util.LinkedHashSet;
import java.util.Set;
import people.Archeologist;

/**
 * Opción "Licencias" del menú principal: muestra las licencias de cada arqueólogo,
 * es decir, las formas de terreno que puede excavar.
 */
public class LicensesOperation implements IOperation {

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
		for (Archeologist archeologist : archaios.getArcheologists()) {
			System.out.println(TextHelper.header(archeologist.toString()));
			Set<String> shapes = new LinkedHashSet<>();
			for (ETerrain terrain : archeologist.getTerrains()) {
				shapes.add(terrain.getShapeName());
			}
			for (String shape : shapes) {
				System.out.println("  -" + UtilsHelper.ucfirst(shape));
			}
		}
	}
}
