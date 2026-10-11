package regions;

import commons.operations.IOperation;

/**
 * Opción "Atlas" del menú principal: información descriptiva de las regiones y sus tesoros.
 */
public class Atlas implements IOperation {

	@Override
	public String getName() {
		return "Atlas";
	}

	@Override
	public void operation() {
		System.out.println("Esta opción todavía no está disponible.");
	}
}
