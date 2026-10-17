package commons.operations.cheats;

import java.util.Arrays;

import commons.Archaios;
import commons.operations.IOperation;
import helpers.InputHelper;
import helpers.MenuHelper;
import helpers.UtilsHelper;
import people.Archeologist;
import skills.Skill;
import treasures.ETreasureRarity;

/**
 * Truco oculto 95: contrata gratis un arqueólogo de la rareza que se elija, para probar
 * los infrecuentes y raros sin depender del azar.
 */
public class HireCheat implements IOperation {

	/** La partida. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida.
	 */
	public HireCheat(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Truco: contratar gratis";
	}

	@Override
	public void operation() {
		System.out.println("Rarezas:");
		ETreasureRarity rarity = MenuHelper.choose(Arrays.asList(ETreasureRarity.values()),
				r -> UtilsHelper.ucfirst(r.toString()), "Elige una rareza: ");
		if (rarity == null) {
			return;
		}
		Archeologist archeologist = new Archeologist("", rarity);
		archeologist.addRandomSkills(archeologist.getInitialSkills());
		System.out.println("Arqueólogo " + rarity + " con " + archeologist.getSkills().size() + " habilidades:");
		for (Skill skill : archeologist.getSkills()) {
			System.out.println("  -" + skill.getName());
		}
		archeologist.setName(InputHelper.readString("Nombre del arqueólogo: "));
		archaios.getArcheologists().add(archeologist);
		System.out.println(archeologist.getName() + " se ha unido al equipo.");
	}
}
