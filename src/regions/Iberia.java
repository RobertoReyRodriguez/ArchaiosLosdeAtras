package regions;

import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Región de Iberia. 
 */
public final class Iberia extends Region {

	/** El código de la región. */
	public static final String CODE = "IBR";

	public Iberia() {
		super("Iberia");
		// Monedas
		addTreasure(new Treasure("IBR_MON_001", "Denario ibérico",
				"Moneda de plata con influencia griega y fenicia.",
				ETreasureRarity.COMMON, ETreasureType.COIN, 1, 1));

		// Teselas
		addTreasure(new Treasure("IBR_TES_001", "Mosaico punteado",
				"Mosaico punteado en dos colores creando formas geométricas. Inspirado en el de la Casa de Likine.",
				ETreasureRarity.COMMON, ETreasureType.TILE, 625, 1));

		// Cuentas
		addTreasure(new Treasure("IBR_CUE_001", "Cuentas de hueso",
				"Hueso pulido con diversas formas y tamaños para formar parte de un colgante.",
				ETreasureRarity.COMMON, ETreasureType.BEAD, 1, 1));

		// Joyería
		addTreasure(new Treasure("IBR_JOY_001", "Fíbula La Tène",
				"Hebilla para sujetar la ropa con forma de arco con un lado vuelto sobre sí mismo.",
				ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 3, 2));
		addTreasure(new Treasure("IBR_JOY_002", "Fíbula anular",
				"Hebilla para sujetar la ropa con forma de anillo.",
				ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 3, 2));
		addTreasure(new Treasure("IBR_JOY_003", "Fíbula de caballito",
				"Hebilla con forma de caballo que presumiblemente llevaban los guerreros.",
				ETreasureRarity.RARE, ETreasureType.JEWELRY, 6, 6));
		addTreasure(new Treasure("IBR_JOY_004", "Brazaletes",
				"Brazaletes de plata decorados con animales.",
				ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 5, 2));
		addTreasure(new Treasure("IBR_JOY_005", "Torques ártabro",
				"Torques de oro y plata con remates piriformes típico de Gallaecia.",
				ETreasureRarity.UNCOMMON, ETreasureType.JEWELRY, 5, 3));
		addTreasure(new Treasure("IBR_JOY_006", "Torques de carrete",
				"Torques de oro con remates en forma de carrete típico de Gallaecia.",
				ETreasureRarity.RARE, ETreasureType.JEWELRY, 5, 5));
		addTreasure(new Treasure("IBR_JOY_007", "Torques castrense",
				"Torques de oro con filigrana decorativa y terminado en formas trapezoidales. Típico de los castros de Gallaecia.",
				ETreasureRarity.RARE, ETreasureType.JEWELRY, 5, 6));
		addTreasure(new Treasure("IBR_JOY_008", "Tésera de hospitalidad",
				"Pieza metálica con forma de jabalí que simbolizaba un pacto de hospitalidad entre grupos.",
				ETreasureRarity.RARE, ETreasureType.JEWELRY, 6, 6));

		// Cerámica
		addTreasure(new Treasure("IBR_CER_001", "Kálathos íbero",
				"Vasija recta con forma de sombrero de copa que se usaba como recipiente para el transporte de mercancía.",
				ETreasureRarity.COMMON, ETreasureType.CERAMIC, 25, 1));
		addTreasure(new Treasure("IBR_CER_002", "Urna de orejetas",
				"Urna emblemática de la cerámica ibérica utilizada como urna funeraria gracias a su cierre hermético.",
				ETreasureRarity.COMMON, ETreasureType.CERAMIC, 21, 1));
		addTreasure(new Treasure("IBR_CER_003", "Vaso bitroncocónico",
				"Urna funeraria encontrada en los túmulos de la Edad de Bronce.",
				ETreasureRarity.UNCOMMON, ETreasureType.CERAMIC, 14, 2));
		addTreasure(new Treasure("IBR_CER_004", "Olla castreña",
				"Olla amplia adornada con relieves geométricos típica de los castros del noroeste de la península.",
				ETreasureRarity.UNCOMMON, ETreasureType.CERAMIC, 20, 3));
		addTreasure(new Treasure("IBR_CER_005", "Ánfora ibérica",
				"Ánfora ibérica con influencia fenicia usada para el transporte de líquidos.",
				ETreasureRarity.COMMON, ETreasureType.CERAMIC, 24, 1));
		addTreasure(new Treasure("IBR_CER_006", "Vaso campaniforme",
				"Cerámica arcaica con forma de campana y decorada con diversas líneas talladas.",
				ETreasureRarity.UNCOMMON, ETreasureType.CERAMIC, 20, 2));

		// Equipo
		addTreasure(new Treasure("IBR_EQP_001", "Falcata",
				"La espada íbera por excelencia. Filo de hierro con una característica curvatura y mango con forma de animal.",
				ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 23, 4));
		addTreasure(new Treasure("IBR_EQP_002", "Espada de antenas",
				"Espada celtíbera de la Edad de Bronce con un característico mango terminado en dos espirales o antenas.",
				ETreasureRarity.UNCOMMON, ETreasureType.EQUIP, 25, 3));
		addTreasure(new Treasure("IBR_EQP_003", "Soliferrum",
				"Lanza íbera hecha de hierro en su totalidad para una alta penetración de las protecciones del enemigo.",
				ETreasureRarity.RARE, ETreasureType.EQUIP, 10, 6));
		addTreasure(new Treasure("IBR_EQP_004", "Cardiophylax",
				"Armadura primitiva hecha de una plancha de bronce que cubre el corazón y unas correas de cuero que la sujetan.",
				ETreasureRarity.RARE, ETreasureType.EQUIP, 8, 6));

		// Estelas
		addTreasure(new Treasure("IBR_EST_001", "Estela funeraria",
				"Estela de piedra tallada con inscripciones que se ponía en los enterramientos nobles.",
				ETreasureRarity.UNCOMMON, ETreasureType.STELE, 48, 5));

		// Fragmentos
		addTreasure(new Treasure("IBR_FRA_001", "Ídolo cilíndrico oculado",
				"Estatuilla con grandes ojos tallada en alabastro que representa a una deidad.",
				ETreasureRarity.UNCOMMON, ETreasureType.FRAGMENT, 4, 3));
		addTreasure(new Treasure("IBR_FRA_002", "Ídolo oculado simple",
				"Estatuilla oculada de piedra tallada con simples rasgos representando una deidad.",
				ETreasureRarity.UNCOMMON, ETreasureType.FRAGMENT, 3, 3));
		addTreasure(new Treasure("IBR_FRA_003", "Ídolo de Garrovillas",
				"Placa antropomórfica y oculada tallada en piedra que representa a una deidad antigua.",
				ETreasureRarity.RARE, ETreasureType.FRAGMENT, 4, 6));
		addTreasure(new Treasure("IBR_FRA_004", "Ídolo de placa",
				"Placa de pizarra, trapezoidal, oculada y tallada con motivos geométricos.",
				ETreasureRarity.UNCOMMON, ETreasureType.FRAGMENT, 3, 2));
	}

	@Override
	public String getCode() {
		return CODE;
	}
}
