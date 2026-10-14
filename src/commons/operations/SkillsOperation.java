package commons.operations;

import commons.repositories.SkillRepository;
import helpers.InputHelper;
import helpers.MenuHelper;
import helpers.TextHelper;
import helpers.UtilsHelper;
import java.util.List;
import skills.ESkillType;
import skills.Skill;

/**
 * Opción "Habilidades" del menú principal: consultar los datos de las habilidades por tipo.
 */
public class SkillsOperation implements IOperation {

	/** Separador entre los datos de la habilidad y sus restricciones. */
	private static final String SEPARATOR = "--------------------";

	@Override
	public String getName() {
		return "Habilidades";
	}

	@Override
	public void operation() {
		ESkillType type = chooseType();
		while (type != null) {
			boolean back = showSkills(type);
			type = back ? null : chooseType();
		}
	}

	/**
	 * Muestra los tipos de habilidad que tienen alguna habilidad para que el usuario elija uno.
	 * @return El tipo elegido, o null si elige volver al menú.
	 */
	private ESkillType chooseType() {
		System.out.println("Tipos de habilidad:");
		return MenuHelper.choose(SkillRepository.getTypes(), t -> UtilsHelper.ucfirst(t.toString()),
				"Elige un tipo: ", "Volver al menú");
	}

	/**
	 * Deja consultar las habilidades de un tipo, por orden alfabético.
	 * @param type El tipo.
	 * @return true si el usuario quiere volver al menú principal;
	 * false si vuelve atrás para elegir otro tipo.
	 */
	private boolean showSkills(ESkillType type) {
		while (true) {
			System.out.println();
			System.out.println("Habilidades de " + type + ":");
			Skill skill = MenuHelper.choose(SkillRepository.getAll(type), Skill::getName, "Elige una habilidad: ");
			if (skill == null) {
				System.out.println();
				return false;
			}
			System.out.println();
			showDetail(skill);
			System.out.println();
			if (!InputHelper.readYesNo("¿Quieres ver otra habilidad?")) {
				return true;
			}
		}
	}

	/**
	 * Muestra el nombre, la descripción, el tipo y, si tiene, las dependencias e incompatibilidades.
	 * @param skill La habilidad.
	 */
	private void showDetail(Skill skill) {
		System.out.println(TextHelper.header(skill.getName()));
		System.out.println(skill.getDescription());
		System.out.println("Tipo: " + skill.getType());
		if (!skill.getDependencies().isEmpty() || !skill.getIncompatibilities().isEmpty()) {
			System.out.println(SEPARATOR);
			printList("Dependencias:", skill.getDependencies());
			printList("Incompatibilidades:", skill.getIncompatibilities());
		}
	}

	/**
	 * Muestra una lista de habilidades con un título, o nada si está vacía.
	 * @param title El título.
	 * @param skills Las habilidades.
	 */
	private void printList(String title, List<Skill> skills) {
		if (!skills.isEmpty()) {
			System.out.println(title);
			for (Skill skill : skills) {
				System.out.println("-" + skill.getName());
			}
		}
	}
}
