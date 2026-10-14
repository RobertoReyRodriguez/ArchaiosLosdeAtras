package people;

import java.util.List;

import excavation.Terrain;
import skills.Skill;

import java.util.ArrayList;
import java.util.Collections;
import regions.Region;
import skills.Skill;
import excavation.ETerrainSize;
import excavation.Terrain;
import treasures.ETreasureRarity;
import treasures.ETreasureType;

/**
 * Arqueólogo del sistema.
 * Cada arqueólogo tiene un nivel (0-5), una experiencia, una rareza, una lista
 * de terrenos que puede excavar y una lista de habilidades Skill.
 * Los bonos se obtienen recorriendo las habilidades y, si el arqueólogo ha
 * alcanzado el nivel máximo, sumando además los bonos de su especialización.
 */
public class Archeologist {

	/** Nivel máximo que puede alcanzar un arqueólogo. */
	public static final int MAX_LEVEL = 5;

	/** Experiencia base necesaria para subir de nivel. */
	public static final int BASE_EXPERIENCE = 50;

	/** Experiencia adicional necesaria por cada nivel que ya se tiene. */
	public static final int EXPERIENCE_PER_LEVEL = 50;

	/** Coste en puntos de contratar un arqueólogo. */
	public static final int HIRE_COST = 50;

	/** Puntos que se obtienen al licenciar por cada nivel (sin contar el 0). */
	public static final int DISMISS_POINTS_PER_LEVEL = 10;

	/** Nivel desde el que se desbloquean los terrenos medianos. */
	public static final int MEDIUM_TERRAIN_LEVEL = 2;

	/** Nivel desde el que se desbloquean los terrenos grandes. */
	public static final int LARGE_TERRAIN_LEVEL = 4;

	/** Experiencia extra por cada acción adicional de la especialización. */
	private static final int XP_PER_SPECIALIZATION_ACTION = 25;

	/**
	 * Experiencia extra por cada tesoro aleatorio adicional de la especialización.
	 */
	private static final int XP_PER_SPECIALIZATION_RANDOM = 50;

	/** Experiencia extra por cada tesoro común adicional de la especialización. */
	private static final int XP_PER_SPECIALIZATION_COMMON = 50;

	/**
	 * Experiencia extra por cada tesoro infrecuente adicional de la
	 * especialización.
	 */
	private static final int XP_PER_SPECIALIZATION_UNCOMMON = 100;

	/** Experiencia extra por cada tesoro raro adicional de la especialización. */
	private static final int XP_PER_SPECIALIZATION_RARE = 200;

	/** Nombre del arqueólogo. */
	private String name;

	/** Rareza del arqueólogo. */
	private final EArcheologistRarity rarity;

	/** Nivel actual, de 0 a MAX_LEVEL. */
	private int level;

	/**
	 * Experiencia acumulada en el nivel actual. Al llegar al nivel máximo pasa a
	 * ser la experiencia obtenida a mayores, que alimenta la especialización.
	 */
	private int experience;

	/** Región de especialización, o null si todavía no tiene. */
	private Region specialization;

	/** Terrenos que el arqueólogo puede excavar. */
	private final List<Terrain> terrains;

	/** Habilidades que posee el arqueólogo. */
	private final List<Skill> skills;

	/**
	 * Crea un arqueólogo de nivel 0, sin experiencia, sin habilidades y con acceso
	 * únicamente a terrenos cuadrados pequeños. Las habilidades iniciales se
	 * añaden después con addSkill(Skill).
	 *
	 * @param rarity La rareza del arqueólogo.
	 */
	public Archeologist(EArcheologistRarity rarity) {
		this.rarity = rarity;
		this.level = 0;
		this.experience = 0;
		this.specialization = null;
		this.terrains = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.terrains.add(Terrain.square(ETerrainSize.SMALL));
	}

	/**
	 * Calcula la experiencia necesaria para subir desde el nivel indicado: 50 más
	 * 50 por cada nivel.
	 *
	 * @param level El nivel de partida.
	 * @return La experiencia necesaria para subir al siguiente nivel.
	 */
	public static int experienceToLevelUp(int level) {
		return BASE_EXPERIENCE + EXPERIENCE_PER_LEVEL * level;
	}

	/**
	 * Obtiene la experiencia necesaria para subir desde el nivel actual.
	 *
	 * @return La experiencia necesaria, o 0 si ya está en el nivel máximo.
	 */
	public int getExperienceToNextLevel() {
		return isMaxLevel() ? 0 : experienceToLevelUp(level);
	}

