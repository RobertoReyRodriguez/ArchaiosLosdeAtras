package regions;

/**
 * Región difícil de Petra, de la expansión Imperios de las arenas. Sus terrenos son 75% piedra y 25% arena.
 * 
 */
public final class Petra extends Region implements IDifficultRegion {

	/** El código de la región. */
	public static final String CODE = "PTR";

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
}
