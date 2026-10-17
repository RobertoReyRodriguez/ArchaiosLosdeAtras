package commons.operations.cheats;

import java.util.List;

import commons.Archaios;
import commons.operations.IOperation;
import helpers.MenuHelper;
import people.Archeologist;
import skills.Skill;

/**
 * Truco oculto 97: un arqueólogo aprende las habilidades que se elijan, sin subir de nivel.
 * Solo se ofrecen las que puede aprender, para que se sigan cumpliendo las dependencias
 * y las incompatibilidades.
 */
public class SkillCheat implements IOperation {

	/** La partida. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida.
	 */
	public SkillCheat(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Truco: habilidades";
	}

	/**
	 * Deja elegir habilidades una tras otra hasta que se elige 0 o no quedan.
	 */
	@Override
	public void operation() {
		System.out.println("Arqueólogos:");
		Archeologist archeologist = MenuHelper.choose(archaios.getArcheologists(), Archeologist::toString, "Elige un arqueólogo: ");
		if (archeologist == null) {
			return;
		}
		List<Skill> learnable = archeologist.getLearnableSkills();
		while (!learnable.isEmpty()) {
			System.out.println();
			System.out.println("Habilidades que puede aprender " + archeologist.getName() + ":");
			Skill skill = MenuHelper.choose(learnable, s -> s.getName() + " | " + s.getDescription(), "Elige una habilidad: ");
			if (skill == null) {
				return;
			}
			archeologist.addSkill(skill);
			System.out.println(archeologist.getName() + " ha aprendido " + skill.getName() + ".");
			learnable = archeologist.getLearnableSkills();
		}
		System.out.println("No quedan habilidades que pueda aprender.");
	}
}
