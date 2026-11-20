package commons;

import com.google.gson.annotations.SerializedName;
import commons.operations.ArcheologistsOperation;
import commons.operations.CatalogOperation;
import commons.operations.ExcavationOperation;
import commons.operations.IOperation;
import commons.operations.LicensesOperation;
import commons.operations.SkillsOperation;
import commons.operations.cheats.ExpCheat;
import commons.operations.cheats.HireCheat;
import commons.operations.cheats.PointsCheat;
import commons.operations.cheats.SkillCheat;
import commons.operations.cheats.TerrainsCheat;
import commons.registers.TreasureRegister;
import commons.repositories.RegionRepository;
import commons.repositories.SkillRepository;
import commons.repositories.TreasureRepository;
import helpers.ErrorHelper;
import helpers.InputHelper;
import helpers.MenuHelper;
import helpers.SaveHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import people.Archeologist;
import regions.Atlas;
import stats.Stats;
import zeraf.ESistema;
import zeraf.Zeraf;
import zeraf.ZerafFactory;

/**
 * Clase principal del sistema: contiene el main, el menú y los datos de la partida.
 * Es el objeto que se guarda en JSON. Los atributos <code>transient</code> no se guardan:
 * se vuelven a crear al cargar la partida.
 */
public class Archaios {

    /** El código del grupo otorgado por el profesor, común para toda la clase. */
    public static final String GROUP_CODE = "2DAM2627";

    /** El código identificativo y único del usuario. */
    @SerializedName("UID")
    private String uid;
    /** El nombre de la partida. */
    @SerializedName("Partida")
    private String gameName;
    /** Los arqueólogos de la partida. */
    @SerializedName("Arqueólogos")
    private List<Archeologist> archeologists = new ArrayList<>();
    /** Las estadísticas de la partida. */
    @SerializedName("Estadísticas")
    private Stats stats = new Stats();
    /** Los puntos disponibles para contratar arqueólogos. */
    @SerializedName("Puntos")
    private int points;
    /** La conexión con el Museo, de la librería Terra, para enviar los tesoros encontrados. */
    private transient Zeraf zeraf;
    /** El registro de tesoros de la partida, donde se anota cada tesoro que se consigue. */
    private transient TreasureRegister treasureRegister;

    /** Las opciones del menú principal, en el orden en que se muestran. */
    private transient List<IOperation> operations;
    /** Las opciones ocultas del menú principal (trucos para probar el sistema), por su número. */
    private transient Map<Integer, IOperation> cheats;

    public static void main(String[] args) {
        preInit();
        Archaios archaios = init();
        archaios.run();
        archaios.end();
    }

    /**
     * Registra los elementos del sistema en sus repositorios antes de arrancar.
     */
    private static void preInit() {
        ErrorHelper.installGlobalHandler();

        RegionRepository.getInstance().populate();
        TreasureRepository.getInstance().populate();
        SkillRepository.getInstance().populate();
    }

    /**
     * Inicializa el sistema. Si hay partidas guardadas, pregunta si se quiere cargar alguna
     * o empezar una nueva. Si no hay, o se elige nueva, o no se puede cargar la elegida, crea una nueva.
     * @return La partida cargada o creada.
     */
    private static Archaios init() {
        System.out.println("=== ARCHAIOS ===");

        Archaios archaios = null;
        List<File> saves = SaveHelper.getSaves();
        if (!saves.isEmpty()) {
            System.out.println("Partidas guardadas:");
            File save = MenuHelper.choose(saves, SaveHelper::getName, "Elige una partida: ", "Nueva partida");
            if (save != null) {
                archaios = load(save);
            }
        }
        if (archaios == null) {
            archaios = create();
        }
        archaios.createOperations();
        return archaios;
    }

    /**
     * Crea una partida nueva pidiendo el código del usuario, el nombre de la partida y el del
     * primer arqueólogo, y la guarda. El nombre no puede ser el de una partida ya guardada.
     * @return La partida creada.
     */
    private static Archaios create() {
        System.out.println("Nueva partida");

        Archaios archaios = new Archaios();
        archaios.uid = InputHelper.readString("Código de usuario: ");
        archaios.zeraf = ZerafFactory.createZeraf(ESistema.MUSEO, archaios.uid, GROUP_CODE);
        archaios.gameName = InputHelper.readString("Nombre de la partida: ");
        while (SaveHelper.exists(archaios.gameName)) {
            System.out.println("Ya hay una partida guardada con ese nombre.");
            archaios.gameName = InputHelper.readString("Nombre de la partida: ");
        }
        archaios.treasureRegister = new TreasureRegister(archaios.gameName);
        archaios.archeologists.add(new Archeologist(InputHelper.readString("Nombre del primer arqueólogo: ")));
        archaios.save();
        return archaios;
    }

