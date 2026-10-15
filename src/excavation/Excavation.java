package excavation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helpers.InputHelper;
import helpers.RandomHelper;
import helpers.UtilsHelper;
import people.Archeologist;
import regions.Region;
import treasures.ETreasureRarity;
import treasures.Treasure;

/**
 * Una excavación: genera la rejilla con sus tesoros escondidos, guarda su estado
 * y lleva el proceso de excavación por turnos.
 */
public class Excavation {

	/** Las letras con las que se nombran las columnas. */
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** Casilla sin excavar. */
	private static final char NOT_DUG = '·';
	/** Casilla excavada (o limpiada) sin tesoro. */
	private static final char EMPTY = 'x';
	/** Casilla marcada por una habilidad de suposición. */
	private static final char GUESS = '?';

	/** Los tesoros que hay, como parte de las casillas: 10%. */
	private static final double TREASURE_RATE = 0.10;
	/** Los tesoros que puede haber como máximo, como parte de las casillas: 50%. */
	private static final double MAX_TREASURE_RATE = 0.50;
	/** Las casillas que pueden revelar las habilidades como máximo: 25%. */
	private static final double MAX_REVEAL_RATE = 0.25;
	/** Las acciones base y mínimas, como parte de las casillas: 25%. */
	private static final double MIN_ACTIONS_RATE = 0.25;
	/** Las acciones máximas, como parte de las casillas: 75%. */
	private static final double MAX_ACTIONS_RATE = 0.75;

	/** Una posición: un número y una letra en cualquier orden. <code>3B</code> o <code>B3</code> */
	private static final Pattern NUMBER_LETTER = Pattern.compile("(\\d+)([A-Z]+)");
	/** Una posición con la letra delante. */
	private static final Pattern LETTER_NUMBER = Pattern.compile("([A-Z]+)(\\d+)");

	/** La región donde se excava. */
	private final Region region;
	/** El arqueólogo que excava. */
	private final Archeologist archeologist;
	/** El terreno que se excava. */
	private final Terrain terrain;

	/** Los tesoros escondidos: el código completo de la parte, o null si la casilla está vacía. */
	private final String[][] treasures;
	/** Lo que ve el usuario de cada casilla. */
	private final char[][] grid;
	/** Las letras de las columnas, en orden. */
	private final List<String> letters;
	/** Los códigos de los tesoros encontrados, en el orden en que se han encontrado. */
	private final List<String> found = new ArrayList<>();
	/** Las casillas reveladas por habilidades antes de empezar. */
	private int revealed;
	/** Las acciones que le quedan al usuario. */
	private int actions;

	/**
	 * Prepara la excavación: genera los tesoros, aplica las habilidades de revelación
	 * y calcula las acciones.
	 * @param region La región donde se excava.
	 * @param archeologist El arqueólogo que excava.
	 * @param terrain El terreno que se excava.
	 */
	public Excavation(Region region, Archeologist archeologist, Terrain terrain) {
		this.region = region;
		this.archeologist = archeologist;
		this.terrain = terrain;
		this.treasures = new String[terrain.getRows()][terrain.getColumns()];
		this.grid = new char[terrain.getRows()][terrain.getColumns()];
		for (char[] row : grid) {
			Arrays.fill(row, NOT_DUG);
		}
		this.letters = generateLetters(terrain.getColumns());

		generateTreasures();
		applyCleaning();
		applyGuesses();
		this.actions = calculateActions();
	}

	// --- Generación ---

	/**
	 * Decide la rareza de cada tesoro en el orden del enunciado y los coloca en casillas al azar.
	 
	 */
	private void generateTreasures() {
		int cells = terrain.getCells();
		int base = Math.max(1, (int) Math.ceil(cells * TREASURE_RATE));
		int max = (int) (cells * MAX_TREASURE_RATE);
		List<ETreasureRarity> rarities = new ArrayList<>();

		for (ETreasureRarity rarity : ETreasureRarity.values()) {
			for (int i = 0; i < terrain.getSize().getMinTreasures(rarity); i++) {
				rarities.add(rarity);
			}
		}
		while (rarities.size() < base) {
			rarities.add(randomRarity());
		}

		ETreasureRarity[] extraOrder = { ETreasureRarity.RARE, ETreasureRarity.UNCOMMON, ETreasureRarity.COMMON };
		for (ETreasureRarity rarity : extraOrder) {
			if (rarity != ETreasureRarity.RARE || terrain.getSize().allowsRare()) {
				int extra = archeologist.getExtraTreasures(region, rarity);
				for (int i = 0; i < extra && rarities.size() < max; i++) {
					rarities.add(rarity);
				}
			}
		}
		int extraRandom = archeologist.getExtraRandomTreasures(region);
		for (int i = 0; i < extraRandom && rarities.size() < max; i++) {
			rarities.add(randomRarity());
		}

		List<Integer> positions = UtilsHelper.genRandomIdxs(rarities.size(), cells);
		for (int i = 0; i < rarities.size(); i++) {
			Treasure treasure = region.getRandomTreasure(rarities.get(i));
			int position = positions.get(i);
			treasures[position / terrain.getColumns()][position % terrain.getColumns()] = treasure.getRandomPartCode();
		}
	}

