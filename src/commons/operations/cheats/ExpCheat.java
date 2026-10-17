package commons.operations.cheats;

import commons.Archaios;
import commons.operations.IOperation;
import helpers.InputHelper;
import helpers.MenuHelper;
import people.Archeologist;

/**
 * Truco oculto 98: da experiencia a un arqueólogo. Usa el mismo método que la excavación,
 * así que al subir de nivel se elige la habilidad y, al llegar al 5, la especialización.
 */
public class ExpCheat implements IOperation {

	/** La experiencia máxima que se puede dar de una vez. */
	private static final int MAX_EXP = 100000;

	/** La partida. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida.
	 */
	public ExpCheat(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Truco: experiencia";
	}

	@Override
	public void operation() {
		System.out.println("Arqueólogos:");
		Archeologist archeologist = MenuHelper.choose(archaios.getArcheologists(), Archeologist::toString, "Elige un arqueólogo: ");
		if (archeologist == null) {
			return;
		}
		if (archeologist.isMaxLevel()) {
			System.out.println("Está a nivel máximo: la experiencia aumenta sus bonos de especialización.");
		} else {
			System.out.println("Exp: " + archeologist.getExp() + "/" + archeologist.getExpToLevelUp());
		}
		int amount = InputHelper.readInt("Experiencia a sumar (1-" + MAX_EXP + "): ", 1, MAX_EXP);
		archeologist.addExp(amount);
		System.out.println(archeologist + " tiene ahora " + archeologist.getExp() + " de experiencia.");
	}
}
