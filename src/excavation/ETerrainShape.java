package excavation;

/**
 * Las formas de la rejilla de un terreno. El arqueólogo empieza con la cuadrada
 * y desbloquea el resto con habilidades de terreno.
 */
public enum ETerrainShape {
	ROW("fila"),
	HORIZONTAL("horizontal"),
	COLUMN("columna"),
	VERTICAL("vertical"),
	SQUARE("cuadrado");

	/** El nombre de la forma. */
	private final String txt;

	/**
	 * @param txt El nombre de la forma.
	 */
	private ETerrainShape(String txt) {
		this.txt = txt;
	}

	@Override
	public String toString() {
		return txt;
	}
}