	/**
	 * Elige una rareza al azar: 60% común, 30% infrecuente y 10% raro, más los bonos del arqueólogo
	 * a infrecuentes y raros, que se restan de los comunes. En terrenos pequeños nunca sale raro.
	 * @return La rareza elegida.
	 */
	private ETreasureRarity randomRarity() {
		int uncommon = 30 + archeologist.getProbabilityBonus(ETreasureRarity.UNCOMMON);
		int rare = terrain.getSize().allowsRare() ? 10 + archeologist.getProbabilityBonus(ETreasureRarity.RARE) : 0;
		Map<ETreasureRarity, Integer> weights = new EnumMap<>(ETreasureRarity.class);
		weights.put(ETreasureRarity.COMMON, Math.max(0, 100 - uncommon - rare));
		weights.put(ETreasureRarity.UNCOMMON, uncommon);
		weights.put(ETreasureRarity.RARE, rare);
		return RandomHelper.weighted(weights);
	}

	/**
	 * Habilidades de limpieza: marca como vacías casillas sin tesoro al azar.
	 */
	private void applyCleaning() {
		int amount = Math.min(archeologist.getCleanedCells(), getMaxRevealed() - revealed);
		for (int[] cell : RandomHelper.randomElements(getCells(false), amount)) {
			grid[cell[0]][cell[1]] = EMPTY;
			revealed++;
		}
	}

	/**
	 * Habilidades de suposición: por cada una, marca con '?' tres casillas al azar,
	 * una con tesoro y dos sin él. Se aplican tras la limpieza: si una de las elegidas
	 * ya está limpiada, se queda como está y no se elige otra.
	 */
	private void applyGuesses() {
		for (int i = 0; i < archeologist.getGuesses(); i++) {
			List<int[]> cells = RandomHelper.randomElements(getCells(true), 1);
			cells.addAll(RandomHelper.randomElements(getCells(false), 2));
			for (int[] cell : cells) {
				if (revealed >= getMaxRevealed()) {
					return;
				}
				if (grid[cell[0]][cell[1]] == NOT_DUG) {
					grid[cell[0]][cell[1]] = GUESS;
					revealed++;
				}
			}
		}
	}

	/**
	 * @return Las casillas que pueden revelar las habilidades como máximo: el 25%.
	 */
	private int getMaxRevealed() {
		return (int) (terrain.getCells() * MAX_REVEAL_RATE);
	}

	/**
	 * @param withTreasure true para las casillas con tesoro, false para las vacías.
	 * @return Las posiciones de esas casillas.
	 */
	private List<int[]> getCells(boolean withTreasure) {
		List<int[]> cells = new ArrayList<>();
		for (int row = 0; row < terrain.getRows(); row++) {
			for (int col = 0; col < terrain.getColumns(); col++) {
				if ((treasures[row][col] != null) == withTreasure) {
					cells.add(new int[] { row, col });
				}
			}
		}
		return cells;
	}

	/**
	 * Calcula las acciones que le quedan al usuario
	 * @return Las acciones de la excavación.
	 */
	private int calculateActions() {
		int cells = terrain.getCells();
		double total = (cells * MIN_ACTIONS_RATE + archeologist.getExtraActions(region)) * archeologist.getActionsFactor();
		int min = (int) (cells * MIN_ACTIONS_RATE);
		int max = (int) (cells * MAX_ACTIONS_RATE);
		return Math.max(min, Math.min(max, (int) total));
	}

	// --- Letras de las columnas ---

