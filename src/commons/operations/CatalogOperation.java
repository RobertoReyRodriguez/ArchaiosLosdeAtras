package commons.operations;

/**
 * Opción "Catálogo de tesoros" del menú principal.
 * 
 */
public class CatalogOperation implements IOperation {

	@Override
	public String getName() {
		return "Catálogo de tesoros";
	}

	@Override
	public void operation() {
		System.out.println("Esta opción todavía no está disponible.");
	}
}
