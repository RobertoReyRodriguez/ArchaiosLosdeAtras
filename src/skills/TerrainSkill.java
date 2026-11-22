package skills;

import excavation.ETerrain;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Habilidades que desbloquean terrenos al arqueólogo.
 * No dan ningún bono: al obtenerse, el arqueólogo añade sus terrenos a su lista.
 */
public class TerrainSkill extends Skill {

	/** Los terrenos que desbloquea. */
	private final List<ETerrain> terrains;

	/**
	 * @param name El nombre de la habilidad.
	 * @param description La descripción.
	 * @param terrains Los terrenos que desbloquea.
	 */
	public TerrainSkill(String name, String description, ETerrain... terrains) {
		super(name, description);
		this.terrains = Arrays.asList(terrains);
	}

	@Override
	public ESkillType getType() {
		return ESkillType.TERRAIN;
	}

	@Override
	public List<ETerrain> getTerrains() {
		return Collections.unmodifiableList(terrains);
	}
}
