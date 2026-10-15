package helpers;

/**
 * Formato común de los textos que se muestran por pantalla.
 */
public final class TextHelper {

	/** Guiones a cada lado del título de una cabecera. */
	private static final String HEADER_SIDE = "--------------------";

	private TextHelper() {
	}

	/**
	 * Construye una cabecera con el título centrado entre guiones.
	 * -------------------- Título --------------------
	 * @param title El título.
	 * @return La cabecera.
	 */
	public static String header(String title) {
		return HEADER_SIDE + " " + title + " " + HEADER_SIDE;
	}

	/**
	 * Rellena un texto con espacios por la derecha hasta una longitud, para alinear columnas.
	 * @param text El texto.
	 * @param length La longitud deseada. Si el texto ya es más largo, se devuelve tal cual.
	 * @return El texto relleno.
	 */
	public static String padRight(String text, int length) {
		StringBuilder result = new StringBuilder(text);
		while (result.length() < length) {
			result.append(' ');
		}
		return result.toString();
	}
}
