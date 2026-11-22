package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import commons.Archaios;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sistema de guardado: guarda y carga las partidas en ficheros JSON 
 */
public final class SaveHelper {

	/** El directorio donde se guardan las partidas. */
	public static final File DIRECTORY = new File("saves");
	/** La extensión de los ficheros de guardado. */
	private static final String EXTENSION = ".save";
	/** Los caracteres que no se pueden usar en el nombre de un fichero. */
	private static final String INVALID_CHARACTERS = "[\\\\/:*?\"<>|\\p{Cntrl}]";
	/** El conversor de GSON. Con sangría, para que el fichero se pueda leer. */
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private SaveHelper() {
	}

	/**
	 * Guarda la partida en su fichero, sustituyendo lo que hubiera.
	 * Si no se puede guardar, avisa del error pero la partida sigue adelante.
	 * @param archaios La partida a guardar.
	 */
	public static void save(Archaios archaios) {
		File file = getFile(archaios.getGameName());
		DIRECTORY.mkdirs();
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
			writer.write(GSON.toJson(archaios));
		} catch (IOException e) {
			ErrorHelper.handle(e, "No se ha podido guardar la partida (" + file + ").");
		}
	}

	/**
	 * Carga una partida guardada.
	 * Si no se puede leer o el fichero no es una partida válida, avisa del error.
	 * @param file El fichero de la partida.
	 * @return La partida cargada, o null si no se ha podido cargar.
	 */
	public static Archaios load(File file) {
		try (JsonReader reader = new JsonReader(
				new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")))) {
			Archaios archaios = GSON.fromJson(reader, Archaios.class);
			if (archaios == null || archaios.getUid() == null || archaios.getGameName() == null) {
				throw new JsonParseException("El fichero " + file + " no es una partida guardada.");
			}
			return archaios;
		} catch (IOException | JsonParseException e) {
			ErrorHelper.handle(e, "No se ha podido cargar la partida " + getName(file) + ".");
			return null;
		}
	}

	/**
	 * @return Los ficheros de las partidas guardadas, por orden alfabético. Vacía si no hay ninguna.
	 */
	public static List<File> getSaves() {
		File[] files = DIRECTORY.listFiles((directory, name) -> name.endsWith(EXTENSION));
		if (files == null) {
			return new ArrayList<>();
		}
		Arrays.sort(files);
		return new ArrayList<>(Arrays.asList(files));
	}

	/**
	 * @param gameName El nombre de la partida.
	 * @return true si ya hay una partida guardada con ese nombre.
	 */
	public static boolean exists(String gameName) {
		return getFile(gameName).exists();
	}

	/**
	 * @param file El fichero de una partida.
	 * @return El nombre del fichero sin la extensión, para mostrarlo en el menú.
	 */
	public static String getName(File file) {
		String name = file.getName();
		return name.substring(0, name.length() - EXTENSION.length());
	}

	/**
	 * Construye el fichero de una partida a partir de su nombre, cambiando por
	 * guiones bajos los caracteres que los sistemas de ficheros no permiten.
	 * @param gameName El nombre de la partida.
	 * @return El fichero de la partida dentro de saves.
	 */
	private static File getFile(String gameName) {
		return new File(DIRECTORY, gameName.replaceAll(INVALID_CHARACTERS, "_") + EXTENSION);
	}
}
