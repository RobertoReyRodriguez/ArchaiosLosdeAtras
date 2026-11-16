package commons.registers;

import helpers.ErrorHelper;
import helpers.LogHelper;
import java.io.UncheckedIOException;
import java.nio.file.Path;

/**
 * El registro de tesoros de una partida: un fichero de texto en <code>log/register</code> con el
 * nombre de la partida, donde se anota una línea cada vez que se consigue un tesoro.
 * <pre>
 * [aaaa-mm-dd hh:mm:ss] Inicio de la partida
 * [aaaa-mm-dd hh:mm:ss] RRR_TTT_NNN_PPPP
 * </pre>
 */
public class TreasureRegister {

	/** El directorio donde se guardan los registros de tesoros de todas las partidas. */
	public static final Path DIRECTORY = Path.of("log", "register");
	/** La extensión de los ficheros de registro. */
	private static final String EXTENSION = ".log";
	/** El mensaje que se escribe al crear la partida. */
	private static final String START_MESSAGE = "Inicio de la partida";
	/** Los caracteres que no se pueden usar en el nombre de un fichero. */
	private static final String INVALID_CHARACTERS = "[\\\\/:*?\"<>|\\p{Cntrl}]";

	/** El fichero de registro de la partida. */
	private final Path file;

	/**
	 * Crea el registro de una partida nueva: crea el fichero y anota su inicio.
	 * Si ya existía un registro con ese nombre, no lo borra y escribe a continuación.
	 * Si no se puede escribir, avisa del error pero la partida sigue adelante.
	 * @param gameName El nombre de la partida.
	 */
	public TreasureRegister(String gameName) {
		this.file = DIRECTORY.resolve(toFileName(gameName));
		write(START_MESSAGE);
	}

	/**
	 * Anota un tesoro conseguido.
	 * @param code El código completo del tesoro encontrado. <code>RRR_TTT_NNN_PPPP</code>
	 */
	public void registerTreasure(String code) {
		write(code);
	}

	/**
	 * @return El fichero donde se guarda el registro.
	 */
	public Path getFile() {
		return file;
	}

	/**
	 * Construye el nombre del fichero a partir del nombre de la partida, cambiando por
	 * guiones bajos los caracteres que los sistemas de ficheros no permiten.
	 * @param gameName El nombre de la partida.
	 * @return El nombre del fichero con extensión <code>.log</code>.
	 */
	static String toFileName(String gameName) {
		return gameName.replaceAll(INVALID_CHARACTERS, "_") + EXTENSION;
	}

	/**
	 * Escribe una línea en el fichero. Un fallo de escritura se muestra al usuario sin interrumpir la partida.
	 * @param message El texto de la línea.
	 */
	private void write(String message) {
		try {
			LogHelper.append(file, message);
		} catch (UncheckedIOException e) {
			ErrorHelper.handle(e, "No se ha podido escribir en el registro de tesoros (" + file + ").");
		}
	}
}