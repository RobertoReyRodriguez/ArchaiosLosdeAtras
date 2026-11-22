package commons.repositories;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import skills.BaseSkills;
import skills.ESkillType;
import skills.SandsSkills;
import skills.Skill;

/**
 * Repositorio de todas las habilidades del sistema, guardadas por su nombre.
 * Cada habilidad se crea una única vez, se registra en el preInit y los arqueólogos
 * guardan referencias a estos mismos objetos.
 */
public final class SkillRepository extends Repository<Skill> {

	/** El único repositorio de habilidades. */
	private static final SkillRepository INSTANCE = new SkillRepository();
	/** Orden alfabético en español: tiene en cuenta las tildes. */
	private static final Comparator<Skill> ALPHABETICAL =
			Comparator.comparing(Skill::getName, Collator.getInstance(Locale.forLanguageTag("es")));

	private SkillRepository() {
	}

	/**
	 * @return El repositorio de habilidades.
	 */
	public static SkillRepository getInstance() {
		return INSTANCE;
	}

	/**
	 * Registra las habilidades base y las de la expansión Imperios de las arenas.
	 */
	@Override
	public void populate() {
		BaseSkills.register();
		SandsSkills.register();
	}

	@Override
	protected String getKey(Skill skill) {
		return skill.getName();
	}

	/**
	 * @param type El tipo de habilidad.
	 * @return Las habilidades de ese tipo, por orden alfabético.
	 */
	public List<Skill> getAll(ESkillType type) {
		List<Skill> result = new ArrayList<>();
		for (Skill skill : getAll()) {
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
	public List<ESkillType> getTypes() {
		List<ESkillType> result = new ArrayList<>();
		for (ESkillType type : ESkillType.values()) {
			if (!getAll(type).isEmpty()) {
				result.add(type);
			}
		}
		return result;
	}
}
