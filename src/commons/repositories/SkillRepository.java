package commons.repositories;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import skills.ESkillType;
import skills.Skill;

/**
 * Repositorio de todas las habilidades del sistema.
 * Cada habilidad se crea una única vez, se registra en el preInit y los arqueólogos
 * guardan referencias a estos mismos objetos.
 */
public final class SkillRepository {

	/** Las habilidades por su nombre, en el orden en que se registran. */
	private static final Map<String, Skill> SKILLS = new LinkedHashMap<>();
	/** Orden alfabético en español: tiene en cuenta las tildes. */
	private static final Comparator<Skill> ALPHABETICAL =
			Comparator.comparing(Skill::getName, Collator.getInstance(Locale.forLanguageTag("es")));

	private SkillRepository() {
	}

	/**
	 * Registra una habilidad. Solo debe llamarse desde el preInit.
	 * @param skill La habilidad a registrar.
	 * @return La propia habilidad, para poder guardarla y añadirle restricciones.
	 * @throws IllegalArgumentException Si ya hay una habilidad registrada con ese nombre.
	 */
	public static <T extends Skill> T register(T skill) {
		if (SKILLS.containsKey(skill.getName())) {
			throw new IllegalArgumentException("La habilidad " + skill.getName() + " ya está registrada.");
		}
		SKILLS.put(skill.getName(), skill);
		return skill;
	}

	/**
	 * @param name El nombre de la habilidad.
	 * @return La habilidad, o null si no está registrada.
	 */
	public static Skill get(String name) {
		return SKILLS.get(name);
	}

	/**
	 * @return Todas las habilidades, en el orden en que se registraron.
	 */
	public static List<Skill> getAll() {
		return new ArrayList<>(SKILLS.values());
	}

	/**
	 * @param type El tipo de habilidad.
	 * @return Las habilidades de ese tipo, por orden alfabético.
	 */
	public static List<Skill> getAll(ESkillType type) {
		List<Skill> result = new ArrayList<>();
		for (Skill skill : SKILLS.values()) {
			if (skill.getType() == type) {
				result.add(skill);
			}
		}
		result.sort(ALPHABETICAL);
		return result;
	}

	/**
	 * @return Los tipos que tienen al menos una habilidad registrada, en el orden del enumerado.
	 */
	public static List<ESkillType> getTypes() {
		List<ESkillType> result = new ArrayList<>();
		for (ESkillType type : ESkillType.values()) {
			if (!getAll(type).isEmpty()) {
				result.add(type);
			}
		}
		return result;
	}
}
