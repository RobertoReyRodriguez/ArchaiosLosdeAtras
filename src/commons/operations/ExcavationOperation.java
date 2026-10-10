package commons.operations;

import commons.Archaios;

/**
 * Opción "Excavar" del menú principal.
 * Elegir región, arqueólogo y terreno y realizar la excavación. 
 */
public class ExcavationOperation implements IOperation {

	/** La partida, para consultar y modificar sus datos. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida en curso.
	 */
	public ExcavationOperation(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Excavar";
	}

	@Override
	public void operation() {
		System.out.println("Esta opción todavía no está disponible.");
	}
}
