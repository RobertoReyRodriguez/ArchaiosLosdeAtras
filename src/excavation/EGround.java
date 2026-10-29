package excavation;

import helpers.RandomHelper;

/**
 * Los tipos de terreno de una casilla de la rejilla.
 * Cada uno tiene un símbolo, un coste de acciones al excavarlo, fijo o aleatorio dentro de un
 * rango, y puede tener un siguiente nivel: al excavarlo no se ve lo que esconde la casilla,
 * sino que pasa a ese terreno y hay que excavarla otra vez. Los que no tienen siguiente
 * nivel son el último: al excavarlos se descubre lo que hay debajo.
 */
public enum EGround {
	/** El terreno básico, el de todas las regiones sin terrenos difíciles. */
	EARTH('·', "tierra", 1, 1, null),
	/** Al excavarla pasa a tierra. */
	STONE('^', "piedra", 1, 1, EARTH),
	/** Su coste cambia en cada excavación. */
	SAND('~', "arena", 1, 2, null),
	WATER('–', "agua", 2, 2, null);

	/** El símbolo con el que se ve en la rejilla. */
	private final char symbol;
	/** El nombre del terreno. */
	private final String name;
	/** El coste mínimo de excavarlo. */
	private final int minCost;
	/** El coste máximo de excavarlo. */
	private final int maxCost;
	/** El terreno en el que se convierte al excavarlo, o null si es el último nivel. */
	private final EGround next;

	/**
	 * @param symbol El símbolo.
	 * @param name El nombre.
	 * @param minCost El coste mínimo.
	 * @param maxCost El coste máximo. Igual que el mínimo si el coste es fijo.
	 * @param next El siguiente nivel, o null si no tiene.
	 */
	private EGround(char symbol, String name, int minCost, int maxCost, EGround next) {
		this.symbol = symbol;
		this.name = name;
		this.minCost = minCost;
		this.maxCost = maxCost;
		this.next = next;
	}

	/**
	 * @return El símbolo con el que se ve en la rejilla.
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * @return El nombre del terreno, en minúsculas. <code>piedra</code>
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return El coste mínimo de excavarlo.
	 */
	public int getMinCost() {
		return minCost;
	}

	/**
	 * @return El coste máximo de excavarlo.
	 */
	public int getMaxCost() {
		return maxCost;
	}

	/**
	 * @return true si el coste es aleatorio dentro de un rango, false si es fijo.
	 */
	public boolean hasVariableCost() {
		return minCost != maxCost;
	}

	/**
	 * Calcula lo que cuesta excavarlo esta vez. Si el coste es variable, se elige al azar
	 * entre el mínimo y el máximo, ambos incluidos.
	 * @return El coste en acciones.
	 */
	public int rollCost() {
		return RandomHelper.randomInt(minCost, maxCost);
	}

	/**
	 * @return true si al excavarlo pasa a otro terreno en lugar de descubrir lo que hay debajo.
	 */
	public boolean hasNext() {
		return next != null;
	}

	/**
	 * @return El terreno en el que se convierte al excavarlo, o null si es el último nivel.
	 */
	public EGround getNext() {
		return next;
	}

	/**
	 * @param symbol El símbolo de un terreno.
	 * @return El terreno con ese símbolo, o null si no hay ninguno.
	 */
	public static EGround fromSymbol(char symbol) {
		for (EGround ground : values()) {
			if (ground.symbol == symbol) {
				return ground;
			}
		}
		return null;
	}

	/**
	 * @return El nombre del terreno con su símbolo y su coste. Ejemplo: Piedra (^, coste 1)
	 */
	@Override
	public String toString() {
		String cost = hasVariableCost() ? minCost + "-" + maxCost : String.valueOf(minCost);
		return name.substring(0, 1).toUpperCase() + name.substring(1) + " (" + symbol + ", coste " + cost + ")";
	}
}
