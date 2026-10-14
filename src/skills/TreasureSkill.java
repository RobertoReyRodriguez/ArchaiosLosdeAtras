package skills;

import java.util.EnumMap;
import java.util.Map;
import treasures.ETreasureRarity;

/**
 * Habilidades que mejoran la obtención de tesoros: más tesoros aleatorios,
 * más tesoros de una rareza o más probabilidad de una rareza.
 */
public class TreasureSkill extends Skill {

	/** Tesoros extra de rareza aleatoria. */
	private int extraRandom;
	/** Tesoros extra por rareza. */
	private final Map<ETreasureRarity, Integer> extraByRarity = new EnumMap<>(ETreasureRarity.class);
	/** Puntos de porcentaje extra por rareza. */
	private final Map<ETreasureRarity, Integer> probabilityByRarity = new EnumMap<>(ETreasureRarity.class);

	/**
	 * @param name El nombre de la habilidad.
	 * @param description La descripción.
	 */
	public TreasureSkill(String name, String description) {
		super(name, description);
	}

	/**
	 * @param amount Tesoros extra de rareza aleatoria.
	 * @return La propia habilidad, para encadenar.
	 */
	public TreasureSkill extraRandom(int amount) {
		extraRandom = amount;
		return this;
	}

	/**
	 * @param rarity La rareza.
	 * @param amount Tesoros extra de esa rareza.
	 * @return La propia habilidad, para encadenar.
	 */
	public TreasureSkill extra(ETreasureRarity rarity, int amount) {
		extraByRarity.put(rarity, amount);
		return this;
	}

	/**
	 * @param rarity La rareza.
	 * @param percent Puntos de porcentaje que se suman a su probabilidad.
	 * @return La propia habilidad, para encadenar.
	 */
	public TreasureSkill probability(ETreasureRarity rarity, int percent) {
		probabilityByRarity.put(rarity, percent);
		return this;
	}

	@Override
	public ESkillType getType() {
		return ESkillType.TREASURE;
	}

	@Override
	public int getExtraRandomTreasures() {
		return extraRandom;
	}

	@Override
	public int getExtraTreasures(ETreasureRarity rarity) {
		return extraByRarity.getOrDefault(rarity, 0);
	}

	@Override
	public int getProbabilityBonus(ETreasureRarity rarity) {
		return probabilityByRarity.getOrDefault(rarity, 0);
	}
}
