package regions;

import java.util.Map;

import excavation.EGround;

/**
 * Región difícil de Sumeria, de la expansión Imperios de las arenas. Sus terrenos son 30% piedra, 50% arena y 20% tierra.
 * 
 */
public final class Sumer extends Region implements IDifficultRegion {

	/** El código de la región. */
	public static final String CODE = "SUM";

	/** La proporción de cada terreno en las excavaciones de Sumeria, en porcentaje. */
	private static final Map<EGround, Integer> GROUNDS = Map.of(EGround.STONE, 30, EGround.SAND, 50, EGround.EARTH, 20);

	public Sumer() {
		super("Sumeria");
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
