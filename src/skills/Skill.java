package skills;

import excavation.ETerrainShape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import treasures.ETreasureRarity;

/**
 * Base de todas las habilidades de los arqueólogos.
 * Tiene un método para cada tipo de bonus posible, que por defecto no da nada;
 * cada tipo de habilidad sobrescribe los suyos. .
 */
public abstract class Skill {

	/** El nombre de la habilidad. Es único y sirve de código. */
	private final String name;
	/** La descripción que se muestra al usuario. */
	private final String description;
	/** Las habilidades que hay que tener antes de poder obtener esta. */
	private final List<Skill> dependencies = new ArrayList<>();
	/** Las habilidades con las que esta no puede convivir. */
	private final List<Skill> incompatibilities = new ArrayList<>();

	/**
	 * @param name El nombre de la habilidad.
	 * @param description La descripción.
	 */
	protected Skill(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * @return El tipo de habilidad.
	 */
	public abstract ESkillType getType();

	/**
	 * @return El nombre de la habilidad.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return La descripción de la habilidad.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return Las habilidades necesarias para obtener esta. 
	 */
	public List<Skill> getDependencies() {
		return Collections.unmodifiableList(dependencies);
	}

	/**
	 * @return Las habilidades incompatibles con esta. 
	 */
	public List<Skill> getIncompatibilities() {
		return Collections.unmodifiableList(incompatibilities);
	}

	/**
	 * Indica que para obtener esta habilidad hay que tener antes otra.
	 * @param skill La habilidad necesaria.
	 */
	public void addDependency(Skill skill) {
		dependencies.add(skill);
	}

	/**
	 * Hace incompatibles dos habilidades.
	 * @param first Una habilidad.
	 * @param second La otra habilidad.
	 */
	public static void setIncompatible(Skill first, Skill second) {
		first.incompatibilities.add(second);
		second.incompatibilities.add(first);
	}

	// --- Bonus. 

	/**
	 * @return Los tesoros extra de rareza aleatoria.
	 */
	public int getExtraRandomTreasures() {
		return 0;
	}

	/**
	 * @param rarity La rareza.
	 * @return Los tesoros extra de esa rareza.
	 */
	public int getExtraTreasures(ETreasureRarity rarity) {
		return 0;
	}

	/**
	 * @param rarity La rareza.
	 * @return Los puntos de porcentaje que se suman a la probabilidad de esa rareza.
	 */
	public int getProbabilityBonus(ETreasureRarity rarity) {
		return 0;
	}

	/**
	 * @return Las acciones extra de excavación.
	 */
	public int getExtraActions() {
		return 0;
	}

	/**
	 * @return El factor por el que se multiplican las acciones. 1 si no cambia nada.
	 */
	public double getActionsFactor() {
		return 1;
	}

	/**
	 * @return Las casillas vacías que se quitan antes de excavar (limpieza).
	 */
	public int getCleanedCells() {
		return 0;
	}

	/**
	 * @return Los grupos de tres '?' que se ponen en la rejilla (suposición).
	 */
	public int getGuesses() {
		return 0;
	}

	/**
	 * @return true si muestra en cada ronda cuántos tesoros de cada rareza hay (investigación).
	 */
	public boolean hasInvestigation() {
		return false;
	}

	/**
	 * Las formas de terreno que añade la habilidad al obtenerse. No es un bono:
	 * el arqueólogo las añade a su lista de terrenos cuando aprende la habilidad.
	 * @return Las formas de terreno a añadir. Vacía si no añade ninguna.
	 */
	public List<ETerrainShape> getTerrains() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return name;
	}
}
