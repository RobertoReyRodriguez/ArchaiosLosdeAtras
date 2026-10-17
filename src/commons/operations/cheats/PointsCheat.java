package commons.operations.cheats;

import commons.Archaios;
import commons.operations.IOperation;
import helpers.InputHelper;

/**
 * Truco oculto 99: suma puntos a la partida, para probar contratar sin excavar antes.
 */
public class PointsCheat implements IOperation {

	/** Los puntos máximos que se pueden sumar de una vez. */
	private static final int MAX_POINTS = 100000;

	/** La partida. */
	private final Archaios archaios;

	/**
	 * @param archaios La partida.
	 */
	public PointsCheat(Archaios archaios) {
		this.archaios = archaios;
	}

	@Override
	public String getName() {
		return "Truco: puntos";
	}

	@Override
	public void operation() {
		System.out.println("Tienes " + archaios.getPoints() + " puntos.");
		int amount = InputHelper.readInt("Puntos a sumar (1-" + MAX_POINTS + "): ", 1, MAX_POINTS);
		archaios.addPoints(amount);
		System.out.println("Ahora tienes " + archaios.getPoints() + " puntos.");
	}
}