	/**
	 * Añade experiencia y sube de nivel todas las veces que corresponda, sobrando
	 * la experiencia restante. Al subir se desbloquean los terrenos cuadrados
	 * medianos (nivel 2) y grandes (nivel 4).
	 *
	 * @param points Los puntos de experiencia a añadir (no negativos).
	 * @return El número de niveles que ha subido.
	 */
	public int addExperience(int points) {
		if (points < 0) {
			return 0;
		}
		experience += points;
		int gained = 0;
		while (!isMaxLevel() && experience >= experienceToLevelUp(level)) {
			experience -= experienceToLevelUp(level);
			level++;
			gained++;
			unlockTerrainsForLevel();
		}
		return gained;
	}

	/**
	 * Desbloquea el terreno cuadrado correspondiente al nivel actual, si procede.
	 */
	private void unlockTerrainsForLevel() {
		if (level == MEDIUM_TERRAIN_LEVEL) {
			addTerrain(Terrain.square(ETerrainSize.MEDIUM));
		} else if (level == LARGE_TERRAIN_LEVEL) {
			addTerrain(Terrain.square(ETerrainSize.LARGE));
		}
	}

	/**
	 * Indica si el arqueólogo está en el nivel máximo.
	 *
	 * @return true si el nivel es MAX_LEVEL.
	 */
	public boolean isMaxLevel() {
		return level >= MAX_LEVEL;
	}

	/**
	 * Indica si el arqueólogo ha llegado al nivel máximo y aún no se ha
	 * especializado.
	 *
	 * @return true si debe elegirse su especialización.
	 */
	public boolean needsSpecialization() {
		return isMaxLevel() && specialization == null;
	}

	/**
	 * Especializa al arqueólogo en una región. Solo es posible en el nivel máximo
	 * y una única vez.
	 *
	 * @param region La región de especialización.
	 * @return true si se ha especializado; false si no está en el nivel máximo,
	 *         ya estaba especializado o la región es null.
	 */
	public boolean specialize(Region region) {
		if (region == null || !needsSpecialization()) {
			return false;
		}
		this.specialization = region;
		return true;
	}

	/**
	 * Obtiene la experiencia obtenida más allá del nivel máximo.
	 *
	 * @return La experiencia a mayores, o 0 si no está en el nivel máximo.
	 */
	private int extraExperience() {
		return isMaxLevel() ? experience : 0;
	}

	/**
	 * Indica si los bonos de especialización se aplican en la región dada.
	 *
	 * @param region La región en la que se excava.
	 * @return true si el arqueólogo está especializado en esa región.
	 */
	private boolean isSpecializedIn(Region region) {
		return specialization != null && specialization == region;
	}

	/**
	 * Añade una habilidad al arqueólogo. Si es modificadora de terreno, añade
	 * además los terrenos que otorga (sin duplicados).
	 *
	 * @param skill La habilidad obtenida.
	 */
	public void addSkill(Skill skill) {
		skills.add(skill);
		for (Terrain terrain : skill.getTerrainsToAdd()) {
			addTerrain(terrain);
		}
	}

	/**
	 * Añade un terreno a la lista si no estaba ya.
	 *
	 * @param terrain El terreno a añadir.
	 */
	private void addTerrain(Terrain terrain) {
		if (!terrains.contains(terrain)) {
			terrains.add(terrain);
		}
	}

