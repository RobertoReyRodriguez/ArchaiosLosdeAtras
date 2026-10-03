package helpers;

/**
 * Gestión centralizada de los errores del sistema.
 */
public final class ErrorHelper {

	/** Mensaje que se muestra cuando no se indica uno concreto. */
	private static final String DEFAULT_MESSAGE = "Se ha producido un error inesperado.";
	/** Prefijo de los mensajes de error mostrados al usuario. */
	private static final String PREFIX = "Error: ";

	/** Si está activo, se muestra la traza completa de cada error. Solo para pruebas. */
	private static boolean debug = false;

	private ErrorHelper() {
	}

	/**
	 * Activa o desactiva la muestra de la traza completa de los errores.
	 * @param enabled true para mostrarla.
	 */
	public static void setDebug(boolean enabled) {
		debug = enabled;
	}

	/**
	 * Hace que cualquier error no capturado del programa pase también por este helper.
	 * Debe llamarse una vez, al arrancar el sistema.
	 */
	public static void installGlobalHandler() {
		Thread.setDefaultUncaughtExceptionHandler((thread, error) -> handle(error));
	}

	/**
	 * Gestiona un error mostrando el mensaje genérico.
	 * @param error El error producido.
	 */
	public static void handle(Throwable error) {
		handle(error, DEFAULT_MESSAGE);
	}

	/**
	 * Gestiona un error: muestra un mensaje comprensible al usuario y lo registra.
	 * @param error El error producido.
	 * @param userMessage El mensaje para el usuario. Si es nulo o vacío, se usa el genérico.
	 */
	public static void handle(Throwable error, String userMessage) {
		boolean noMessage = userMessage == null || userMessage.isEmpty();
		System.out.println(PREFIX + (noMessage ? DEFAULT_MESSAGE : userMessage));
		register(error);
	}

	/**
	 * Registra el error. 
	 * @param error El error producido.
	 */
	private static void register(Throwable error) {
		if (debug) {
			error.printStackTrace();
		}
		//  Esto es para la segunda entrega: escribir en log/errors.log con LogHelper ahora mismo este método está de placeholder.
	}
}