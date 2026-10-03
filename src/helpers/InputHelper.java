package helpers;


import java.util.Locale;
import java.util.Scanner;

/**
 * Lectura y validación de las entradas del usuario por consola.
 */
public final class InputHelper {

	/** Única fuente de lectura de la entrada estándar en todo el sistema. */
	private static final Scanner SCANNER = new Scanner(System.in);

	private InputHelper() {
	}

	/**
	 * Lee una línea tal cual la escribe el usuario, sin espacios en los extremos.
	 * @param prompt El texto que se muestra antes de leer.
	 * @return La línea leída. Puede estar vacía.
	 * @throws IllegalStateException Si la entrada estándar se ha cerrado.
	 */
	public static String readLine(String prompt) {
		System.out.print(prompt);
		if (!SCANNER.hasNextLine()) {
			throw new IllegalStateException("La entrada estándar se ha cerrado.");
		}
		return SCANNER.nextLine().trim();
	}

	/**
	 * Lee un texto no vacío, repitiendo la pregunta hasta obtenerlo.
	 * @param prompt El texto que se muestra antes de leer.
	 * @return El texto leído, nunca vacío.
	 */
	public static String readString(String prompt) {
		String text = readLine(prompt);
		while (text.isEmpty()) {
			System.out.println("El texto no puede estar vacío.");
			text = readLine(prompt);
		}
		return text;
	}

	/**
	 * Lee un número entero dentro de un rango, repitiendo la pregunta hasta obtenerlo.
	 * @param prompt El texto que se muestra antes de leer.
	 * @param min El valor mínimo permitido, incluido.
	 * @param max El valor máximo permitido, incluido.
	 * @return El número leído, dentro de [<code>min</code>, <code>max</code>].
	 * @throws IllegalArgumentException Si <code>min</code> es mayor que <code>max</code>.
	 */
	public static int readInt(String prompt, int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("Rango inválido: " + min + " > " + max);
		}
		while (true) {
			String text = readLine(prompt);
			try {
				int value = Integer.parseInt(text);
				if (value >= min && value <= max) {
					return value;
				}
			} catch (NumberFormatException e) {
				// Se trata igual que un número fuera de rango.
			}
			System.out.println("Introduce un número entre " + min + " y " + max + ".");
		}
	}

	/**
	 * Lee una confirmación sí/no, repitiendo la pregunta hasta obtener una respuesta válida.
	 * Acepta s, si, sí, n y no, sin distinguir mayúsculas.
	 * @param prompt El texto que se muestra antes de leer, sin el indicador (s/n).
	 * @return <code>true</code> si la respuesta es afirmativa, <code>false</code> si es negativa.
	 */
	public static boolean readYesNo(String prompt) {
		String fullPrompt = prompt + " (s/n): ";
		while (true) {
			switch (readLine(fullPrompt).toLowerCase(Locale.ROOT)) {
				case "s":
				case "si":
				case "sí":
					return true;
				case "n":
				case "no":
					return false;
				default:
					System.out.println("Responde s o n.");
			}
		}
	}

	/**
	 * Cierra la lectura de la entrada estándar. Solo debe llamarse al cerrar el sistema.
	 */
	public static void close() {
		SCANNER.close();
	}
}