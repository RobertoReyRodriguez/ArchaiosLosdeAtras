package stats;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import commons.operations.IOperation;

/**
 * Estadísticas de la partida y opción "Estadísticas" del menú principal.
 * Los datos ya se registran; la opción del menú se implementa en el paso 7.
 */
public class Stats implements IOperation {

	/** Veces que se ha encontrado cada tesoro, por código completo y en orden alfabético. */
	private Map<String, Integer> treasures = new TreeMap<>();
	/** Veces que se ha excavado en cada región, por código de región. */
	private Map<String, Integer> places = new TreeMap<>();

	/**
	 * Apunta que se ha encontrado un tesoro.
	 * @param code El código completo del tesoro encontrado. <code>RRR_TTT_NNN_PPPP</code>
	 */
	public void registerTreasure(String code) {
		treasures.merge(code, 1, Integer::sum);
	}

	/**
	 * Apunta que se ha excavado en una región.
	 * @param regionCode El código de la región. <code>RRR</code>
	 */
	public void registerPlace(String regionCode) {
		places.merge(regionCode, 1, Integer::sum);
	}

	/**
	 * @return Las veces que se ha encontrado cada tesoro. No se puede modificar.
	 */
	public Map<String, Integer> getTreasures() {
		return Collections.unmodifiableMap(treasures);
	}

	/**
	 * @return Las veces que se ha excavado en cada región. No se puede modificar.
	 */
	public Map<String, Integer> getPlaces() {
		return Collections.unmodifiableMap(places);
	}

	@Override
	public String getName() {
		return "Estadísticas";
	}

	@Override
	public void operation() {
		System.out.println("Esta opción todavía no está disponible.");
	}
}
