package skills;


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import excavation.Terrain;
import treasures.ETreasureRarity;


/**
 * Clase base de todas las habilidades (mejoras) de los arqueólogos.
 * Dispone de un método por cada tipo de bono posible, con un valor neutro por
 * defecto (0, lista vacía, false). Cada habilidad concreta sobrescribe
 * únicamente los bonos que le afectan, de modo que Archeologist pueda
 * recorrer su lista de habilidades y acumular cada bono sin conocer la clase
 * concreta de cada una.
 * También gestiona las restricciones de obtención: dependencias (habilidades
 * que el arqueólogo debe tener antes) e incompatibilidades (habilidades que
 * no puede tener a la vez).
 */
public abstract class Skill {


    /** Nombre de la habilidad. */
    private final String name;


    /** Descripción de la habilidad. */
    private final String description;


    /** Tipo de la habilidad. */
    private final ESkillType type;


    /** Habilidades que el arqueólogo debe poseer antes de obtener esta. */
    private final Set<Skill> dependencies = new LinkedHashSet<>();


    /** Habilidades con las que esta es incompatible. */
    private final Set<Skill> incompatibilities = new LinkedHashSet<>();


    /**
     * Constructor que establece los datos básicos de la habilidad.
     *
     * @param name        Nombre de la habilidad.
     * @param description Descripción de la habilidad.
     * @param type        Tipo de la habilidad.
     */
    protected Skill(String name, String description, ESkillType type)
    {
        this.name = name;
        this.description = description;
        this.type = type;
    }


    /**
     * Obtiene el nombre de la habilidad.
     *
     * @return El nombre.
     */
    public String getName()
    {
        return name;
    }


    /**
     * Obtiene la descripción de la habilidad.
     *
     * @return La descripción.
     */
    public String getDescription()
    {
        return description;
    }


    /**
     * Obtiene el tipo de la habilidad.
     *
     * @return El tipo.
     */
    public ESkillType getType()
    {
        return type;
    }


    /**
     * Obtiene las dependencias de la habilidad.
     *
     * @return Conjunto de solo lectura con las habilidades requeridas, en
     *         orden de inserción. Vacío si no tiene dependencias.
     */
    public Set<Skill> getDependencies()
    {
        return Collections.unmodifiableSet(dependencies);
    }


    /**
     * Obtiene las incompatibilidades de la habilidad.
     *
     * @return Conjunto de solo lectura con las habilidades incompatibles, en
     *         orden de inserción. Vacío si no tiene incompatibilidades.
     */
    public Set<Skill> getIncompatibilities()
    {
        return Collections.unmodifiableSet(incompatibilities);
    }


    /**
     * Añade una dependencia: el arqueólogo necesitará poseer la habilidad
     * indicada para poder obtener esta. Solo debe indicarse el nivel
     * inmediatamente anterior, no toda la cadena.
     *
     * @param required Habilidad requerida.
     */
    public void addDependency(Skill required)
    {
        dependencies.add(required);
    }


    /**
     * Añade una incompatibilidad. La relación se refleja en ambas
     * habilidades, por lo que no es necesario invocar el método en la otra.
     *
     * @param other Habilidad incompatible con esta.
     */
    public void addIncompatibility(Skill other)
    {
        incompatibilities.add(other);
        other.incompatibilities.add(this);
    }


    /**
     * Comprueba si un arqueólogo con las habilidades indicadas puede obtener
     * esta. Para ello no debe poseerla ya, debe tener todas sus dependencias
     * y no debe tener ninguna de sus incompatibilidades.
     *
     * @param owned Habilidades que ya posee el arqueólogo.
     * @return true si puede obtener la habilidad.
     */
    public boolean isObtainableBy(Collection<Skill> owned)
    {
        if (owned.contains(this)) {
            return false;
        }
        if (!owned.containsAll(dependencies)) {
            return false;
        }
        for (Skill inc : incompatibilities) {
            if (owned.contains(inc)) {
                return false;
            }
        }
        return true;
    }


    /*  -------------------------------------------------------------------------------------
    // Bonuses (valor neutro por defecto).Los nombres deben coordinarse con los de Archeologist. Se tiene que modificar
    */


    /**
     * Bono de acciones adicionales.
     *
     * @return Número de acciones extra que otorga. Por defecto, 0.
     */
    public int getExtraActions()
    {
        return 0;
    }


    /**
     * Bono de tesoros aleatorios adicionales.
     *
     * @return Número de tesoros aleatorios extra. Por defecto, 0.
     */
    public int getRandomTreasures()
    {
        return 0;
    }


    /**
     * Bono de tesoros adicionales de una rareza concreta.
     *
     * @param rarity Rareza consultada.
     * @return Número de tesoros extra de esa rareza. Por defecto, 0.
     */
    public int getTreasuresByRarity(ETreasureRarity rarity)
    {
        return 0;
    }


    /**
     * Bono de tesoros adicionales en un terreno concreto.
     *
     * @param terrain Terreno consultado.
     * @return Número de tesoros extra en ese terreno. Por defecto, 0.
     */
    public int getTreasuresByTerrain(Terrain terrain)
    {
        return 0;
    }


    /**
     * Terrenos que esta habilidad añade al arqueólogo (habilidades
     * modificadoras de terreno). Se va tener que invocar al obtener la habilidad.
     *
     * @return Lista de terrenos a añadir.
     */
    public List<Terrain> getTerrainsToAdd()
    {
        return Collections.emptyList();
    }


    /**
     * Proporción de posiciones de excavación que revela la habilidad
     * (habilidades de revelación). La suma total de todas las habilidades
     * no puede superar el 25 %, límite que debe aplicar quien las acumule.
     *
     * @return Proporción entre 0 y 1. Por defecto, 0.
     */
    public double getRevealedRatio()
    {
        return 0.0;
    }


    /**
     * Número de posiciones sin tesoro que se eliminan aleatoriamente antes
     * de excavar (habilidades de limpieza).
     *
     * @return Número de posiciones a eliminar. Por defecto, 0.
     */
    public int getCleanedPositions()
    {
        return 0;
    }


    /**
     * Número de grupos de suposición. Cada grupo marca con '?' tres
     * posiciones aleatorias, una con tesoro y dos vacías. Se aplican tras la
     * limpieza.
     *
     * @return Número de grupos. Por defecto, 0.
     */
    public int getGuessGroups()
    {
        return 0;
    }


    /**
     * Indica si la habilidad muestra el número y la rareza de los tesoros
     * de la rejilla en cada ronda (habilidades de investigación).
     *
     * @return true si da esa información.
     */
    public boolean givesTreasureInfo()
    {
        return false;
    }


    /**
     * Dos habilidades son iguales si tienen el mismo nombre, sin distinguir
     * mayúsculas de minúsculas.
     *
     * @param o Objeto a comparar.
     * @return true si son la misma habilidad.
     */
    @Override
    public boolean equals(Object o)
    {
        return o instanceof Skill s && s.name.equalsIgnoreCase(name);
    }


    /**
     * Metodo para la coherencia del código hash con equals(Object).
     *
     * @return El código hash basado en el nombre en minúsculas.
     */
    @Override
    public int hashCode()
    {
        return name.toLowerCase().hashCode();
    }


    /**
     * Representa la habilidad mediante su nombre.
     *
     * @return El nombre de la habilidad.
     */
    @Override
    public String toString()
    {
        return name;
    }
}

