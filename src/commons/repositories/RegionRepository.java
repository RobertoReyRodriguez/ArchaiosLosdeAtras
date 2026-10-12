package commons.repositories;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import regions.Region;

/**
 * Repositorio de todas las regiones del sistema.
 * Cada región se crea una única vez, se registra en el preInit y desde entonces
 * cualquier clase la obtiene de aquí en vez de crear una nueva.
 */
public final class RegionRepository {

	/** Las regiones por su código, en el orden en que se registran. */
	private static final Map<String, Region> REGIONS = new LinkedHashMap<>();

	private RegionRepository() {
	}

	/**
	 * Registra una región. Solo debe llamarse desde el preInit.
	 * @param region La región a registrar.
	 * @throws IllegalArgumentException Si ya hay una región registrada con ese código.
	 */
	public static void register(Region region) {
		if (REGIONS.containsKey(region.getCode())) {
			throw new IllegalArgumentException("La región " + region.getCode() + " ya está registrada.");
		}
		REGIONS.put(region.getCode(), region);
	}

	/**
	 * @param code El código de la región. <code>RRR</code>
	 * @return La región, o null si no está registrada.
	 */
	public static Region get(String code) {
		return REGIONS.get(code);
	}

	/**
	 * @return Todas las regiones, en el orden en que se registraron.
	 */
	public static List<Region> getAll() {
		return new ArrayList<>(REGIONS.values());
	}
}
