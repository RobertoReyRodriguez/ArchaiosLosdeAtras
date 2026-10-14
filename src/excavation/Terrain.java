package excavation;

import java.util.Objects;

import treasures.ETreasureRarity;

/**
 * Terreno que puede excavar un arqueólogo. Se define por un tamaño
 * ETerrainSize y una forma de rejilla ETerrainShape de
 * esa combinación salen las dimensiones y los límites de tesoros.
 */
public final class Terrain {

	/** El tamaño del terreno. */
	private final ETerrainSize size;

	/** La forma de la rejilla. */
	private final ETerrainShape shape;

	/**
	 * Crea un terreno.
	 *
	 * @param size  El tamaño del terreno(si fuese null se tiene como default tamano pequeno).
	 * @param shape La forma de la rejilla(si fuese null se tiene como default forma cuadrada).
	 */
	public Terrain(ETerrainSize size, ETerrainShape shape) {
		this.size = (size != null) ? size : ETerrainSize.SMALL;
		this.shape = (shape != null) ? shape : ETerrainShape.SQUARE;
	}

	/**
	 * Crea un terreno cuadrado del tamaño indicado, el único tipo que desbloquean
	 * los arqueólogos al subir de nivel.
	 *
	 * @param size El tamaño.
	 * @return El terreno cuadrado.
	 */
	public static Terrain square(ETerrainSize size) {
		return new Terrain(size, ETerrainShape.SQUARE);
	}

	/**
	 * Obtiene el tamaño.
	 *
	 * @return El tamaño del terreno.
	 */
	public ETerrainSize getSize() {
		return size;
	}

	/**
	 * Obtiene la forma de la rejilla.
	 *
	 * @return La forma del terreno.
	 */
	public ETerrainShape getShape() {
		return shape;
	}

	/**
	 * Obtiene el ancho de la rejilla.
	 *
	 * @return El número de columnas.
	 */
	public int getWidth() {
		return size.getWidth(shape);
	}

	/**
	 * Obtiene el alto de la rejilla.
	 *
	 * @return El número de filas.
	 */
	public int getHeight() {
		return size.getHeight(shape);
	}

	/**
	 * Obtiene el número total de casillas.
	 *
	 * @return Ancho por alto.
	 */
	public int getCells() {
		return getWidth() * getHeight();
	}

	/**
	 * Obtiene el mínimo de tesoros de una rareza que debe contener.
	 *
	 * @param rarity La rareza de los tesoros.
	 * @return El mínimo de tesoros.
	 */
	public int getMinTreasures(ETreasureRarity rarity) {
		return size.getMinTreasures(rarity);
	}

	/**
	 * Obtiene el máximo de tesoros de una rareza que puede contener.
	 *
	 * @param rarity La rareza de los tesoros.
	 * @return El máximo de tesoros.
	 */
	public int getMaxTreasures(ETreasureRarity rarity) {
		return size.getMaxTreasures(rarity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Terrain)) {
			return false;
		}
		Terrain other = (Terrain) obj;
		return size == other.size && shape == other.shape;
	}

	@Override
	public int hashCode() {
		return Objects.hash(size, shape);
	}

	/**
	 * Devuelve una descripción como "pequeño cuadrada (5x5)".
	 *
	 * @return El texto del terreno.
	 */
	@Override
	public String toString() {
		return size + " " + shape + " (" + getWidth() + "x" + getHeight() + ")";
	}
}
