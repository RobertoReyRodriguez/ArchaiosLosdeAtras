package people;

import java.util.Random;

/**
 * Rareza de un arqueólogo. Determina la probabilidad de obtenerlo al contratar,
 * cuántas habilidades iniciales tiene y los puntos extra al licenciarlo.
 */
public enum EArcheologistRarity {
	/** Común: 70% de probabilidad, sin habilidades iniciales. */
	COMMON("común", 70, 0, 0),
	/** Infrecuente: 20% de probabilidad, una habilidad inicial. */
	UNCOMMON("infrecuente", 20, 1, 10),
	/** Raro: 10% de probabilidad, dos habilidades iniciales. */
	RARE("raro", 10, 2, 20);

	/** El texto asociado. */
	private final String txt;

	/** Probabilidad (0-100) de obtener esta rareza al contratar. */
	private final int chance;

	/** Número de habilidades iniciales. */
	private final int initialSkills;

	/** Puntos extra que se obtienen al licenciar. */
	private final int dismissBonus;

	/**
	 * Constructor de la rareza.
	 *
	 * @param txt           El texto asociado.
	 * @param chance        La probabilidad (0-100) al contratar.
	 * @param initialSkills El número de habilidades iniciales.
	 * @param dismissBonus  Los puntos extra al licenciar.
	 */
	private EArcheologistRarity(String txt, int chance, int initialSkills, int dismissBonus) {
		this.txt = txt;
		this.chance = chance;
		this.initialSkills = initialSkills;
		this.dismissBonus = dismissBonus;
	}

	/**
	 * Obtiene la probabilidad de esta rareza al contratar.
	 *
	 * @return La probabilidad, de 0 a 100.
	 */
	public int getChance() {
		return chance;
	}

	/**
	 * Obtiene el número de habilidades con las que se contrata el arqueólogo.
	 *
	 * @return El número de habilidades iniciales.
	 */
	public int getInitialSkills() {
		return initialSkills;
	}

	/**
	 * Obtiene los puntos extra que se reciben al licenciar un arqueólogo de esta
	 * rareza.
	 *
	 * @return Los puntos extra.
	 */
	public int getDismissBonus() {
		return dismissBonus;
	}

	/**
	 * Escoge una rareza al azar respetando las probabilidades de cada una.
	 *
	 * @param rnd El generador de números aleatorios.
	 * @return La rareza obtenida.
	 */
	public static EArcheologistRarity random(Random rnd) {
		int roll = rnd.nextInt(100);
		int accumulated = 0;
		for (EArcheologistRarity rarity : values()) {
			accumulated += rarity.chance;
			if (roll < accumulated) {
				return rarity;
			}
		}
		return COMMON;
	}

	@Override
	public String toString() {
		return this.txt;
	}
}