	/**
	 * Genera las letras de las columnas
	 * @param count Cuántas hacen falta.
	 * @return Las letras, en orden.
	 */
	private static List<String> generateLetters(int count) {
		List<String> letters = new ArrayList<>();
		for (int length = 1; letters.size() < count; length++) {
			addLetters("", length, count, letters);
		}
		return letters;
	}

	/**
	 * Añade una letra a la cadena y la pasa a la siguiente llamada.
	 * Deja de añadir en cuanto la lista tiene todas las letras que hacen falta.
	 * @param prefix La cadena construida hasta ahora.
	 * @param length La longitud de las letras que se están generando.
	 * @param count Cuántas letras hacen falta en total.
	 * @param letters La lista donde se añaden.
	 */
	private static void addLetters(String prefix, int length, int count, List<String> letters) {
		if (prefix.length() == length) {
			if (letters.size() < count) {
				letters.add(prefix);
			}
			return;
		}
		for (char letter : ALPHABET.toCharArray()) {
			if (letters.size() >= count) {
				return;
			}
			addLetters(prefix + letter, length, count, letters);
		}
	}

	// --- Proceso de excavación ---

	/**
	 * Realiza la excavación por turnos hasta que se acaban las acciones o se encuentran todos
	 * los tesoros, y la finaliza mostrando la rejilla y el resumen.
	 * @return Los códigos completos de los tesoros encontrados.
	 */
	public List<String> excavate() {
		while (actions > 0 && !allFound()) {
			System.out.println();
			System.out.println("Acciones restantes: " + actions);
			if (archeologist.hasInvestigation()) {
				System.out.println("Investigación:");
				printSummary();
			}
			printGrid(false);
			for (int[] position : readPositions()) {
				if (actions == 0 || allFound()) {
					break;
				}
				dig(position[0], position[1]);
			}
		}
		if (allFound()) {
			System.out.println("¡Has encontrado todos los tesoros!");
		}
		return finish();
	}

	/**
	 * Excava una casilla: se cambia lo que ve el usuario por lo que hay escondido.
	 * Si ya estaba descubierta, no gasta acción.
	 * @param row La fila.
	 * @param col La columna.
	 */
	private void dig(int row, int col) {
		String position = (row + 1) + letters.get(col);
		if (grid[row][col] != NOT_DUG && grid[row][col] != GUESS) {
			System.out.println(position + ": ya está descubierta.");
			return;
		}
		actions--;
		grid[row][col] = getHidden(row, col);
		if (treasures[row][col] == null) {
			System.out.println(position + ": nada.");
		} else {
			found.add(treasures[row][col]);
			System.out.println(position + ": ¡tesoro " + getRarity(row, col) + "!");
		}
	}

	/**
	 * Pide las posiciones a excavar hasta que todas sean válidas.
	 * @return Las posiciones {fila, columna}.
	 */
	private List<int[]> readPositions() {
		while (true) {
			String[] tokens = InputHelper.readString("Posiciones a excavar (por ejemplo 1A 2B): ")
					.toUpperCase().split("[\\s,;]+");
			List<int[]> positions = new ArrayList<>();
			String invalid = null;
			for (String token : tokens) {
				int[] position = parsePosition(token);
				if (position == null) {
					invalid = token;
					break;
				}
				positions.add(position);
			}
			if (invalid == null && !positions.isEmpty()) {
				return positions;
			}
			System.out.println("La posición " + invalid + " no existe. Escribe el número de la fila y la letra de la columna.");
		}
	}

	/**
	 * @param token Una posición escrita por el usuario, en mayúsculas. 
	 * @return La posición o null si no es válida o está fuera de la rejilla.
	 */
	private int[] parsePosition(String token) {
		String number;
		String letter;
		Matcher matcher = NUMBER_LETTER.matcher(token);
		if (matcher.matches()) {
			number = matcher.group(1);
			letter = matcher.group(2);
		} else {
			matcher = LETTER_NUMBER.matcher(token);
			if (!matcher.matches()) {
				return null;
			}
			letter = matcher.group(1);
			number = matcher.group(2);
		}
		int col = letters.indexOf(letter);
		int row;
		try {
			row = Integer.parseInt(number) - 1;
		} catch (NumberFormatException e) {
			return null;
		}
		if (col < 0 || row < 0 || row >= terrain.getRows()) {
			return null;
		}
		return new int[] { row, col };
	}

