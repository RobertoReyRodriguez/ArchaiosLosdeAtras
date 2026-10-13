package people;

import excavation.Terrain;
import java.util.ArrayList;
import java.util.List;
import skills.Skill;

/**
 * Los arqueólogos de la partida. De momento solo guarda sus datos básicos;
 * niveles, habilidades, contratar y licenciar llegan en el paso 5.
 */
public class Archeologist {

    /** El nombre del arqueólogo. */
    private String name;
    /** El nivel actual, de 0 a 5. */
    private int level;
    /** La experiencia actual. */
    private int exp;
    /** Los tipos de terreno que puede excavar. */
    private List<Terrain> terrains = new ArrayList<>();
    /** Las habilidades de las que dispone. */
    private List<Skill> skills = new ArrayList<>();

    //metodos para obtener cada uno de los bonos de la lista de habilidades.
    //metodo verArqueologos(String nombre)
    //metodo contratarArqueologo() cuesta 50 puntos y genera uno aleatorio
    //metodo Licenciar() elimina arqueologos con compensacion dependiendo de la rareza.

    /**
     * Crea un arqueólogo nuevo a nivel 0, sin experiencia ni habilidades.
     * @param name El nombre del arqueólogo.
     */
    public Archeologist(String name) {
        this.name = name;
    }

    /**
     * @return El nombre del arqueólogo.
     */
    public String getName() {
        return name;
    }

    /**
     * @return El nivel actual.
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return La experiencia actual.
     */
    public int getExp() {
        return exp;
    }

    @Override
    public String toString() {
        return name + " (Nv " + level + ")";
    }
}
