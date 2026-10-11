package commons.operations;

import commons.Archaios;

/**
 * Opción "Arqueólogos" del menú principal.
 * Ver los datos de los arqueólogos, contratar y licenciar. 
 */
public class ArcheologistsOperation implements IOperation {

	/** La partida, para consultar y modificar sus datos. */
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
		System.out.println("Esta opción todavía no está disponible.");
	}
}
