package helpers;

import java.util.List;
import java.util.function.Function;

/**
 * Menús numerados para elegir un elemento de una lista.
 */
public final class MenuHelper {

	/** Texto por defecto de la opción 0. */
	private static final String DEFAULT_EXIT = "Volver";
	/** Separador entre el número y el texto de cada opción. */
	private static final String SEPARATOR = ".- ";
	/** Salto de línea del sistema. */
	private static final String NL = System.lineSeparator();

	private MenuHelper() {
	}

	/**
	 * Muestra un menú con la opción 0 "Volver" al final y devuelve el elemento elegido.
	 * @param items Los elementos a elegir.
	 * @param labeler Cómo se muestra cada elemento.
	 * @param prompt El texto de la pregunta.
	 * @return El elemento elegido, o null si se elige la opción 0.
	 */
	public static <T> T choose(List<T> items, Function<? super T, String> labeler, String prompt) {
		return choose(items, labeler, prompt, DEFAULT_EXIT);
	}

	/**
	 * Muestra un menú con la opción 0 al final y devuelve el elemento elegido.
	 * @param items Los elementos a elegir.
	 * @param labeler Cómo se muestra cada elemento.
	 * @param prompt El texto de la pregunta.
	 * @param exitLabel El texto de la opción 0.
	 * @return El elemento elegido, o null si se elige la opción 0.
	 */
	public static <T> T choose(List<T> items, Function<? super T, String> labeler, String prompt, String exitLabel) {
		if (items.isEmpty()) {
			System.out.println("No hay elementos disponibles.");
		}
		print(items, labeler, exitLabel);
		int option = InputHelper.readInt(prompt, 0, items.size());
		return option == 0 ? null : items.get(option - 1);
	}

	/**
	 * Muestra un menú sin opción de salida y devuelve el elemento elegido obligatoriamente.
	 * @param items Los elementos a elegir. No puede estar vacía.
	 * @param labeler Cómo se muestra cada elemento.
	 * @param prompt El texto de la pregunta.
	 * @return El elemento elegido.
	 * @throws IllegalArgumentException Si la lista está vacía.
	 */
	public static <T> T chooseRequired(List<T> items, Function<? super T, String> labeler, String prompt) {
		if (items.isEmpty()) {
			throw new IllegalArgumentException("Un menú obligatorio necesita al menos un elemento.");
		}
		print(items, labeler, null);
		return items.get(InputHelper.readInt(prompt, 1, items.size()) - 1);
	}

	/**
	 * Construye y muestra las opciones con los números alineados a la derecha.
	 * @param exitLabel El texto de la opción 0, o null si no hay opción de salida.
	 */
	private static <T> void print(List<T> items, Function<? super T, String> labeler, String exitLabel) {
		int width = String.valueOf(items.size()).length();
		StringBuilder menu = new StringBuilder();
		int number = 1;
		for (T item : items) {
			appendOption(menu, number++, width, labeler.apply(item));
		}
		if (exitLabel != null) {
			appendOption(menu, 0, width, exitLabel);
		}
		System.out.print(menu);
	}

	/**
	 * Añade una línea de opción al menú, rellenando el número con espacios hasta el ancho indicado.
	 */
	private static void appendOption(StringBuilder menu, int number, int width, String text) {
		String num = String.valueOf(number);
		for (int i = num.length(); i < width; i++) {
			menu.append(' ');
		}
		menu.append(num).append(SEPARATOR).append(text).append(NL);
	}
}
