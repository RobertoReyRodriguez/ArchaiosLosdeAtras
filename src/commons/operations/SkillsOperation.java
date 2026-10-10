package commons.operations;

/**
 * Opción "Habilidades" del menú principal.
 * Consulta los datos de las habilidades por tipo. 
 */
public class SkillsOperation implements IOperation {

	@Override
	public String getName() {
		return "Habilidades";
	}

	@Override
	public void operation() {
		System.out.println("Esta opción todavía no está disponible.");
	}
}
