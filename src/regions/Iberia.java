package regions;

/**
 * Región básica de Iberia. Sus tesoros se cargan de <code>data/regions/IBR.json</code> en el preInit.
 */
public final class Iberia extends Region {

	/** El código de la región. */
	public static final String CODE = "IBR";

	public Iberia() {
		super("Iberia");
	}

	@Override
	public String getCode() {
		return CODE;
	}
}
