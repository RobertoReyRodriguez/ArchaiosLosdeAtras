package excavation;

import helpers.UtilsHelper;
import treasures.ETreasureRarity;

/**
 * Los terrenos de excavación. Cada uno es la combinación de un tamaño y una forma, y de ello
 * salen las medidas de la rejilla, el nivel de arqueólogo necesario para excavarlo, los tesoros
 * mínimos de cada rareza que tiene siempre y la experiencia extra que da.
 * El arqueólogo empieza con los terrenos cuadrados y desbloquea el resto con habilidades de terreno.
 */
public enum ETerrain {
	// tamaño, forma, filas, columnas, nivel mínimo, comunes, infrecuentes, raros, permite raros, exp extra
	SMALL_ROW("pequeño", "fila", 5, 3, 0, 1, 0, 0, false, 2),
	SMALL_HORIZONTAL("pequeño", "horizontal", 10, 2, 0, 1, 0, 0, false, 2),
	SMALL_COLUMN("pequeño", "columna", 3, 5, 0, 1, 0, 0, false, 2),
	SMALL_VERTICAL("pequeño", "vertical", 2, 10, 0, 1, 0, 0, false, 2),
	SMALL_SQUARE("pequeño", "cuadrado", 5, 5, 0, 1, 0, 0, false, 2),

	MEDIUM_ROW("mediano", "fila", 20, 5, 2, 3, 1, 0, true, 5),
	MEDIUM_HORIZONTAL("mediano", "horizontal", 30, 3, 2, 3, 1, 0, true, 5),
	MEDIUM_COLUMN("mediano", "columna", 5, 20, 2, 3, 1, 0, true, 5),
	MEDIUM_VERTICAL("mediano", "vertical", 3, 30, 2, 3, 1, 0, true, 5),
	MEDIUM_SQUARE("mediano", "cuadrado", 10, 10, 2, 3, 1, 0, true, 5),

	LARGE_ROW("grande", "fila", 20, 10, 4, 5, 3, 1, true, 10),
	LARGE_HORIZONTAL("grande", "horizontal", 40, 5, 4, 5, 3, 1, true, 10),
	LARGE_COLUMN("grande", "columna", 10, 20, 4, 5, 3, 1, true, 10),
	LARGE_VERTICAL("grande", "vertical", 5, 40, 4, 5, 3, 1, true, 10),
	LARGE_SQUARE("grande", "cuadrado", 15, 15, 4, 5, 3, 1, true, 10);

	/** El nombre del tamaño. */
	private final String size;
	/** El nombre de la forma. */
	private final String shape;
	/** El número de filas de la rejilla, las que van con números. */
	private final int rows;
	/** El número de columnas de la rejilla, las que van con letras. */
	private final int columns;
	/** El nivel mínimo del arqueólogo para excavar este terreno. */
	private final int minLevel;
	/** Los tesoros comunes que hay como mínimo. */
	private final int minCommon;
	/** Los tesoros infrecuentes que hay como mínimo. */
	private final int minUncommon;
	/** Los tesoros raros que hay como mínimo. */
	private final int minRare;
	/** Si puede tener tesoros raros. Los pequeños no, ni siquiera con habilidades. */
	private final boolean allowsRare;
	/** La experiencia extra que gana el arqueólogo al excavar este terreno. */
	private final int extraExp;

	/**
	 * @param size El nombre del tamaño.
	 * @param shape El nombre de la forma.
	 * @param rows Las filas de la rejilla.
	 * @param columns Las columnas de la rejilla.
	 * @param minLevel El nivel mínimo del arqueólogo.
	 * @param minCommon Los tesoros comunes mínimos.
	 * @param minUncommon Los tesoros infrecuentes mínimos.
	 * @param minRare Los tesoros raros mínimos.
	 * @param allowsRare Si puede tener tesoros raros.
	 * @param extraExp La experiencia extra al excavarlo.
	 */
	private ETerrain(String size, String shape, int rows, int columns, int minLevel, int minCommon,
			int minUncommon, int minRare, boolean allowsRare, int extraExp) {
		this.size = size;
		this.shape = shape;
		this.rows = rows;
		this.columns = columns;
		this.minLevel = minLevel;
		this.minCommon = minCommon;
		this.minUncommon = minUncommon;
		this.minRare = minRare;
		this.allowsRare = allowsRare;
		this.extraExp = extraExp;
	}

	/**
	 * @return El nombre del tamaño del terreno. <code>pequeño</code>
	 */
	public String getSizeName() {
		return size;
	}

	/**
	 * @return El nombre de la forma del terreno. <code>cuadrado</code>
	 */
	public String getShapeName() {
		return shape;
	}

	/**
	 * @return El número de filas de la rejilla, las que van con números.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return El número de columnas de la rejilla, las que van con letras.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * @return El número total de casillas.
	 */
	public int getCells() {
		return rows * columns;
	}

	/**
	 * @return El nivel mínimo del arqueólogo para excavar este terreno.
	 */
	public int getMinLevel() {
		return minLevel;
	}

	/**
	 * @param rarity La rareza.
	 * @return Los tesoros de esa rareza que tiene este terreno como mínimo.
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
	 * @return La experiencia extra que gana el arqueólogo al excavar este terreno.
	 */
	public int getExtraExp() {
		return extraExp;
	}

	/**
	 * @return El nombre del terreno con sus medidas. <code>Pequeño cuadrado (5x5)</code>
	 */
	@Override
	public String toString() {
		return UtilsHelper.ucfirst(size) + " " + shape + " (" + rows + "x" + columns + ")";
	}
}