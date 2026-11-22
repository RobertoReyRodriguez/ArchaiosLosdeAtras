package helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Escritura de ficheros de registro de texto, una línea por entrada con su marca de tiempo.
 * [aaaa-mm-dd hh:mm:ss] Mensaje
 */
public final class LogHelper {

	/** El registro de errores, único y compartido por todas las partidas. */
	public static final Path ERRORS_FILE = Path.of("log", "errors.log");

	/** El formato de la marca de tiempo. */
	private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private LogHelper() {
	}

	/**
	 * Anota un error en el registro de errores  la descripción de la
	 * excepción con la fecha y hora, y a continuación su pila de trazabilidad, incluidas las causas.
	 * @param error El error producido.
	 * @throws UncheckedIOException Si no se puede escribir en el fichero.
	 */
	public static void logError(Throwable error) {
		StringWriter trace = new StringWriter();
		error.printStackTrace(new PrintWriter(trace));
		append(ERRORS_FILE, trace.toString().stripTrailing());
	}

	/**
	 * Añade una línea al final de un fichero de registro con la fecha y hora actuales.
	 * Si el fichero o sus directorios no existen, los crea. Nunca borra lo que ya había escrito.
	 * @param file El fichero de registro.
	 * @param message El mensaje a escribir.
	 * @throws UncheckedIOException Si no se puede escribir en el fichero.
	 */
	public static void append(Path file, String message) {
		String line = "[" + LocalDateTime.now().format(TIMESTAMP) + "] " + message + System.lineSeparator();
		try {
			Path directory = file.getParent();
			if (directory != null) {
				Files.createDirectories(directory);
			}
			Files.writeString(file, line, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
