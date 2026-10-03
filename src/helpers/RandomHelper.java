package helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Operaciones de azar del sistema: números, elecciones y repartos por probabilidad.
 */
public final class RandomHelper {

	/** Único generador de números aleatorios del sistema. */
	private static final Random RANDOM = new Random();

	private RandomHelper() {
	}

	/**
	 * Fija la semilla del generador para que los resultados se repitan. Solo para pruebas, borrar este método a la hora de juntar el trabajo final.
	 * @param seed La semilla.
	 */
	public static void setSeed(long seed) {
		RANDOM.setSeed(seed);
	}

	/**
	 * Devuelve un número entero aleatorio dentro de un rango.
	 * @param min El valor mínimo, incluido.
	 * @param max El valor máximo, incluido.
	 * @return Un número dentro de [<code>min, max].
	 * @throws IllegalArgumentException Si min es mayor que max>.
	 */
	public static int randomInt(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("Rango inválido: " + min + " > " + max);
		}
		return min + RANDOM.nextInt(max - min + 1);
	}

	/**
	 * Devuelve un elemento aleatorio de una lista.
	 * @param items Los elementos.
	 * @return Un elemento aleatorio, o null si la lista está vacía.
	 */
	public static <T> T randomElement(List<T> items) {
		return items.isEmpty() ? null : items.get(RANDOM.nextInt(items.size()));
	}

	/**
	 * Devuelve varios elementos distintos de una lista, en orden aleatorio.
	 * @param items Los elementos. No se modifica.
	 * @param count El número de elementos a devolver.
	 * @return Una lista nueva con count elementos sin repetir,
	 * o con todos ellos si la lista tiene menos de count.
	 */
	public static <T> List<T> randomElements(List<T> items, int count) {
		List<T> pool = new ArrayList<>(items);
		int size = pool.size();
		int n = Math.max(0, Math.min(count, size));
		for (int i = 0; i < n; i++) {
			Collections.swap(pool, i, i + RANDOM.nextInt(size - i));
		}
		return new ArrayList<>(pool.subList(0, n));
	}

	/**
	 * Elige una opción según su peso. Una opción con peso 60 sale el doble que una con peso 30.
	 * Las opciones con peso 0 o negativo no pueden salir.
	 * @param weights Las opciones y su peso.
	 * @return La opción elegida.
	 * @throws IllegalArgumentException Si ninguna opción tiene peso positivo.
	 */
	public static <T> T weighted(Map<T, Integer> weights) {
		int total = 0;
		for (int weight : weights.values()) {
			if (weight > 0) {
				total += weight;
			}
		}
		if (total <= 0) {
			throw new IllegalArgumentException("Ninguna opción tiene peso positivo.");
		}
		int roll = RANDOM.nextInt(total);
		for (Map.Entry<T, Integer> entry : weights.entrySet()) {
			int weight = entry.getValue();
			if (weight > 0) {
				roll -= weight;
				if (roll < 0) {
					return entry.getKey();
				}
			}
		}
		throw new IllegalStateException("No se pudo elegir una opción.");
	}
}