    /**
     * Carga una partida guardada y vuelve a crear lo que no se guarda: la conexión con el Museo
     * y el registro de tesoros, que sigue escribiendo en el fichero de la partida.
     * @param file El fichero de la partida.
     * @return La partida cargada, o null si no se ha podido cargar.
     */
    private static Archaios load(File file) {
        Archaios archaios = SaveHelper.load(file);
        if (archaios == null) {
            return null;
        }
        archaios.zeraf = ZerafFactory.createZeraf(ESistema.MUSEO, archaios.uid, GROUP_CODE);
        archaios.treasureRegister = new TreasureRegister(archaios.gameName, false);
        for (Archeologist archeologist : archaios.archeologists) {
            archeologist.restoreTerrains();
        }
        System.out.println("Partida " + archaios.gameName + " cargada.");
        return archaios;
    }

    /**
     * Guarda la partida en su fichero JSON 
     */
    public void save() {
        SaveHelper.save(this);
    }

    /**
     * Crea las opciones del menú principal y las ocultas (trucos del 95 al 99).
     * Cada una recibe la partida si necesita sus datos.
     */
    private void createOperations() {
        operations = new ArrayList<>();
        operations.add(new ArcheologistsOperation(this));
        operations.add(new SkillsOperation());
        operations.add(new LicensesOperation(this));
        operations.add(new Atlas());
        operations.add(new CatalogOperation());
        operations.add(new ExcavationOperation(this));
        operations.add(stats);

        cheats = new LinkedHashMap<>();
        cheats.put(99, new PointsCheat(this));
        cheats.put(98, new ExpCheat(this));
        cheats.put(97, new SkillCheat(this));
        cheats.put(96, new TerrainsCheat(this));
        cheats.put(95, new HireCheat(this));
    }

    /**
     * Muestra el menú principal y ejecuta la opción elegida hasta que el usuario sale.
     * Si se cierra la entrada (Ctrl+Z en Windows, Ctrl+D en Linux), sale del programa
     */
    private void run() {
        try {
            IOperation operation = menu();
            while (operation != null) {
                System.out.println();
                System.out.println("--- " + operation.getName() + " ---");
                try {
                    operation.operation();
                } catch (RuntimeException e) {
                    if (InputHelper.isClosed()) {
                        throw e;
                    }
                    ErrorHelper.handle(e);
                }
                operation = menu();
            }
        } catch (RuntimeException e) {
            if (!InputHelper.isClosed()) {
                throw e;
            }
            System.out.println();
            System.out.println("Programa cerrado por el usuario.");
        }
    }

    /**
     * Muestra el texto del menú principal y pide una opción.
     * @return La opción elegida, o null si el usuario elige salir.
     */
    private IOperation menu() {
        System.out.println();
        System.out.println("=== " + gameName + " === Puntos: " + points);
        return MenuHelper.choose(operations, IOperation::getName, "Elige una opción: ", "Salir", cheats);
    }

    /**
     * Cierra el sistema, liberando los recursos que ya no se necesitan.
     */
    private void end() {
        InputHelper.close();
        System.out.println("¡Hasta pronto!");
    }

    /**
     * @return El código del usuario.
     */
    public String getUid() {
        return uid;
    }

    /**
     * @return El nombre de la partida.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @return Los arqueólogos de la partida. Se puede modificar para contratar o licenciar.
     */
    public List<Archeologist> getArcheologists() {
        return archeologists;
    }

    /**
     * @return Las estadísticas de la partida.
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @return La conexión con el Museo.
     */
    public Zeraf getZeraf() {
        return zeraf;
    }

    /**
     * @return El registro de tesoros de la partida.
     */
    public TreasureRegister getTreasureRegister() {
        return treasureRegister;
    }

    /**
     * @return Los puntos disponibles.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Suma puntos a la partida, por ejemplo al terminar una excavación o al licenciar.
     * @param amount Los puntos a sumar. No puede ser negativo.
     * @throws IllegalArgumentException Si la cantidad es negativa.
     */
    public void addPoints(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("No se pueden sumar puntos negativos: " + amount);
        }
        points += amount;
    }

    /**
     * Gasta puntos de la partida
     * @param amount Los puntos a gastar.
     * @return true si había puntos suficientes y se han gastado; false si no, y no se gasta nada.
     */
    public boolean spendPoints(int amount) {
        if (amount < 0 || amount > points) {
            return false;
        }
        points -= amount;
        return true;
    }
}
