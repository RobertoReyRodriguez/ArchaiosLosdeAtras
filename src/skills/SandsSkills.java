package skills;

import commons.repositories.SkillRepository;
import excavation.EGround;

/**
 * Las habilidades que añade la expansión Imperios de las arenas: las que facilitan la
 * excavación en sus terrenos difíciles.
 */
public final class SandsSkills {

	private SandsSkills() {
	}

	/**
	 * Registra las habilidades de la expansión. 
	 */
	public static void register() {
		SkillRepository repository = SkillRepository.getInstance();
		repository.register(new GroundSkill("Especialización en desierto",
				"Fija en 1 el coste de excavar la arena. Incompatible con otras especializaciones en un terreno.")
				.cost(EGround.SAND, 1));
		repository.register(new GroundSkill("Especialista en demoliciones A",
				"Rompe hasta 5 piedras al azar antes de empezar la excavación, pasando sus casillas a tierra.")
				.changes(EGround.STONE, 5));
	}
}
