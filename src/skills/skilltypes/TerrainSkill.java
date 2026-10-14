package skills.skilltypes;
import java.util.List;

import excavation.Terrain;
import skills.ESkillType;
import skills.Skill;
/*Clase para la habilidad que modifica los terrenos del arquelologo. Provisional aun hay que modificarla */
public class TerrainSkill extends Skill {
    private final List<Terrain> terrains;
    public TerrainSkill(String name, String desc, List<Terrain> terrains) {
        super(name, desc, ESkillType.TERRAIN);
        this.terrains = List.copyOf(terrains);
    }
    @Override public List<Terrain> getTerrainsToAdd() { return terrains; }
}
