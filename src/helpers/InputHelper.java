package helpers;


import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

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
	 * @return El texto leído.
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
	 * @return El número leído
	 * @throws IllegalArgumentException Si min es mayor que max
	 */
	public static int readInt(String prompt, int min, int max) {
		return readInt(prompt, min, max, Collections.emptySet());
	}

	/**
	 * Lee un número entero dentro de un rango o que sea uno de los números ocultos,
	 * repitiendo la pregunta hasta obtenerlo. El mensaje de error solo menciona el rango,
	 * para que los números ocultos no se vean.
	 * @param prompt El texto que se muestra antes de leer.
	 * @param min El valor mínimo permitido, incluido.
	 * @param max El valor máximo permitido, incluido.
	 * @param hidden Números permitidos fuera del rango que no se le muestran al usuario.
	 * @return El número leído, dentro del rango o de los ocultos.
	 * @throws IllegalArgumentException Si min es mayor que max.
	 */
	public static int readInt(String prompt, int min, int max, Set<Integer> hidden) {
		if (min > max) {
			throw new IllegalArgumentException("Rango inválido: " + min + " > " + max);
		}
		while (true) {
			String text = readLine(prompt);
			try {
				int value = Integer.parseInt(text);
				if ((value >= min && value <= max) || hidden.contains(value)) {
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
	 * @return true si la respuesta es afirmativa, false si es negativa.
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