package people;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import commons.repositories.RegionRepository;
import commons.repositories.SkillRepository;
import excavation.ETerrainShape;
import excavation.ETerrainSize;
import helpers.MenuHelper;
import helpers.RandomHelper;
import regions.Region;
import skills.Skill;
import treasures.ETreasureRarity;

/**
 * Los arqueólogos de la partida: su nivel, experiencia, terrenos y habilidades,
 * y los bonos que estas les dan.
 */
public class Archeologist {

    /** El nivel máximo. Al alcanzarlo, el arqueólogo se especializa en una región. */
    public static final int MAX_LEVEL = 5;
    /** El número de habilidades entre las que se elige al subir de nivel. */
    private static final int SKILL_OPTIONS = 3;

    /** El nombre del arqueólogo. */
    private String name;
    /** La rareza con la que se contrató. */
    private ETreasureRarity rarity;
    /** El nivel actual, de 0 a 5. */
    private int level;
    /** La experiencia dentro del nivel actual. A nivel máximo, la obtenida a mayores. */
    private int exp;
    /** Las formas de terreno que puede excavar. */
    private List<ETerrainShape> terrains = new ArrayList<>();
    /** Las habilidades de las que dispone. */
    private List<Skill> skills = new ArrayList<>();
    /** La región en la que se especializa al llegar a nivel máximo. Null hasta entonces. */
    private Region specialization;

    /**
     * Crea un arqueólogo nuevo a nivel 0, sin experiencia ni habilidades.
     * Empieza pudiendo excavar solo terrenos cuadrados.
     * @param name El nombre del arqueólogo.
     * @param rarity La rareza con la que se contrata.
     */
    public Archeologist(String name, ETreasureRarity rarity) {
        this.name = name;
        this.rarity = rarity;
        this.terrains.add(ETerrainShape.SQUARE);
    }

    /**
     * Crea un arqueólogo común, como el primero de la partida.
     * @param name El nombre del arqueólogo.
     */
    public Archeologist(String name) {
        this(name, ETreasureRarity.COMMON);
    }

    // --- Datos ---

    /**
     * @return El nombre del arqueólogo.
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre. Se usa al contratar, ya que el nombre se pide después de mostrar al arqueólogo.
     * @param name El nuevo nombre.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return La rareza con la que se contrató.
     */
    public ETreasureRarity getRarity() {
        return rarity;
    }

    /**
     * @return El nivel actual.
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return La experiencia dentro del nivel actual, o la obtenida a mayores si está a nivel máximo.
     */
    public int getExp() {
        return exp;
    }

    /**
     * @return La experiencia necesaria para subir del nivel actual al siguiente: 50 más 50 por nivel.
     */
    public int getExpToLevelUp() {
        return 50 + 50 * level;
    }

    /**
     * @return true si está a nivel máximo.
     */
    public boolean isMaxLevel() {
        return level >= MAX_LEVEL;
    }

    /**
     * @return La región de especialización, o null si todavía no ha llegado a nivel máximo.
     */
    public Region getSpecialization() {
        return specialization;
    }

    /**
     * @return Las formas de terreno que puede excavar. No se puede modificar.
     */
    public List<ETerrainShape> getTerrains() {
        return Collections.unmodifiableList(terrains);
    }

    /**
     * @return Los tamaños de terreno que puede excavar según su nivel.
     */
    public List<ETerrainSize> getTerrainSizes() {
        List<ETerrainSize> sizes = new ArrayList<>();
        for (ETerrainSize size : ETerrainSize.values()) {
            if (level >= size.getMinLevel()) {
                sizes.add(size);
            }
        }
        return sizes;
    }

    /**
     * @return Las habilidades del arqueólogo. No se puede modificar.
     */
    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    /**
     * @return Las habilidades con las que empieza al contratarlo: 0 si es común, 1 si es infrecuente
     * y 2 si es raro. Coincide con la posición de la rareza en el enumerado.
     */
    public int getInitialSkills() {
        return rarity.ordinal();
    }

