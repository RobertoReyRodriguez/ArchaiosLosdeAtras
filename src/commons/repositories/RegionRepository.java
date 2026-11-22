package commons.repositories;

import java.util.ArrayList;
import java.util.List;

import regions.Egypt;
import regions.Greece;
import regions.Iberia;
import regions.IDifficultRegion;
import regions.Petra;
import regions.Region;
import regions.RegionLoader;
import regions.Rome;
import regions.Sumer;

/**
 * Repositorio de todas las regiones del sistema, guardadas por su código.
 * Cada región se crea una única vez, se registra en el preInit y desde entonces
 * cualquier clase la obtiene de aquí en vez de crear una nueva.
 */
public final class RegionRepository extends Repository<Region> {

	/** El único repositorio de regiones. */
	private static final RegionRepository INSTANCE = new RegionRepository();

	private RegionRepository() {
	}

	/**
	 * @return El repositorio de regiones.
	 */
	public static RegionRepository getInstance() {
		return INSTANCE;
	}

	/**
	 * Registra las regiones básicas y las de las expansiones. Se crean vacías y sus tesoros
	 * se cargan de <code>data/regions/&lt;código&gt;.json</code>.
	 */
	@Override
	public void populate() {
		register(RegionLoader.load(new Greece()));
		register(RegionLoader.load(new Iberia()));
		register(RegionLoader.load(new Rome()));
		register(RegionLoader.load(new Egypt()));
		register(RegionLoader.load(new Petra()));
		register(RegionLoader.load(new Sumer()));
	}

	@Override
	protected String getKey(Region region) {
		return region.getCode();
	}

	/**
	 * @return Las regiones básicas, las que no tienen terrenos difíciles, en el orden en que se registraron.
	 */
	public List<Region> getBasic() {
		List<Region> result = new ArrayList<>();
		for (Region region : getAll()) {
			if (!(region instanceof IDifficultRegion)) {
				result.add(region);
			}
		}
		return result;
	}

	/**
	 * @return Las regiones difíciles, las de las expansiones, en el orden en que se registraron.
	 */
	public List<Region> getDifficult() {
		List<Region> result = new ArrayList<>();
		for (Region region : getAll()) {
			if (region instanceof IDifficultRegion) {
				result.add(region);
			}
		}
		return result;
	}
}
