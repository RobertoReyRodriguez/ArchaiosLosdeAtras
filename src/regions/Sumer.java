package regions;

/**
 * Región difícil de Sumeria, de la expansión Imperios de las arenas. Sus terrenos son 30% piedra, 50% arena y 20% tierra.
 * 
 */
public final class Sumer extends Region implements IDifficultRegion {

	/** El código de la región. */
	public static final String CODE = "SUM";

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
}