    /**
     * @return Los puntos que se obtienen al licenciarlo: 10 por nivel más 10 por la posición de su
     * rareza en el enumerado (0 si es común, 10 si es infrecuente y 20 si es raro).
     */
    public int getLicensePoints() {
        return 10 * level + 10 * rarity.ordinal();
    }

    // --- Habilidades ---

    /**
     * Añade una habilidad. Si es de terreno, añade también sus terrenos a la lista.
     * @param skill La habilidad.
     */
    public void addSkill(Skill skill) {
        skills.add(skill);
        for (ETerrainShape terrain : skill.getTerrains()) {
            addTerrain(terrain);
        }
    }

    /**
     * Añade una forma de terreno si todavía no la tiene. Lo usan las habilidades de terreno
     * y el truco que desbloquea todos los terrenos.
     * @param terrain La forma de terreno.
     */
    public void addTerrain(ETerrainShape terrain) {
        if (!terrains.contains(terrain)) {
            terrains.add(terrain);
        }
    }

    /**
     * Indica si el arqueólogo puede obtener una habilidad: no la tiene, tiene todas
     * sus dependencias y no tiene ninguna incompatible con ella.
     * @param skill La habilidad.
     * @return true si puede obtenerla.
     */
    public boolean canLearn(Skill skill) {
        if (skills.contains(skill) || !skills.containsAll(skill.getDependencies())) {
            return false;
        }
        for (Skill incompatible : skill.getIncompatibilities()) {
            if (skills.contains(incompatible)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Todas las habilidades que el arqueólogo puede obtener ahora mismo.
     */
    public List<Skill> getLearnableSkills() {
        List<Skill> result = new ArrayList<>();
        for (Skill skill : SkillRepository.getAll()) {
            if (canLearn(skill)) {
                result.add(skill);
            }
        }
        return result;
    }

    /**
     * Elige las habilidades que se ofrecen al subir de nivel: tres al azar entre las que puede obtener.
     * La primera es una mejora de las que ya tiene (una que dependa de ellas), si hay alguna.
     * @return Hasta tres habilidades distintas. Menos si no hay tantas disponibles.
     */
    public List<Skill> getSkillOptions() {
        List<Skill> learnable = getLearnableSkills();
        List<Skill> upgrades = new ArrayList<>();
        for (Skill skill : learnable) {
            if (!skill.getDependencies().isEmpty()) {
                upgrades.add(skill);
            }
        }

        List<Skill> options = new ArrayList<>();
        Skill first = upgrades.isEmpty() ? RandomHelper.randomElement(learnable) : RandomHelper.randomElement(upgrades);
        if (first != null) {
            options.add(first);
            learnable.remove(first);
        }
        options.addAll(RandomHelper.randomElements(learnable, SKILL_OPTIONS - options.size()));
        return options;
    }

    /**
     * Añade habilidades al azar entre las que puede obtener. Se usa al contratar.
     * Se añaden de una en una para que se respeten las dependencias e incompatibilidades.
     * @param amount El número de habilidades.
     */
    public void addRandomSkills(int amount) {
        for (int i = 0; i < amount; i++) {
            Skill skill = RandomHelper.randomElement(getLearnableSkills());
            if (skill != null) {
                addSkill(skill);
            }
        }
    }

    // --- Experiencia y niveles ---

    /**
     * Suma experiencia y sube los niveles que correspondan. Por cada nivel el usuario
     * elige obligatoriamente una habilidad, y al llegar a nivel máximo elige su especialización.
     * @param amount La experiencia ganada.
     */
    public void addExp(int amount) {
        exp += amount;
        while (!isMaxLevel() && exp >= getExpToLevelUp()) {
            exp -= getExpToLevelUp();
            level++;
            System.out.println("El arqueólogo " + name + " ha subido al nivel " + level + ".");
            learnNewSkill();
            if (isMaxLevel()) {
                specialize();
            }
        }
    }

    /**
     * Ofrece tres habilidades y el usuario aprende una. No hay opción de salir.
     */
    private void learnNewSkill() {
        List<Skill> options = getSkillOptions();
        if (options.isEmpty()) {
            System.out.println("No quedan habilidades que pueda aprender.");
            return;
        }
        if (options.size() == 1) {
            System.out.println("Puede aprender esta habilidad:");
        } else {
            System.out.println("Puede aprender una de estas " + (options.size() == 3 ? "tres" : "dos") + " habilidades:");
        }
        Skill skill = MenuHelper.chooseRequired(options, s -> s.getName() + " | " + s.getDescription(), "Habilidad a aprender: ");
        addSkill(skill);
    }

    /**
     * El usuario elige la región en la que se especializa el arqueólogo. No hay opción de salir.
     */
    private void specialize() {
        System.out.println("El arqueólogo " + name + " ha alcanzado el nivel máximo y se especializa en una región:");
        specialization = MenuHelper.chooseRequired(RegionRepository.getAll(), Region::getName, "Región de especialización: ");
    }

    // --- Bonos: suman los de todas las habilidades y, en su región, los de la especialización ---

    /**
     * @param region La región de la excavación.
     * @return Los tesoros extra de rareza aleatoria.
     */
    public int getExtraRandomTreasures(Region region) {
        int total = isSpecializedIn(region) ? exp / 50 : 0;
        for (Skill skill : skills) {
            total += skill.getExtraRandomTreasures();
        }
        return total;
    }

    /**
     * @param region La región de la excavación.
     * @param rarity La rareza.
     * @return Los tesoros extra de esa rareza.
     */
    public int getExtraTreasures(Region region, ETreasureRarity rarity) {
        int total = isSpecializedIn(region) ? exp / getSpecializationStep(rarity) : 0;
        for (Skill skill : skills) {
            total += skill.getExtraTreasures(rarity);
        }
        return total;
    }

    /**
     * @param rarity La rareza.
     * @return Los puntos de porcentaje que se suman a la probabilidad de esa rareza.
     */
    public int getProbabilityBonus(ETreasureRarity rarity) {
        int total = 0;
        for (Skill skill : skills) {
            total += skill.getProbabilityBonus(rarity);
        }
        return total;
    }

    /**
     * @param region La región de la excavación.
     * @return Las acciones extra de excavación.
     */
    public int getExtraActions(Region region) {
        int total = isSpecializedIn(region) ? exp / 25 : 0;
        for (Skill skill : skills) {
            total += skill.getExtraActions();
        }
        return total;
    }

    /**
     * @return El factor de aumento de acciones: los de todas las habilidades multiplicados entre sí.
     */
    public double getActionsFactor() {
        double factor = 1;
        for (Skill skill : skills) {
            factor *= skill.getActionsFactor();
        }
        return factor;
    }

    /**
     * @return Las casillas vacías que se quitan antes de excavar.
     */
    public int getCleanedCells() {
        int total = 0;
        for (Skill skill : skills) {
            total += skill.getCleanedCells();
        }
        return total;
    }

    /**
     * @return Los grupos de tres '?' que se ponen en la rejilla.
     */
    public int getGuesses() {
        int total = 0;
        for (Skill skill : skills) {
            total += skill.getGuesses();
        }
        return total;
    }

    /**
     * @return true si alguna habilidad muestra cuántos tesoros de cada rareza hay.
     */
    public boolean hasInvestigation() {
        for (Skill skill : skills) {
            if (skill.hasInvestigation()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param region La región.
     * @return true si está especializado en esa región.
     */
    private boolean isSpecializedIn(Region region) {
        return specialization != null && specialization == region;
    }

    /**
     * @param rarity La rareza.
     * @return La experiencia a mayores necesaria para cada tesoro extra de esa rareza:
     * 50 para los comunes, 100 para los infrecuentes y 200 para los raros.
     */
    private int getSpecializationStep(ETreasureRarity rarity) {
        switch (rarity) {
            case COMMON:
                return 50;
            case UNCOMMON:
                return 100;
            default:
                return 200;
        }
    }

    @Override
    public String toString() {
        return name + " (Nv " + level + ")";
    }
}
