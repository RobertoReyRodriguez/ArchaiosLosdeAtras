package skills;

import excavation.ETerrainShape;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Habilidades que añaden formas de terreno al arqueólogo.
 * No dan ningún bono: al obtenerse, el arqueólogo añade sus terrenos a su lista.
 */
public class TerrainSkill extends Skill {

	/** Las formas de terreno que añade. */
	private final List<ETerrainShape> terrains;

	/**
	 * @param name El nombre de la habilidad.
	 * @param description La descripción.
	 * @param terrains Las formas de terreno que añade.
	 */
	public TerrainSkill(String name, String description, ETerrainShape... terrains) {
		super(name, description);
		this.terrains = Arrays.asList(terrains);
	}

	@Override
	public ESkillType getType() {
		return ESkillType.TERRAIN;
	}

	@Override
	public List<ETerrainShape> getTerrains() {
		return Collections.unmodifiableList(terrains);
	}
}