	/**
	 * Comprueba si el arqueólogo dispone de una habilidad.
	 *
	 * @param skillName El nombre de la habilidad.
	 * @return true si la tiene.
	 */
	public boolean hasSkill(String skillName) {
		for (Skill skill : skills) {
			if (skill.getName().equals(skillName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calcula los puntos que se obtienen al licenciar al arqueólogo: 10 por nivel
	 * más 10 si es infrecuente o 20 si es raro.
	 *
	 * @return Los puntos de compensación.
	 */
	public int getDismissPoints() {
		return DISMISS_POINTS_PER_LEVEL * level + rarity.getDismissBonus();
	}

	/**
	 * Obtiene el nombre.
	 *
	 * @return El nombre del arqueólogo.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre (se solicita tras mostrar la rareza al contratar).
	 *
	 * @param name El nuevo nombre.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene la rareza.
	 *
	 * @return La rareza del arqueólogo.
	 */
	public EArcheologistRarity getRarity() {
		return rarity;
	}

	/**
	 * Obtiene el nivel actual.
	 *
	 * @return El nivel, de 0 a MAX_LEVEL.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Obtiene la experiencia acumulada (a mayores del máximo si está en el nivel
	 * máximo).
	 *
	 * @return La experiencia.
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * Obtiene la región de especialización.
	 *
	 * @return La región, o null si no está especializado.
	 */
	public Region getSpecialization() {
		return specialization;
	}

	/**
	 * Obtiene los terrenos que puede excavar.
	 *
	 * @return Una vista no modificable de los terrenos.
	 */
	public List<Terrain> getTerrains() {
		return Collections.unmodifiableList(terrains);
	}

	/**
	 * Obtiene las habilidades del arqueólogo.
	 *
	 * @return Una vista no modificable de las habilidades.
	 */
	public List<Skill> getSkills() {
		return Collections.unmodifiableList(skills);
	}

	/**
	 * Devuelve los datos del arqueólogo para la opción de ver arqueólogos: nivel,
	 * experiencia (frente a la necesaria, o la obtenida a mayores y la
	 * especialización en el nivel máximo), terrenos y habilidades. Los terrenos y
	 * los nombres de habilidad llevan dos espacios de sangría y las descripciones,
	 * tres.
	 *
	 * @return El texto con los datos del arqueólogo.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Nivel: ").append(level);
		if (isMaxLevel()) {
			sb.append("\nEspecialización: ")
					.append(specialization == null ? "ninguna" : specialization);
			sb.append("\nExperiencia a mayores: ").append(experience);
		} else {
			sb.append("\nExperiencia: ").append(experience).append("/").append(getExperienceToNextLevel());
		}

		sb.append("\nTerrenos:");
		for (Terrain terrain : terrains) {
			sb.append("\n  ").append(terrain);
		}

		if (!skills.isEmpty()) {
			sb.append("\nHabilidades:");
			for (Skill skill : skills) {
				sb.append("\n  ").append(skill.getName());
				sb.append("\n   ").append(skill.getDescription());
			}
		}
		return sb.toString();
	}

	/**
	 * Acciones extra al excavar en una región: las de las habilidades más 1 por
	 * cada 25 de experiencia a mayores si está especializado en ella.
	 *
	 * @param region La región en la que se excava.
	 * @return El número de acciones extra.
	 */
	public int getExtraActions(Region region) {
		int total = 0;
		for (Skill skill : skills) {
			total += skill.getExtraActions();
		}
		if (isSpecializedIn(region)) {
			total += extraExperience() / XP_PER_SPECIALIZATION_ACTION;
		}
		return total;
	}

	/**
	 * Tesoros aleatorios extra en una región: los de las habilidades más 1 por
	 * cada 50 de experiencia a mayores si está especializado en ella.
	 *
	 * @param region La región en la que se excava.
	 * @return El número de tesoros aleatorios extra.
	 */
	public int getRandomTreasures(Region region) {
		int total = 0;
		for (Skill skill : skills) {
			total += skill.getRandomTreasures();
		}
		if (isSpecializedIn(region)) {
			total += extraExperience() / XP_PER_SPECIALIZATION_RANDOM;
		}
		return total;
	}

	/**
	 * Tesoros extra de una rareza en una región: los de las habilidades más, si
	 * está especializado en ella, 1 común por cada 50 de experiencia a mayores, 1
	 * infrecuente por cada 100 o 1 raro por cada 200.
	 *
	 * @param rarity La rareza de los tesoros.
	 * @param region La región en la que se excava.
	 * @return El número de tesoros extra de esa rareza.
	 */
	public int getTreasuresByRarity(ETreasureRarity rarity, Region region) {
		int total = 0;
		for (Skill skill : skills) {
			total += skill.getTreasuresByRarity(rarity);
		}
		if (isSpecializedIn(region)) {
			int divisor;
			switch (rarity) {
				case UNCOMMON:
					divisor = XP_PER_SPECIALIZATION_UNCOMMON;
					break;
				case RARE:
					divisor = XP_PER_SPECIALIZATION_RARE;
					break;
				default:
					divisor = XP_PER_SPECIALIZATION_COMMON;
					break;
			}
			total += extraExperience() / divisor;
		}
		return total;
	}

	/**
	 * Tesoros extra al excavar un terreno concreto, según las habilidades.
	 *
	 * @param terrain El terreno excavado.
	 * @return El número de tesoros extra en ese terreno.
	 */
	public int getTreasuresByTerrain(Terrain terrain) {
		int total = 0;
		for (Skill skill : skills) {
			total += skill.getTreasuresByTerrain(terrain);
		}
		return total;
	}
}