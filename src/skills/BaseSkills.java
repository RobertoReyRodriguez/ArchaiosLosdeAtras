package skills;

import commons.repositories.SkillRepository;
import excavation.ETerrain;
import treasures.ETreasureRarity;

/**
 * Las habilidades de Archaios.
 * 
 */
public final class BaseSkills {

	private BaseSkills() {
	}

	/**
	 * Crea y registra todas las habilidades base con sus dependencias e incompatibilidades.
	 * Solo debe llamarse una vez, desde el preInit.
	 */
	public static void register() {
		registerTreasureSkills();
		registerExcavationSkills();
		registerTerrainSkills();
	}

	/**
	 * Tesoros.
	 */
	private static void registerTreasureSkills() {
		Skill extra1 = SkillRepository.register(new TreasureSkill("Extra I",
				"Aumenta en 3 el número de tesoros de la excavación.").extraRandom(3));
		Skill extra2 = SkillRepository.register(new TreasureSkill("Extra II",
				"Aumenta en 7 el número de tesoros de la excavación.").extraRandom(7));
		Skill extra3 = SkillRepository.register(new TreasureSkill("Extra III",
				"Aumenta en 10 el número de tesoros de la excavación.").extraRandom(10));
		extra2.addDependency(extra1);
		extra3.addDependency(extra2);

		Skill apprentice = SkillRepository.register(new TreasureSkill("Aprendiz",
				"Aumenta el número de tesoros posibles de la excavación en 5 comunes.")
				.extra(ETreasureRarity.COMMON, 5));
		Skill expert = SkillRepository.register(new TreasureSkill("Experto",
				"Aumenta el número de tesoros posibles de la excavación en 5 comunes y 5 infrecuentes.")
				.extra(ETreasureRarity.COMMON, 5).extra(ETreasureRarity.UNCOMMON, 5));
		Skill master = SkillRepository.register(new TreasureSkill("Maestro",
				"Aumenta el número de tesoros posibles de la excavación en 5 infrecuentes y 2 raros.")
				.extra(ETreasureRarity.UNCOMMON, 5).extra(ETreasureRarity.RARE, 2));
		expert.addDependency(apprentice);
		master.addDependency(expert);

		Skill specialistU = SkillRepository.register(new TreasureSkill("Especialista U",
				"Aumenta en un 10% la probabilidad de encontrar tesoros infrecuentes.")
				.probability(ETreasureRarity.UNCOMMON, 10));
		Skill specialistR = SkillRepository.register(new TreasureSkill("Especialista R",
				"Aumenta en un 10% la probabilidad de encontrar tesoros raros.")
				.probability(ETreasureRarity.RARE, 10));
		Skill specialistM = SkillRepository.register(new TreasureSkill("Especialista M",
				"Aumenta en un 5% la probabilidad de encontrar tesoros infrecuentes y en un 5% la de raros.")
				.probability(ETreasureRarity.UNCOMMON, 5).probability(ETreasureRarity.RARE, 5));
		Skill.setIncompatible(specialistU, specialistR);
		Skill.setIncompatible(specialistU, specialistM);
		Skill.setIncompatible(specialistR, specialistM);
	}

	/**
	 *  Excavación.
	 */
	private static void registerExcavationSkills() {
		Skill excavation1 = SkillRepository.register(new ExcavationSkill("Excavación I",
				"Aumenta en 5 el número de acciones de la excavación.").extraActions(5));
		Skill excavation2 = SkillRepository.register(new ExcavationSkill("Excavación II",
				"Aumenta en 10 el número de acciones de la excavación.").extraActions(10));
		Skill excavation3 = SkillRepository.register(new ExcavationSkill("Excavación III",
				"Aumenta en 15 el número de acciones de la excavación.").extraActions(15));
		excavation2.addDependency(excavation1);
		excavation3.addDependency(excavation2);

		Skill experience1 = SkillRepository.register(new ExcavationSkill("Experiencia I",
				"Aumenta en un 20% el número de acciones de la excavación.").actionsPercent(20));
		Skill experience2 = SkillRepository.register(new ExcavationSkill("Experiencia II",
				"Aumenta en un 30% el número de acciones de la excavación.").actionsPercent(30));
		experience2.addDependency(experience1);

		Skill cleaning1 = SkillRepository.register(new ExcavationSkill("Limpieza I",
				"Elimina 5 casillas sin tesoro antes de empezar la excavación.").cleanedCells(5));
		Skill cleaning2 = SkillRepository.register(new ExcavationSkill("Limpieza II",
				"Elimina 10 casillas sin tesoro antes de empezar la excavación.").cleanedCells(10));
		cleaning2.addDependency(cleaning1);

		for (String letter : new String[] { "A", "B", "C", "D" }) {
			SkillRepository.register(new ExcavationSkill("Escaneo " + letter,
					"Marca con '?' tres casillas: en una hay un tesoro y en las otras dos nada.").guesses(1));
		}

		SkillRepository.register(new ExcavationSkill("Investigación",
				"Muestra en cada ronda cuántos tesoros de cada rareza hay en la rejilla.").investigation());
	}

	/**
	 *Terreno.
	 */
	private static void registerTerrainSkills() {
		Skill licenseA = SkillRepository.register(new TerrainSkill("Licencia A",
				"Permite excavar terrenos con forma de fila y de columna.",
				ETerrain.SMALL_ROW, ETerrain.MEDIUM_ROW, ETerrain.LARGE_ROW,
				ETerrain.SMALL_COLUMN, ETerrain.MEDIUM_COLUMN, ETerrain.LARGE_COLUMN));
		Skill licenseB = SkillRepository.register(new TerrainSkill("Licencia B",
				"Permite excavar terrenos con forma horizontal y vertical.",
				ETerrain.SMALL_HORIZONTAL, ETerrain.MEDIUM_HORIZONTAL, ETerrain.LARGE_HORIZONTAL,
				ETerrain.SMALL_VERTICAL, ETerrain.MEDIUM_VERTICAL, ETerrain.LARGE_VERTICAL));
		Skill.setIncompatible(licenseA, licenseB);
	}
}