	/**
	 * Termina la excavación: muestra el estado final de la rejilla, la rejilla descubierta
	 * y cuántos tesoros de cada rareza se han encontrado.
	 * @return Los códigos completos de los tesoros encontrados.
	 */
	private List<String> finish() {
		System.out.println();
		System.out.println("Excavación terminada");
		printGrid(false);
		printGrid(true);
		System.out.println("Se han encontrado:");
		printSummary();
		return new ArrayList<>(found);
	}

	// --- Mostrar ---

	/**
	 * Muestra la rejilla 
	 * @param uncovered true para mostrar todo lo que hay escondido; false para mostrar lo que ve el usuario.
	 */
	private void printGrid(boolean uncovered) {
		int numberWidth = String.valueOf(terrain.getRows()).length();
		int letterWidth = letters.get(letters.size() - 1).length();

		StringBuilder header = new StringBuilder(" ".repeat(numberWidth));
		for (String letter : letters) {
			header.append(' ').append(center(letter, letterWidth));
		}
		System.out.println(header.toString().stripTrailing());

		for (int row = 0; row < terrain.getRows(); row++) {
			StringBuilder line = new StringBuilder(String.valueOf(row + 1));
			line.append(" ".repeat(numberWidth - line.length()));
			for (int col = 0; col < terrain.getColumns(); col++) {
				char cell = uncovered ? getHidden(row, col) : grid[row][col];
				line.append(' ').append(center(String.valueOf(cell), letterWidth));
			}
			System.out.println(line.toString().stripTrailing());
		}
	}

	/**
	 * Centra un texto en un hueco rellenando con espacios. Si el hueco sobrante es impar,
	 * el espacio de más va a la derecha.
	 * @param text El texto.
	 * @param width El ancho del hueco.
	 * @return El texto centrado.
	 */
	private static String center(String text, int width) {
		int left = (width - text.length()) / 2;
		int right = width - text.length() - left;
		return " ".repeat(left) + text + " ".repeat(right);
	}

	/**
	 * Muestra, por cada rareza que hay en la rejilla, los tesoros encontrados frente al total.
	 * 
	 */
	private void printSummary() {
		Map<ETreasureRarity, Integer> total = new EnumMap<>(ETreasureRarity.class);
		Map<ETreasureRarity, Integer> foundByRarity = new EnumMap<>(ETreasureRarity.class);
		for (int row = 0; row < terrain.getRows(); row++) {
			for (int col = 0; col < terrain.getColumns(); col++) {
				if (treasures[row][col] != null) {
					ETreasureRarity rarity = getRarity(row, col);
					total.merge(rarity, 1, Integer::sum);
					if (grid[row][col] != NOT_DUG && grid[row][col] != GUESS) {
						foundByRarity.merge(rarity, 1, Integer::sum);
					}
				}
			}
		}
		for (Map.Entry<ETreasureRarity, Integer> entry : total.entrySet()) {
			System.out.println(foundByRarity.getOrDefault(entry.getKey(), 0) + "/" + entry.getValue()
					+ " tesoros " + getPlural(entry.getKey()) + ".");
		}
	}

	/**
	 * @param rarity La rareza.
	 * @return La rareza en plural, para el resumen. <code>comunes</code>
	 */
	private static String getPlural(ETreasureRarity rarity) {
		switch (rarity) {
			case COMMON:
				return "comunes";
			case UNCOMMON:
				return "infrecuentes";
			default:
				return "raros";
		}
	}

	// --- Consultas ---

	/**
	 * @param row La fila.
	 * @param col La columna.
	 * @return Lo que hay escondido en la casilla: la letra de la rareza del tesoro, o 'x' si no hay nada.
	 */
	private char getHidden(int row, int col) {
		return treasures[row][col] == null ? EMPTY : getRarity(row, col).getCode().charAt(0);
	}

	/**
	 * @param row La fila.
	 * @param col La columna. Debe tener un tesoro.
	 * @return La rareza del tesoro de la casilla.
	 */
	private ETreasureRarity getRarity(int row, int col) {
		return region.getTreasure(treasures[row][col]).getRarity();
	}

	/**
	 * @return true si se han encontrado todos los tesoros escondidos.
	 */
	private boolean allFound() {
		return found.size() == getCells(true).size();
	}

	/**
	 * @return La región donde se excava.
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * @return El arqueólogo que excava.
	 */
	public Archeologist getArcheologist() {
		return archeologist;
	}

	/**
	 * @return El terreno que se excava.
	 */
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	 * @return Las acciones que quedan.
	 */
	public int getActions() {
		return actions;
	}
}
