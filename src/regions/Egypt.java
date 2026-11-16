package regions;

/**
 * Región difícil de Egipto, de la expansión Imperios de las arenas. Sus terrenos son 10% agua, 35% tierra y 55% arena.
 * Sus tesoros se cargan de <code>data/regions/EGP.json</code> en el preInit.
 */
public final class Egypt extends Region implements IDifficultRegion {

	/** El código de la región. */
	public static final String CODE = "EGP";

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
}
