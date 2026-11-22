package commons.repositories;

import helpers.CodeHelper;
import java.util.ArrayList;
import java.util.List;
import regions.Region;
import treasures.ETreasureRarity;
import treasures.ETreasureType;
import treasures.Treasure;

/**
 * Repositorio de todos los tesoros del sistema, guardados por su código.
 */
public final class TreasureRepository extends Repository<Treasure> {

	/** El único repositorio de tesoros. */
	private static final TreasureRepository INSTANCE = new TreasureRepository();

	private TreasureRepository() {
	}

	/**
	 * @return El repositorio de tesoros.
	 */
	public static TreasureRepository getInstance() {
		return INSTANCE;
	}

	/**
	 * Registra los tesoros de todas las regiones. Hay que llamarlo después de llenar el
	 * repositorio de regiones, porque los tesoros se cargan con ellas.
	 */
	@Override
	public void populate() {
		for (Region region : RegionRepository.getInstance().getAll()) {
			for (Treasure treasure : region.getTreasures()) {
				register(treasure);
			}
		}
	}

	@Override
	protected String getKey(Treasure treasure) {
		return treasure.getCode();
	}

	/**
	 * Busca un tesoro por su código. Acepta también el código de una de sus partes.
	 * @param code El código del tesoro, con o sin la parte. <code>GRC_MON_001</code> o <code>GRC_MON_001_0001</code>
	 * @return El tesoro, o null si no existe.
	 */
	@Override
	public Treasure get(String code) {
		return super.get(CodeHelper.getFullTreasure(code));
	}

	/**
	 * @param type El tipo buscado.
	 * @return Todos los tesoros de ese tipo. Puede estar vacía.
	 */
	public List<Treasure> getAll(ETreasureType type) {
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
	public List<Treasure> getAll(ETreasureRarity rarity) {
		List<Treasure> result = new ArrayList<>();
		for (Treasure treasure : getAll()) {
			if (treasure.getRarity() == rarity) {
				result.add(treasure);
			}
		}
		return result;
	}
}
