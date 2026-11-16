package regions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Carga los tesoros de las regiones desde sus ficheros JSON, para no tener que escribirlos a mano
 * en el código. Cada región tiene su fichero con su código como nombre: <code>data/regions/GRC.json</code>.
 */
public final class RegionLoader {

	/** La carpeta de los ficheros de las regiones, relativa a la carpeta desde la que se ejecuta. */
	public static final String FOLDER = "data" + File.separator + "regions";

	private RegionLoader() {
	}

	/**
	 * Lee el fichero de la región y le añade sus tesoros.
	 * @param region La región, todavía sin tesoros.
	 * @return La misma región, ya con sus tesoros, para poder registrarla a continuación.
	 * @throws IllegalStateException Si no se puede leer el fichero. Sin las regiones no se puede jugar.
	 */
	public static Region load(Region region) {
		File file = new File(FOLDER, region.getCode() + ".json");
		try (JsonReader reader = new JsonReader(
				new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")))) {
			JsonArray treasures = JsonParser.parseReader(reader).getAsJsonArray();
			for (JsonElement element : treasures) {
				Treasure treasure = toTreasure(element.getAsJsonObject());
				region.addTreasure(treasure);
			}
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Error en la codificación al leer " + file.getPath() + ".", e);
		} catch (IOException e) {
			throw new IllegalStateException("No se ha podido leer " + file.getPath() + ".", e);
		}
		return region;
	}

	/**
	 * Crea un tesoro a partir de su objeto JSON.
	 * <code>{"code": "GRC_MON_001", "name": "Calco griego", "description": "...", "rarity": "C", "parts": 1, "points": 1, "type": "MON"}</code>
	 * @param json El objeto JSON del tesoro.
	 * @return El tesoro.
	 */
	private static Treasure toTreasure(JsonObject json) {
		return new Treasure(
				json.get("code").getAsString(),
				json.get("name").getAsString(),
				json.get("description").getAsString(),
				getRarity(json.get("rarity").getAsString()),
				getType(json.get("type").getAsString()),
				json.get("parts").getAsInt(),
				json.get("points").getAsInt());
	}

	/**
	 * Busca la rareza por su código. No se usa <code>ETreasureRarity.getByCode</code> porque compara
	 * los textos con <code>==</code>, y un texto leído de un fichero nunca es el mismo objeto que el
	 * del enumerado, así que siempre devolvería null.
	 * @param code El código de la rareza. <code>C</code>
	 * @return La rareza.
	 * @throws IllegalArgumentException Si no hay ninguna rareza con ese código.
	 */
	private static ETreasureRarity getRarity(String code) {
		for (ETreasureRarity rarity : ETreasureRarity.values()) {
			if (rarity.getCode().equals(code)) {
				return rarity;
			}
		}
		throw new IllegalArgumentException("La rareza " + code + " no existe.");
	}

	/**
	 * Busca el tipo por su código, por el mismo motivo que {@link #getRarity(String)}.
	 * @param code El código del tipo. <code>MON</code>
	 * @return El tipo.
	 * @throws IllegalArgumentException Si no hay ningún tipo con ese código.
	 */
	private static ETreasureType getType(String code) {
		for (ETreasureType type : ETreasureType.values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("El tipo " + code + " no existe.");
	}
}
