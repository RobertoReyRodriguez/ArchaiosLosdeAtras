package excavation;

import java.util.ArrayList;
import java.util.List;
import people.Archeologist;
import regions.Region;

/**
 * Versión mínima de Excavation para hacer pruebas: solo tiene lo mínimo para que compile
 * 
 */
public class Excavation {

	private final Region region;
	private final Archeologist archeologist;
	private final Terrain terrain;

	public Excavation(Region region, Archeologist archeologist, Terrain terrain) {
		this.region = region;
		this.archeologist = archeologist;
		this.terrain = terrain;
	}

	/**
	 * Aquí va el proceso de excavación.
	 * @return Los códigos completos de los tesoros encontrados (RRR_TTT_NNN_PPPP).
	 */
	public List<String> excavate() {
		System.out.println("Excavando en " + region.getName() + " con " + archeologist.getName()
				+ " en un terreno " + terrain + ".");
		List<String> found = new ArrayList<>();
		return found;
	}

	public Region getRegion() {
		return region;
	}

	public Archeologist getArcheologist() {
		return archeologist;
	}

	public Terrain getTerrain() {
		return terrain;
	}
}
