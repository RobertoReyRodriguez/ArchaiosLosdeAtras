package excavation;

import treasures.ETreasureRarity;

/**
 * Tamaño de un terreno. Cada tamaño define su rango de casillas, las
 * dimensiones de cada una de sus cinco formas y los mínimos y máximos de
 * tesoros de cada rareza.
 * Las dimensiones se expresan como ancho x alto (columnas x filas). 
 */
public enum ETerrainSize {
	/**
	 * Pequeño: 15-25 casillas. Al menos un tesoro común, puede haber infrecuentes
	 * y nunca raros.
	 */
	SMALL("pequeño", 15, 25,
			new int[][] { { 5, 3 }, { 10, 2 }, { 3, 5 }, { 2, 10 }, { 5, 5 } },
			new int[] { 1, 0, 0 },
			new int[] { 4, 2, 0 }),

	/**
	 * Mediano: 90-100 casillas. Al menos tres tesoros comunes y uno infrecuente;
	 * puede haber raros.
	 */
	MEDIUM("mediano", 90, 100,
			new int[][] { { 20, 5 }, { 30, 3 }, { 5, 20 }, { 3, 30 }, { 10, 10 } },
			new int[] { 3, 1, 0 },
			new int[] { 8, 4, 2 }),

	/**
	 * Grande: 200-225 casillas. Al menos cinco tesoros comunes, tres infrecuentes
	 * y uno raro.
	 */
	LARGE("grande", 200, 225,
			new int[][] { { 20, 10 }, { 40, 5 }, { 10, 20 }, { 5, 40 }, { 15, 15 } },
			new int[] { 5, 3, 1 },
			new int[] { 12, 7, 3 });

	/** El texto asociado. */
	private final String txt;

	/** Número mínimo de casillas. */
	private final int minCells;

	/** Número máximo de casillas. */
	private final int maxCells;

	/**
	 * Dimensiones {ancho, alto} de cada forma, indexadas por el orden de ETerrainShape.
	 */
	private final int[][] dimensions;

	/** Mínimo de tesoros por rareza, indexado por el orden de ETreasureRarity. */
	private final int[] minTreasures;

	/** Máximo de tesoros por rareza, indexado por el orden de ETreasureRarity. */
	private final int[] maxTreasures;

	/**
	 * Constructor del tamaño.
	 *
	 * @param txt          El texto asociado.
	 * @param minCells     El número mínimo de casillas.
	 * @param maxCells     El número máximo de casillas.
	 * @param dimensions   Las dimensiones {ancho, alto} de cada forma, en el orden de ETerrainShape.
	 * @param minTreasures El mínimo de tesoros por rareza, en el orden de ETreasureRarity.
	 * @param maxTreasures El máximo de tesoros por rareza, en el orden de ETreasureRarity.
	 */
	private ETerrainSize(String txt, int minCells, int maxCells, int[][] dimensions,
			int[] minTreasures, int[] maxTreasures) {
		this.txt = txt;
		this.minCells = minCells;
		this.maxCells = maxCells;
		this.dimensions = dimensions;
		this.minTreasures = minTreasures;
		this.maxTreasures = maxTreasures;
	}

	/**
	 * Obtiene el número mínimo de casillas de este tamaño.
	 *
	 * @return El mínimo de casillas.
	 */
	public int getMinCells() {
		return minCells;
	}

	/**
	 * Obtiene el número máximo de casillas de este tamaño.
	 *
	 * @return El máximo de casillas.
	 */
	public int getMaxCells() {
		return maxCells;
	}

	/**
	 * Obtiene el ancho (columnas) de la rejilla para una forma.
	 *
	 * @param shape La forma del terreno.
	 * @return El ancho en casillas.
	 */
	public int getWidth(ETerrainShape shape) {
		return dimensions[shape.ordinal()][0];
	}

	/**
	 * Obtiene el alto (filas) de la rejilla para una forma.
	 *
	 * @param shape La forma del terreno.
	 * @return El alto en casillas.
	 */
	public int getHeight(ETerrainShape shape) {
		return dimensions[shape.ordinal()][1];
	}

	/**
	 * Obtiene el mínimo de tesoros de una rareza que debe tener un terreno de este
	 * tamaño.
	 *
	 * @param rarity La rareza de los tesoros.
	 * @return El mínimo de tesoros.
	 */
	public int getMinTreasures(ETreasureRarity rarity) {
		return minTreasures[rarity.ordinal()];
	}

	/**
	 * Obtiene el máximo de tesoros de una rareza que puede tener un terreno de este
	 * tamaño.
	 *
	 * @param rarity La rareza de los tesoros.
	 * @return El máximo de tesoros.
	 */
	public int getMaxTreasures(ETreasureRarity rarity) {
		return maxTreasures[rarity.ordinal()];
	}

	@Override
	public String toString() {
		return this.txt;
	}
}
