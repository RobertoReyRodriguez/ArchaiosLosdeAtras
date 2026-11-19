package skills;

import excavation.EGround;
import java.util.EnumMap;
import java.util.Map;

/**
 * Habilidades de especialista: facilitan la excavación de los terrenos difíciles, ya sea fijando
 * el coste de excavar un tipo de terreno o cambiando casillas de un tipo de terreno por tierra antes de empezar.
 */
public class GroundSkill extends Skill {

	/** El coste fijo de excavar cada terreno que cambia. */
	private final Map<EGround, Integer> costs = new EnumMap<>(EGround.class);
	/** Las casillas de cada terreno que se cambian por tierra antes de empezar. */
	private final Map<EGround, Integer> changes = new EnumMap<>(EGround.class);

	/**
	 * @param name El nombre de la habilidad.
	 * @param description La descripción.
	 */
	public GroundSkill(String name, String description) {
		super(name, description);
	}

	/**
	 * Fija el coste de excavar un terreno. Las habilidades que lo hacen son especializaciones.
	 * @param ground El terreno.
	 * @param cost El coste de excavarlo con esta habilidad.
	 * @return La propia habilidad, para encadenar.
	 */
	public GroundSkill cost(EGround ground, int cost) {
		costs.put(ground, cost);
		return this;
	}

	/**
	 * @param ground El terreno.
	 * @param amount Las casillas de ese terreno que se cambian por tierra antes de empezar.
	 * @return La propia habilidad, para encadenar.
	 */
	public GroundSkill changes(EGround ground, int amount) {
		changes.put(ground, amount);
		return this;
	}

	@Override
	public ESkillType getType() {
		return ESkillType.EXPERT;
	}

	@Override
	public int getGroundCost(char symbol) {
		return costs.getOrDefault(EGround.fromSymbol(symbol), 0);
	}

	@Override
	public int getChangedGrounds(char symbol) {
		return changes.getOrDefault(EGround.fromSymbol(symbol), 0);
	}

	@Override
	public boolean isGroundSpecialization() {
		return !costs.isEmpty();
	}
}
