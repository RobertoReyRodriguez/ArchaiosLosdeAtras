package regions;

import java.util.Map;

import excavation.EGround;

/**
 * Región difícil de Egipto, de la expansión Imperios de las arenas. Sus terrenos son 10% agua, 35% tierra y 55% arena.
 * Sus tesoros se cargan de <code>data/regions/EGP.json</code> en el preInit.
 */
public final class Egypt extends Region implements IDifficultRegion {

	/** El código de la región. */
	public static final String CODE = "EGP";

	/** La proporción de cada terreno en las excavaciones de Egipto, en porcentaje. */
	private static final Map<EGround, Integer> GROUNDS = Map.of(EGround.WATER, 10, EGround.EARTH, 35, EGround.SAND, 55);

	public Egypt() {
		super("Egipto");
	}

	@Override
	public String getCode() {
		return CODE;
	}

	@Override
	public EExpansion getExpansion() {
		return EExpansion.SANDS;
	}

	@Override
	public Map<EGround, Integer> getGroundProportions() {
		return GROUNDS;
	}
}
