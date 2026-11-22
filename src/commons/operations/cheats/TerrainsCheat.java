package commons.operations.cheats;

import commons.Archaios;
import commons.operations.IOperation;
import excavation.ETerrain;
import helpers.MenuHelper;
import people.Archeologist;

/**
 * Truco oculto 96: un arqueólogo puede excavar los 15 terrenos. Se le desbloquean todos
 * y, si no llega al nivel de los grandes, recibe la experiencia justa para alcanzarlo
 * (eligiendo una habilidad por cada nivel, como en el juego).
 */
public class TerrainsCheat implements IOperation {

	/** La partida. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida.
	 */
	public TerrainsCheat(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Truco: todos los terrenos";
	}

	@Override
	public void operation() {
		System.out.println("Arqueólogos:");
		Archeologist archeologist = MenuHelper.choose(archaios.getArcheologists(), Archeologist::toString, "Elige un arqueólogo: ");
		if (archeologist == null) {
			return;
		}
		for (ETerrain terrain : ETerrain.values()) {
			archeologist.addTerrain(terrain);
		}
		int largeLevel = ETerrain.LARGE_SQUARE.getMinLevel();
		int needed = getExpToLevel(archeologist, largeLevel);
		if (needed > 0) {
			System.out.println("Para los terrenos grandes necesita nivel " + largeLevel
					+ ": recibe " + needed + " de experiencia.");
			archeologist.addExp(needed);
		}
		System.out.println(archeologist + " puede excavar " + archeologist.getAvailableTerrains().size() + " terrenos.");
	}

	/**
	 * Calcula la experiencia que le falta a un arqueólogo para llegar a un nivel.
	 * @param archeologist El arqueólogo.
	 * @param level El nivel buscado.
	 * @return La experiencia que le falta, o 0 si ya lo tiene.
	 */
	private int getExpToLevel(Archeologist archeologist, int level) {
		int needed = -archeologist.getExp();
		for (int current = archeologist.getLevel(); current < level; current++) {
			needed += 50 + 50 * current;
		}
		return Math.max(0, needed);
	}
}
