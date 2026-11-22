package regions;

/**
 * Las expansiones del sistema, para saber de dónde viene cada región difícil.
 */
public enum EExpansion {
	SANDS("Imperios de las arenas");

	/** El nombre de la expansión. */
	private final String txt;

	/**
	 * @param txt El nombre de la expansión.
	 */
	private EExpansion(String txt) {
		this.txt = txt;
	}

	/**
	 * @return El nombre de la expansión. <code>Imperios de las arenas</code>
	 */
	@Override
	public String toString() {
		return txt;
	}
}
