package excavation;

/**
 * Forma de la rejilla de un terreno. Todas las formas de un mismo tamaño tienen
 * un número de casillas similar. El orden de las constantes se usa como índice
 * en la tabla de dimensiones de ETerrainSize.
 */
public enum ETerrainShape {
	/** Fila. */
	ROW("fila"),
	/** Horizontal (más ancha que alta). */
	HORIZONTAL("horizontal"),
	/** Columna. */
	COLUMN("columna"),
	/** Vertical (más alta que ancha). */
	VERTICAL("vertical"),
	/** Cuadrada. */
	SQUARE("cuadrada");

	/** El texto asociado. */
	private final String txt;

	/**
	 * Constructor para establecer el texto.
	 *
	 * @param txt El texto interior.
	 */
	private ETerrainShape(String txt) {
		this.txt = txt;
	}

	@Override
	public String toString() {
		return this.txt;
	}
}
