package regions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import commons.repositories.RegionRepository;
import java.lang.reflect.Type;

/**
 * Adaptador de GSON para las regiones: en el JSON cada región se guarda solo con su código,
 * y al cargar se recupera la del repositorio. Así no se guardan todos sus tesoros, y la región
 * cargada es el mismo objeto que la del sistema.
 * Sin región (un arqueólogo sin especializar) se guarda un texto vacío.
 */
public class RegionAdapter implements JsonSerializer<Region>, JsonDeserializer<Region> {

	@Override
	public JsonElement serialize(Region src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src == null ? "" : src.getCode());
	}

	@Override
	public Region deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (json == null || json.isJsonNull() || json.getAsString().isEmpty()) {
			return null;
		}
		Region region = RegionRepository.getInstance().get(json.getAsString());
		if (region == null) {
			throw new JsonParseException("No existe la región " + json.getAsString() + ".");
		}
		return region;
	}
}
