package regions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helpers.CodeHelper;
import helpers.RandomHelper;
import treasures.ETreasureRarity;
import treasures.Treasure;

/**
 * Base de las regiones en las que se realizan las excavaciones.
 * Cada región guarda sus propios tesoros, que son únicos de ella.
 */
public abstract class Region {

	/** El nombre de la región que se muestra al usuario. */
	private final String name;
	/** Los tesoros de la región, en el orden en que se añaden. */
	private final List<Treasure> treasures = new ArrayList<>();

	/**
	 * @param name El nombre de la región que se muestra al usuario.
	 */
	protected Region(String name) {
		this.name = name;
	}

	/**
	 * @return El código de la región. <code>RRR</code>
	 */
	public abstract String getCode();

	/**
	 * @return El nombre de la región.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Añade un tesoro a la región. Solo lo usan las regiones al crearse.
	 * @param treasure El tesoro a añadir.
	 * @throws IllegalArgumentException Si el tesoro no es de esta región o ya existe.
	 */
	protected void addTreasure(Treasure treasure) {
		if (!CodeHelper.getRegion(treasure.getCode()).equals(getCode())) {
			throw new IllegalArgumentException("El tesoro " + treasure.getCode() + " no pertenece a " + name + ".");
		}
		if (getTreasure(treasure.getCode()) != null) {
			throw new IllegalArgumentException("El tesoro " + treasure.getCode() + " está repetido en " + name + ".");
		}
		treasures.add(treasure);
	}

	/**
	 * @return Todos los tesoros de la región. La lista no se puede modificar.
	 */
	public List<Treasure> getTreasures() {
		return Collections.unmodifiableList(treasures);
	}

	/**
	 * @param rarity La rareza buscada.
	 * @return Los tesoros de la región con esa rareza. Puede estar vacía.
	 */
	public List<Treasure> getTreasures(ETreasureRarity rarity) {
		List<Treasure> result = new ArrayList<>();
		for (Treasure treasure : treasures) {
			if (treasure.getRarity() == rarity) {
				result.add(treasure);
			}
		}
		return result;
	}

	/**
	 * @return Un tesoro aleatorio de la región, o null si no tiene tesoros.
	 */
	public Treasure getRandomTreasure() {
		return RandomHelper.randomElement(treasures);
	}

	/**
	 * @param rarity La rareza buscada.
	 * @return Un tesoro aleatorio de esa rareza, o null si la región no tiene ninguno.
	 */
	public Treasure getRandomTreasure(ETreasureRarity rarity) {
		return RandomHelper.randomElement(getTreasures(rarity));
	}

	/**
	 * Busca un tesoro de la región por su código.
	 * @param code El código del tesoro, con o sin la parte. <code>RRR_TTT_NNN[_PPPP]</code>
	 * @return El tesoro, o null si no está en la región.
	 */
	public Treasure getTreasure(String code) {
		String base = CodeHelper.getFullTreasure(code);
		for (Treasure treasure : treasures) {
			if (treasure.getCode().equals(base)) {
				return treasure;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return name;
	}
}
