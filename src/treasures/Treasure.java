package treasures;


/**
 * Clase que representa un tesoro dentro del juego.
 * Almacena su informacion basica, tipo, rareza y puntuacion.
 * 
 * @author David B.R
 */
public class Treasure {

    private String code;
    private String name;
    private String description;
    private ETreasureRarity rarity;
    private ETreasureType type;
    private int parts;
    private int points;

    /**
     * Constructor por defecto.
     * Inicializa las partes del tesoro a 1 por defecto.
     */
    public Treasure() {
        this.parts = 1;
    }

    /**
     * Constructor con todos los parametros.
     * 
     * @param code Identificador unico del tesoro
     * @param name Nombre del tesoro
     * @param description Breve descripcion
     * @param rarity Nivel de rareza del tesoro
     * @param type Categoria o tipo de tesoro
     * @param parts Numero de partes en las que se divide (minimo 1)
     * @param points Puntuacion que otorga
     */
    public Treasure(String code, String name, String description, ETreasureRarity rarity, ETreasureType type, int parts, int points) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.type = type;
        this.parts = parts < 1 ? 1 : parts;
        this.points = points;
    }

    /**
     * Obtiene el codigo del tesoro.
     * @return El codigo en formato String
     */
    public String getCode() { 
        return code; 
    }

    /**
     * Establece el codigo del tesoro.
     * @param code El nuevo codigo
     */
    public void setCode(String code) { 
        this.code = code; 
    }

    /**
     * Obtiene el nombre del tesoro.
     * @return El nombre
     */
    public String getName() {
        return name; 
    }

    /**
     * Establece el nombre del tesoro.
     * @param name El nuevo nombre
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripcion.
     * @return Texto descriptivo
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripcion.
     * @param description Nueva descripcion
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene la rareza del tesoro.
     * @return Objeto de tipo ETreasureRarity
     */
    public ETreasureRarity getRarity() {
        return rarity; 
    }

    /**
     * Establece la rareza del tesoro.
     * @param rarity Nueva rareza
     */
    public void setRarity(ETreasureRarity rarity) { 
        this.rarity = rarity; 
    }

    /**
     * Obtiene el tipo de tesoro.
     * @return Objeto de tipo ETreasureType
     */
    public ETreasureType getType() {
        return type; 
    }

    /**
     * Establece el tipo de tesoro.
     * @param type Nuevo tipo
     */
    public void setType(ETreasureType type) { 
        this.type = type;
    }

    /**
     * Obtiene el numero de partes del tesoro.
     * @return Cantidad de partes
     */
    public int getParts() { 
        return parts; 
    }

    /**
     * Establece el numero de partes. Se asegura de que sea al menos 1.
     * @param parts Numero de partes
     */
    public void setParts(int parts) {
        this.parts = parts < 1 ? 1 : parts; 
    }

    /**
     * Obtiene los puntos del tesoro.
     * @return Puntuacion
     */
    public int getPoints() { 
        return points;
    }

    /**
     * Establece la puntuacion del tesoro.
     * @param points Nuevos puntos
     */
    public void setPoints(int points) {
        this.points = points; 
    }
}