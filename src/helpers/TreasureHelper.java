package helpers;

import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Textos para mostrar los tesoros, con el tipo y la rareza concordando en género.
 */
public final class TreasureHelper {

	private TreasureHelper() {
	}

	/**
	 * Indica si el nombre del tipo es femenino: la moneda, la tesela... frente a el equipo, el fragmento.
	 * @param type El tipo de tesoro.
	 * @return true si es femenino.
	 */
	public static boolean isFeminine(ETreasureType type) {
		switch (type) {
			case EQUIP:
			case FRAGMENT:
				return false;
			default:
				return true;
		}
	}

	/**
	 * Devuelve la rareza concordando en género. Solo cambia "raro", ya que "común" e "infrecuente" no varían.
	 * @param rarity La rareza.
	 * @param feminine true si debe ir en femenino.
	 * @return La rareza en el género indicado.
	 */
	public static String getRarityText(ETreasureRarity rarity, boolean feminine) {
		if (rarity == ETreasureRarity.RARE && feminine) {
			return "rara";
		}
		return rarity.toString();
	}

	/**
	 * Construye la frase del tipo y la rareza. <code>Cerámica común</code>
	 * @param treasure El tesoro.
	 * @return La frase.
	 */
	public static String getTypeAndRarity(Treasure treasure) {
		return UtilsHelper.ucfirst(treasure.getType().toString()) + " "
				+ getRarityText(treasure.getRarity(), isFeminine(treasure.getType()));
	}

	/**
	 * Construye la ficha completa de un tesoro, como se muestra en el catálogo.
	 * Las partes solo aparecen si son más de una. <code>Cerámica común dividida en 10 partes</code>
	 * @param treasure El tesoro.
	 * @return La ficha: cabecera con el nombre, descripción, código y tipo con rareza.
	 */
	public static String getDetail(Treasure treasure) {
		String nl = System.lineSeparator();
		String detail = TextHelper.header(treasure.getName()) + nl
				+ treasure.getDescription() + nl
				+ "Código: " + treasure.getCode() + nl
				+ getTypeAndRarity(treasure);
		if (treasure.getParts() > 1) {
			detail += (isFeminine(treasure.getType()) ? " dividida" : " dividido") + " en " + treasure.getParts() + " partes";
		}
		return detail;
	}
	/**
	 * Construye la ficha de un tesoro encontrado en una excavación, de forma similar al catálogo
	 * pero con el código de la parte concreta encontrada.
	 * @param treasure El tesoro.
	 * @param partCode El código completo de la parte encontrada. <code>RRR_TTT_NNN_PPPP</code>
	 * @return La ficha: nombre, descripción, código de la parte y tipo con rareza.
	 */
	public static String getFoundDetail(Treasure treasure, String partCode) {
		String nl = System.lineSeparator();
		return treasure.getName() + nl
				+ treasure.getDescription() + nl
				+ "Código: " + partCode + nl
				+ getTypeAndRarity(treasure);
	}
}
