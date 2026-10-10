package commons.repositories;

import java.util.ArrayList;
import java.util.List;

import helpers.CodeHelper;
import regions.Region;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Puerta de acceso a todos los tesoros del sistema.
 * No guarda tesoros propios: los busca en las regiones registradas en
 * {@link RegionRepository}, que son las que los almacenan.
 */
public final class TreasureRepository {

	private TreasureRepository() {
	}

	/**
	 * Busca un tesoro por su código.
	 * @param code El código del tesoro, con o sin la parte. <code>RRR_TTT_NNN[_PPPP]</code>
	 * @return El tesoro, o null si no existe.
	 */
	public static Treasure get(String code) {
		Region region = RegionRepository.get(CodeHelper.getRegion(code));
		return region == null ? null : region.getTreasure(code);
	}

	/**
	 * @return Todos los tesoros, agrupados por región en el orden de registro.
	 */
	public static List<Treasure> getAll() {
		List<Treasure> result = new ArrayList<>();
		for (Region region : RegionRepository.getAll()) {
			result.addAll(region.getTreasures());
		}
		return result;
	}

	/**
	 * @param type El tipo buscado.
	 * @return Todos los tesoros de ese tipo. Puede estar vacía.
	 */
	public static List<Treasure> getAll(ETreasureType type) {
		List<Treasure> result = new ArrayList<>();
		for (Treasure treasure : getAll()) {
			if (treasure.getType() == type) {
				result.add(treasure);
			}
		}
		return result;
	}

	/**
	 * @param rarity La rareza buscada.
	 * @return Todos los tesoros de esa rareza. Puede estar vacía.
	 */
	public static List<Treasure> getAll(ETreasureRarity rarity) {
		List<Treasure> result = new ArrayList<>();
		for (Treasure treasure : getAll()) {
			if (treasure.getRarity() == rarity) {
				result.add(treasure);
			}
		}
		return result;
	}
}
