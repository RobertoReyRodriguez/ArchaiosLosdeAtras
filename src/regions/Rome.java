package regions;

/**
 * Región básica de Roma. Sus tesoros se cargan de <code>data/regions/ROM.json</code> en el preInit.
 */
public final class Rome extends Region {

	/** El código de la región. */
	public static final String CODE = "ROM";

	public Rome() {
		super("Roma");
	}

	@Override
	public String getCode() {
		return CODE;
	}
}
