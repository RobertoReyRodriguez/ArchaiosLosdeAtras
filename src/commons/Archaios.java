package commons;

import commons.operations.ArcheologistsOperation;
import commons.operations.CatalogOperation;
import commons.operations.ExcavationOperation;
import commons.operations.IOperation;
import commons.operations.LicensesOperation;
import commons.operations.SkillsOperation;
import commons.repositories.RegionRepository;
import helpers.ErrorHelper;
import helpers.InputHelper;
import helpers.MenuHelper;
import java.util.ArrayList;
import java.util.List;
import people.Archeologist;
import regions.Atlas;
import regions.Greece;
import regions.Iberia;
import regions.Rome;
import skills.BaseSkills;
import stats.Stats;
import zeraf.ESistema;
import zeraf.Zeraf;
import zeraf.ZerafFactory;

/**
 * Clase principal del sistema: contiene el main, el menú y los datos de la partida.
 */
public class Archaios {

    /** El código del grupo otorgado por el profesor, común para toda la clase. */
    public static final String GROUP_CODE = "2DAM2627";

    /** El código identificativo y único del usuario. */
    private String uid;
    /** El nombre de la partida. */
    private String gameName;
    /** Los arqueólogos de la partida. */
    private List<Archeologist> archeologists = new ArrayList<>();
    /** Las estadísticas de la partida. */
    private Stats stats = new Stats();
    /** Los puntos disponibles para contratar arqueólogos. */
    private int points;
    /** La conexión con el Museo, de la librería Terra, para enviar los tesoros encontrados. */
    private Zeraf zeraf;

    /** Las opciones del menú principal, en el orden en que se muestran. */
    private List<IOperation> operations;

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

        RegionRepository.register(new Greece());
        RegionRepository.register(new Iberia());
        RegionRepository.register(new Rome());

        BaseSkills.register();
    }

    /**
     * Inicializa el sistema creando una partida nueva.
     * La carga de partidas guardadas es parte del sistema de guardado (segunda entrega).
     * @return La partida creada.
     */
    private static Archaios init() {
        System.out.println("=== ARCHAIOS ===");
        System.out.println("Nueva partida");

        Archaios archaios = new Archaios();
        archaios.uid = InputHelper.readString("Código de usuario: ");
        archaios.zeraf = ZerafFactory.createZeraf(ESistema.MUSEO, archaios.uid, GROUP_CODE);
        archaios.gameName = InputHelper.readString("Nombre de la partida: ");
        archaios.archeologists.add(new Archeologist(InputHelper.readString("Nombre del primer arqueólogo: ")));
        archaios.createOperations();
        return archaios;
    }

    /**
     * Crea las opciones del menú principal. Cada una recibe la partida si necesita sus datos.
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
    }

    /**
     * Muestra el menú principal y ejecuta la opción elegida hasta que el usuario sale.
     */
    private void run() {
        IOperation operation = menu();
        while (operation != null) {
            System.out.println();
            System.out.println("--- " + operation.getName() + " ---");
            try {
                operation.operation();
            } catch (RuntimeException e) {
                ErrorHelper.handle(e);
            }
            operation = menu();
        }
    }

    /**
     * Muestra el texto del menú principal y pide una opción.
     * @return La opción elegida, o null si el usuario elige salir.
     */
    private IOperation menu() {
        System.out.println();
        System.out.println("=== " + gameName + " === Puntos: " + points);
        return MenuHelper.choose(operations, IOperation::getName, "Elige una opción: ", "Salir");
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
     * Gasta puntos de la partida, por ejemplo al contratar.
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
