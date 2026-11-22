package regions;

import java.util.Map;

import excavation.EGround;

/**
 * Región difícil de Petra, de la expansión Imperios de las arenas. Sus terrenos son 75% piedra y 25% arena.
 * 
 */
public final class Petra extends Region implements IDifficultRegion {

	/** El código de la región. */
	public static final String CODE = "PTR";

	/** La proporción de cada terreno en las excavaciones de Petra, en porcentaje. */
	private static final Map<EGround, Integer> GROUNDS = Map.of(EGround.STONE, 75, EGround.SAND, 25);

	public Petra() {
		super("Petra");
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
