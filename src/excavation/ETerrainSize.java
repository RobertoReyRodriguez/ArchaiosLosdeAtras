package excavation;

/**
 * Los tamaños de terreno y el nivel de arqueólogo necesario para excavarlos.
 */
public enum ETerrainSize {
	SMALL("pequeño", 0),
	MEDIUM("mediano", 2),
	LARGE("grande", 4);

	/** El nombre del tamaño. */
	private final String txt;
	/** El nivel mínimo del arqueólogo para excavar este tamaño. */
	private final int minLevel;

	/**
	 * @param txt El nombre del tamaño.
	 * @param minLevel El nivel mínimo del arqueólogo.
	 */
	private ETerrainSize(String txt, int minLevel) {
		this.txt = txt;
		this.minLevel = minLevel;
	}

	/**
	 * @return El nivel mínimo del arqueólogo para excavar este tamaño.
	 */
	public int getMinLevel() {
		return minLevel;
	}

	@Override
	public String toString() {
		return txt;
	}
}
