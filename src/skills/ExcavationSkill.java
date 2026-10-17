package skills;

/**
 * Habilidades que mejoran el proceso de excavación: más acciones, limpieza
 * de casillas vacías, suposición con '?' e investigación.
 */
public class ExcavationSkill extends Skill {

	/** Acciones extra. */
	private int extraActions;
	/** Porcentaje en que aumentan las acciones. */
	private int actionsPercent;
	/** Casillas vacías que se quitan antes de excavar. */
	private int cleanedCells;
	/** Grupos de tres '?' que se ponen en la rejilla. */
	private int guesses;
	/** Si muestra cuántos tesoros de cada rareza hay. */
	private boolean investigation;

	/**
	 * @param name El nombre de la habilidad.
	 * @param description La descripción.
	 */
	public ExcavationSkill(String name, String description) {
		super(name, description);
	}

	/**
	 * @param amount Acciones extra.
	 * @return La propia habilidad, para encadenar.
	 */
	public ExcavationSkill extraActions(int amount) {
		extraActions = amount;
		return this;
	}

	/**
	 * @param percent Porcentaje en que aumentan las acciones.
	 * @return La propia habilidad, para encadenar.
	 */
	public ExcavationSkill actionsPercent(int percent) {
		actionsPercent = percent;
		return this;
	}

	/**
	 * @param amount Casillas vacías que se quitan antes de excavar.
	 * @return La propia habilidad, para encadenar.
	 */
	public ExcavationSkill cleanedCells(int amount) {
		cleanedCells = amount;
		return this;
	}

	/**
	 * @param amount Grupos de tres '?' que se ponen en la rejilla.
	 * @return La propia habilidad, para encadenar.
	 */
	public ExcavationSkill guesses(int amount) {
		guesses = amount;
		return this;
	}

	/**
	 * Activa la investigación: mostrar cuántos tesoros de cada rareza hay.
	 * @return La propia habilidad, para encadenar.
	 */
	public ExcavationSkill investigation() {
		investigation = true;
		return this;
	}

	@Override
	public ESkillType getType() {
		return ESkillType.EXCAVATION;
	}

	@Override
	public int getExtraActions() {
		return extraActions;
	}

	@Override
	public double getActionsFactor() {
		return 1 + actionsPercent / 100.0;
	}

	@Override
	public int getCleanedCells() {
		return cleanedCells;
	}

	@Override
	public int getGuesses() {
		return guesses;
	}

	@Override
	public boolean hasInvestigation() {
		return investigation;
	}
}
