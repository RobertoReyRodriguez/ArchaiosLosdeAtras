package treasures;
import java.util.Objects;
import helpers.RandomHelper;
import helpers.UtilsHelper;


/**
 * Clase que representa un tesoro dentro del juego.
 * Almacena su informacion basica, tipo, rareza y puntuacion.
 * 
 * @author David B.R
 */
public class Treasure implements Comparable<Treasure> {

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
     * Comprueba si el tipo de tesoro es de género femenino.
     * @return true si es femenino, false si es masculino.
     */
    public boolean isFeminine() {
        if (this.type == null) {
            return false;
        }
        return this.type == ETreasureType.COIN
                || this.type == ETreasureType.TILE
                || this.type == ETreasureType.BEAD
                || this.type == ETreasureType.JEWELRY
                || this.type == ETreasureType.CERAMIC
                || this.type == ETreasureType.STELE;
    }

    /**
     * Devuelve la rareza formateada según el género del tipo de tesoro.
     * 
     * @return Rareza formateada ("rara" o "raro", etc.).
     */
    public String getFormattedRarity() {
        if (this.rarity == null) {
            return "";
        }
        if (this.rarity == ETreasureRarity.RARE) {
            return isFeminine() ? "rara" : "raro";
        }
        return this.rarity.toString();
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

    /**
     * Genera el código completo del tesoro seleccionando una parte
     * de forma aleatoria entre 1 y el total de partes disponibles.
     *
     * @return El codigo completo formateado (RRR_TTT_NNN_PPPP).
     */
    public String generateRandomPartCode() {
        int randomPart = RandomHelper.randomInt(1, this.parts);
        return getFullPartCode(randomPart);
    }

    /**
     * Devuelve el código completo añadiendo el numero de parte especificado
     * formateado a 4 digitos con ceros a la izquierda.
     *
     * @param partNumber Numero de la parte a formatear.
     * @return El codigo formateado (ejemplo: GRC_MON_001_0005).
     */
    public String getFullPartCode(int partNumber) {
        return String.format("%s_%04d", this.code, partNumber);
    }

    public String getCatalogDetails() {
        String typeStr = (this.type != null) ? UtilsHelper.ucfirst(this.type.toString()) : "";
        
        String result = "-------------------- " + this.name + " --------------------\n"
                + this.description + "\n"
                + "Código: " + this.code + "\n"
                + typeStr + " " + getFormattedRarity();

        if (this.parts > 1) {
            String wordDivided = isFeminine() ? "dividida" : "dividido";
            result += " " + wordDivided + " en " + this.parts + " partes";
        }

        return result;
    }

     /**
     * Devuelve los datos formateados del tesoro encontrado tras una excavacion.
     * @param fullPartCode El codigo completo que incluye la parte encontrada.
     * @return Texto formateado para mostrar en la excavacion.
     */
  public String getFoundDetails(String fullPartCode) {
        String result = this.name + "\n";
        result += this.description + "\n";
        result += "Código: " + fullPartCode + "\n";
        result += this.type + " " + getFormattedRarity();
        return result;
    }
    
    /**
    * Devuelve una representacion textual del tesoro con su nombre y codigo base.
    * Ejemplo: "Calco griego (GRC_MON_001)"
    * 
     * @return El nombre y codigo del tesoro en formato legible.
    */
    public String toString() {
        return this.name + " (" + this.code + ")";
    }

    /**
     * Compara este tesoro con otro basándose en su código.
     * Permite ordenar las listas de tesoros alfabéticamente por código.
     * 
     * @param other El otro tesoro a comparar.
     * @return Un entero negativo, cero o positivo si este código es menor,
     *         igual o mayor que el del otro tesoro.
     */

    @Override
    public int compareTo(Treasure other) {
        if (other == null || other.getCode() == null) {
            return 1;
        }
        if (this.code == null) {
            return -1;
        }
        return this.code.compareTo(other.getCode());
    }
}
