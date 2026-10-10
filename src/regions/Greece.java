package regions;

import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Región de Grecia. 
 */
public final class Greece extends Region {

	/** El código de la región. */
	public static final String CODE = "GRC";

	public Greece() {
		super("Grecia");
		// Monedas
		addTreasure(new Treasure("GRC_MON_001", "Calco griego",
				"Moneda griega de cobre que tenía el menor valor.",
				ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
		addTreasure(new Treasure("GRC_MON_002", "Óbolo griego",
				"Moneda griega de plata de poco valor.",
				ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
		addTreasure(new Treasure("GRC_MON_003", "Dracma griego",
				"Moneda griega de plata ampliamente usada.",
				ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));
		addTreasure(new Treasure("GRC_MON_004", "Estatero",
				"Moneda griega de gran valor hecha de electro.",
				ETreasureRarity.UNCOMMON, ETreasureType.COIN, 1, 2));

		// Teselas
		addTreasure(new Treasure("GRC_TES_001", "Mosaico de Poseidón",
				"Mosaico inspirado en el de la Casa de los Delfines de Delos.",
				ETreasureRarity.COMMON, ETreasureType.TILE, 625, 1));
		addTreasure(new Treasure("GRC_TES_002", "Mosaico geométrico",
				"Mosaico geométrico en dos colores, típico de los primeros siglos de Grecia.",
				ETreasureRarity.COMMON, ETreasureType.TILE, 625, 1));

		// Cuentas
		addTreasure(new Treasure("GRC_CUE_001", "Cuenta de vidrio",
				"Pequeños trozos de vidrio pulido que se utilizaban en collares y pulseras.",
				ETreasureRarity.COMMON, ETreasureType.BEAD, 1, 1));
		addTreasure(new Treasure("GRC_CUE_002", "Cuenta de cerámica",
				"Pequeños trozos de cerámica pintada a mano que se utilizaban en collares y pulseras.",
				ETreasureRarity.COMMON, ETreasureType.BEAD, 1, 1));

		// Joyería
		addTreasure(new Treasure("GRC_JOY_001", "Fíbula omega",
				"Hebilla para sujetar la ropa similar a un imperdible.",
				ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 3, 2));
		addTreasure(new Treasure("GRC_JOY_002", "Anillos",
				"Anillos de oro, plata y bronce adornados con grabados e incrustaciones de gemas.",
				ETreasureRarity.RARE, ETreasureType.JEWELRY, 4, 5));
		addTreasure(new Treasure("GRC_JOY_003", "Brazal",
				"Brazal de oro decorado con filigranas y gemas.",
				ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 7, 3));

		// Cerámica
		addTreasure(new Treasure("GRC_CER_001", "Ánfora de Nola",
				"Ánfora típica de la región de Nola con un cuello más largo y estrecho.",
				ETreasureRarity.UNCOMMON, ETreasureType.CERAMIC, 34, 2));
		addTreasure(new Treasure("GRC_CER_002", "Ánfora nicosténica",
				"Ánfora creada en el siglo VI a.C. por Nicóstenes que imita la forma etrusca.",
				ETreasureRarity.UNCOMMON, ETreasureType.CERAMIC, 18, 3));
		addTreasure(new Treasure("GRC_CER_003", "Ánfora panatenaica",
				"Ánfora que contenía el aceite que se le daba como premio a los ganadores de los Juegos Panatenaicos.",
				ETreasureRarity.RARE, ETreasureType.CERAMIC, 30, 5));
		addTreasure(new Treasure("GRC_CER_004", "Calpis",
				"Vaso griego utilizado para almacenar agua.",
				ETreasureRarity.COMMON, ETreasureType.CERAMIC, 22, 1));
		addTreasure(new Treasure("GRC_CER_005", "Crátera",
				"Vasija de gran capacidad usada para guardar la mezcla de agua y vino.",
				ETreasureRarity.COMMON, ETreasureType.CERAMIC, 20, 1));
		addTreasure(new Treasure("GRC_CER_006", "Enócoe",
				"Vasija con un asa usada para servir el vino.",
				ETreasureRarity.COMMON, ETreasureType.CERAMIC, 16, 1));
		addTreasure(new Treasure("GRC_CER_007", "Estamno",
				"Vasija para conservar el vino con forma de globo y asas horizontales.",
				ETreasureRarity.RARE, ETreasureType.CERAMIC, 20, 5));
		addTreasure(new Treasure("GRC_CER_008", "Hidria",
				"Vasija para servir y guardar agua con tres asas, dos a los lados y una a modo de jarra.",
				ETreasureRarity.COMMON, ETreasureType.CERAMIC, 16, 1));

		// Equipo
		addTreasure(new Treasure("GRC_EQP_001", "Aspis",
				"Escudo circular de bronce, madera y cuero utilizado por los hoplitas griegos.",
				ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 28, 3));
		addTreasure(new Treasure("GRC_EQP_002", "Casco corintio",
				"Casco de bronce que cubre toda la cabeza y el cuello.",
				ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 30, 4));
		addTreasure(new Treasure("GRC_EQP_003", "Casco frigio",
				"Casco de bronce con una protuberancia en la parte superior y dos placas laterales para la cara.",
				ETreasureRarity.RARE, ETreasureType.EQUIP, 21, 6));
		addTreasure(new Treasure("GRC_EQP_004", "Xifos",
				"Espada de bronce utilizada por los hoplitas griegos.",
				ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 25, 4));
	}

	@Override
	public String getCode() {
		return CODE;
	}
}
