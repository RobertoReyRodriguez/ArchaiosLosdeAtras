package excavation;

import helpers.UtilsHelper;
import java.util.ArrayList;
import java.util.List;
import people.Archeologist;

/**
 * Un terreno de excavación: un tamaño y una forma, que juntos dan las medidas de la rejilla.
 */
public class Terrain {

	/**
	 * Las medidas de cada terreno como columnas y filas. 
	 * 
	 */
	private static final int[][][] DIMENSIONS = {
			{ { 5, 3 }, { 10, 2 }, { 3, 5 }, { 2, 10 }, { 5, 5 } },
			{ { 20, 5 }, { 30, 3 }, { 5, 20 }, { 3, 30 }, { 10, 10 } },
			{ { 20, 10 }, { 40, 5 }, { 10, 20 }, { 5, 40 }, { 15, 15 } }
	};

	/** El tamaño del terreno. */
	private final ETerrainSize size;
	/** La forma del terreno. */
	private final ETerrainShape shape;

	/**
	 * @param size El tamaño del terreno.
	 * @param shape La forma del terreno.
	 */
	public Terrain(ETerrainSize size, ETerrainShape shape) {
		this.size = size;
		this.shape = shape;
	}

	/**
	 * Devuelve los terrenos que puede excavar un arqueólogo: los tamaños que permite su nivel
	 * combinados con las formas que tiene.
	 * @param archeologist El arqueólogo.
	 * @return Los terrenos, ordenados por tamaño y forma.
	 */
	public static List<Terrain> getAvailable(Archeologist archeologist) {
		List<Terrain> terrains = new ArrayList<>();
		for (ETerrainSize size : archeologist.getTerrainSizes()) {
			for (ETerrainShape shape : ETerrainShape.values()) {
				if (archeologist.getTerrains().contains(shape)) {
					terrains.add(new Terrain(size, shape));
				}
			}
		}
		return terrains;
	}

	/**
	 * @return El tamaño del terreno.
	 */
	public ETerrainSize getSize() {
		return size;
	}

	/**
	 * @return La forma del terreno.
	 */
	public ETerrainShape getShape() {
		return shape;
	}

	/**
	 * @return El número de columnas de la rejilla, las que van con letras.
	 */
	public int getColumns() {
		return DIMENSIONS[size.ordinal()][shape.ordinal()][0];
	}

	/**
	 * @return El número de filas de la rejilla, las que van con números.
	 */
	public int getRows() {
		return DIMENSIONS[size.ordinal()][shape.ordinal()][1];
	}

	/**
	 * @return El número total de casillas.
	 */
	public int getCells() {
		return getColumns() * getRows();
	}

	/**
	 * @return El nombre del terreno con sus medidas. <code>Pequeño cuadrado (5x5)</code>
	 */
	@Override
	public String toString() {
		return UtilsHelper.ucfirst(size.toString()) + " " + shape + " (" + getColumns() + "x" + getRows() + ")";
	}
}
