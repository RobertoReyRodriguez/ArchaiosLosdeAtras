package commons.operations;

import commons.Archaios;

/**
 * Opción "Licencias" del menú principal.
 * Muestra las licencias (tipos de terreno que puede excavar) de cada arqueólogo
 */
public class LicensesOperation implements IOperation {

	/** La partida, para consultar y modificar sus datos. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida en curso.
	 */
	public LicensesOperation(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Licencias";
	}

	@Override
	public void operation() {
		System.out.println("Esta opción todavía no está disponible.");
	}
}
