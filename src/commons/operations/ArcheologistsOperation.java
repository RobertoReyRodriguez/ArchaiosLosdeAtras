package commons.operations;

import commons.Archaios;
import excavation.ETerrainSize;
import helpers.InputHelper;
import helpers.MenuHelper;
import helpers.RandomHelper;
import helpers.TextHelper;
import helpers.UtilsHelper;
import java.util.List;
import java.util.Map;
import people.Archeologist;
import skills.Skill;
import treasures.ETreasureRarity;

/**
 * Opción "Arqueólogos" del menú principal: ver los datos de los arqueólogos, contratar y licenciar.
 */
public class ArcheologistsOperation implements IOperation {

	/** Lo que cuesta contratar un arqueólogo. */
	public static final int HIRE_COST = 50;

	/** La probabilidad de cada rareza al contratar, en porcentaje. */
	private static final Map<ETreasureRarity, Integer> HIRE_PROBABILITIES = Map.of(
			ETreasureRarity.COMMON, 70,
			ETreasureRarity.UNCOMMON, 20,
			ETreasureRarity.RARE, 10);

	/** Las opciones del submenú de arqueólogos. */
	private static final List<String> OPTIONS = List.of("Ver arqueólogos", "Contratar", "Licenciar");

	/** La partida, para consultar y modificar sus arqueólogos y puntos. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida en curso.
	 */
	public ArcheologistsOperation(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Arqueólogos";
	}

	@Override
	public void operation() {
		String option = MenuHelper.choose(OPTIONS, o -> o, "Elige una opción: ");
		while (option != null) {
			System.out.println();
			switch (option) {
				case "Ver arqueólogos":
					show();
					break;
				case "Contratar":
					hire();
					break;
				default:
					license();
			}
			System.out.println();
			option = MenuHelper.choose(OPTIONS, o -> o, "Elige una opción: ");
		}
	}

	// --- Ver arqueólogos ---

	/**
	 * Pide un arqueólogo y muestra sus datos.
	 */
	private void show() {
		Archeologist archeologist = chooseArcheologist();
		if (archeologist == null) {
			return;
		}
		System.out.println();
		System.out.println(TextHelper.header(archeologist.getName()));
		System.out.println("Nivel: " + archeologist.getLevel());
		if (archeologist.isMaxLevel()) {
			System.out.println("Especialización: " + archeologist.getSpecialization().getName());
			System.out.println("Exp: " + archeologist.getExp());
		} else {
			System.out.println("Exp: " + archeologist.getExp() + "/" + archeologist.getExpToLevelUp());
		}
		System.out.println("Terrenos:");
		for (ETerrainSize size : archeologist.getTerrainSizes()) {
			System.out.println("  -" + UtilsHelper.ucfirst(size.toString()));
		}
		printSkills(archeologist.getSkills());
	}

	/**
	 * Muestra las habilidades con el nombre a dos espacios y la descripción a tres.
	 * No muestra nada si no hay ninguna.
	 * @param skills Las habilidades.
	 */
	private void printSkills(List<Skill> skills) {
		if (!skills.isEmpty()) {
			System.out.println("Habilidades:");
			for (Skill skill : skills) {
				System.out.println("  -" + skill.getName() + ":");
				System.out.println("   " + skill.getDescription());
			}
		}
	}

	// --- Contratar ---

	/**
	 * Contrata un arqueólogo aleatorio si hay puntos suficientes y el usuario lo confirma.
	 */
	private void hire() {
		System.out.println("Tienes " + archaios.getPoints() + " puntos. Contratar un arqueólogo cuesta " + HIRE_COST + " puntos.");
		if (archaios.getPoints() < HIRE_COST) {
			System.out.println("No tienes puntos suficientes.");
			return;
		}
		if (!InputHelper.readYesNo("¿Quieres contratar un arqueólogo?")) {
			return;
		}
		archaios.spendPoints(HIRE_COST);

		ETreasureRarity rarity = RandomHelper.weighted(HIRE_PROBABILITIES);
		Archeologist archeologist = new Archeologist("", rarity);
		archeologist.addRandomSkills(archeologist.getInitialSkills());

		System.out.println();
		System.out.println("Has contratado un arqueólogo " + rarity + ".");
		printSkills(archeologist.getSkills());
		archeologist.setName(InputHelper.readString("Nombre del arqueólogo: "));
		archaios.getArcheologists().add(archeologist);
		System.out.println(archeologist.getName() + " se ha unido al equipo.");
	}

	// --- Licenciar ---

	/**
	 * Deja elegir un arqueólogo, muestra los puntos que da y lo licencia si el usuario confirma.
	 * Si no confirma, vuelve a mostrar la lista para elegir otro.
	 */
	private void license() {
		while (true) {
			if (archaios.getArcheologists().size() == 1) {
				System.out.println("No puedes licenciar a tu único arqueólogo.");
				return;
			}
			Archeologist archeologist = chooseArcheologist();
			if (archeologist == null) {
				return;
			}
			int points = archeologist.getLicensePoints();
			System.out.println("Licenciar a " + archeologist.getName() + " te da " + points + " puntos.");
			if (InputHelper.readYesNo("¿Quieres licenciarlo?")) {
				archaios.getArcheologists().remove(archeologist);
				archaios.addPoints(points);
				System.out.println(archeologist.getName() + " ha dejado el equipo. Ahora tienes " + archaios.getPoints() + " puntos.");
				return;
			}
			System.out.println();
		}
	}

	/**
	 * Muestra los arqueólogos con su nivel para que el usuario elija uno.
	 * @return El arqueólogo elegido, o null si elige volver.
	 */
	private Archeologist chooseArcheologist() {
		System.out.println("Arqueólogos:");
		return MenuHelper.choose(archaios.getArcheologists(), Archeologist::toString, "Elige un arqueólogo: ");
	}
}
