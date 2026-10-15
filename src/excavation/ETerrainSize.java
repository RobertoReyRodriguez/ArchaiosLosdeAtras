package excavation;

import treasures.ETreasureRarity;

/**
 * Los tamaños de terreno: el nivel de arqueólogo necesario para excavarlos, los tesoros
 * mínimos de cada rareza que tienen siempre y la experiencia extra que dan.
 */
public enum ETerrainSize {
	SMALL("pequeño", 0, 1, 0, 0, false, 2),
	MEDIUM("mediano", 2, 3, 1, 0, true, 5),
	LARGE("grande", 4, 5, 3, 1, true, 10);

	/** El nombre del tamaño. */
	private final String txt;
	/** El nivel mínimo del arqueólogo para excavar este tamaño. */
	private final int minLevel;
	/** Los tesoros comunes que hay como mínimo. */
	private final int minCommon;
	/** Los tesoros infrecuentes que hay como mínimo. */
	private final int minUncommon;
	/** Los tesoros raros que hay como mínimo. */
	private final int minRare;
	/** Si puede tener tesoros raros. Los pequeños no, ni siquiera con habilidades. */
	private final boolean allowsRare;
	/** La experiencia extra que gana el arqueólogo al excavar este tamaño. */
	private final int extraExp;

	/**
	 * @param txt El nombre del tamaño.
	 * @param minLevel El nivel mínimo del arqueólogo.
	 * @param minCommon Los tesoros comunes mínimos.
	 * @param minUncommon Los tesoros infrecuentes mínimos.
	 * @param minRare Los tesoros raros mínimos.
	 * @param allowsRare Si puede tener tesoros raros.
	 * @param extraExp La experiencia extra al excavarlo.
	 */
	private ETerrainSize(String txt, int minLevel, int minCommon, int minUncommon, int minRare,
			boolean allowsRare, int extraExp) {
		this.txt = txt;
		this.minLevel = minLevel;
		this.minCommon = minCommon;
		this.minUncommon = minUncommon;
		this.minRare = minRare;
		this.allowsRare = allowsRare;
		this.extraExp = extraExp;
	}

	/**
	 * @return El nivel mínimo del arqueólogo para excavar este tamaño.
	 */
	public int getMinLevel() {
		return minLevel;
	}

	/**
	 * @param rarity La rareza.
	 * @return Los tesoros de esa rareza que tiene este tamaño como mínimo.
	 */
	public int getMinTreasures(ETreasureRarity rarity) {
		switch (rarity) {
			case COMMON:
				return minCommon;
			case UNCOMMON:
				return minUncommon;
			default:
				return minRare;
		}
	}

	/**
	 * @return Si puede tener tesoros raros.
	 */
	public boolean allowsRare() {
		return allowsRare;
	}

	/**
	 * @return La experiencia extra que gana el arqueólogo al excavar este tamaño.
	 */
	public int getExtraExp() {
		return extraExp;
	}

	@Override
	public String toString() {
		return txt;
	}
}
