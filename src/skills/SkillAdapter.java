package skills;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import commons.repositories.SkillRepository;
import java.lang.reflect.Type;

/**
 * Adaptador de GSON para las habilidades: en el JSON cada habilidad se guarda solo con su nombre,
 * al cargar se recupera la del repositorio. 
 */
public class SkillAdapter implements JsonSerializer<Skill>, JsonDeserializer<Skill> {

	@Override
	public JsonElement serialize(Skill src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getName());
	}

	@Override
	public Skill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Skill skill = SkillRepository.getInstance().get(json.getAsString());
		if (skill == null) {
			throw new JsonParseException("No existe la habilidad " + json.getAsString() + ".");
		}
		return skill;
	}
}
