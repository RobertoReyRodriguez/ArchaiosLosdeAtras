package regions;

/**
 * Región básica de Grecia. Sus tesoros se cargan de <code>data/regions/GRC.json</code> en el preInit.
 */
public final class Greece extends Region {

	/** El código de la región. */
	public static final String CODE = "GRC";

	public Greece() {
		super("Grecia");
	}

	@Override
	public String getCode() {
		return CODE;
	}
}
