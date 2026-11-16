package regions;

import java.util.Map;

import excavation.EGround;

/**
 * Marca las regiones difíciles: las de las expansiones.
 * 
 */
public interface IDifficultRegion {

	/**
	 * @return La expansión a la que pertenece la región.
	 */
	EExpansion getExpansion();
	
	/**
	 * @return La proporción de casillas de cada tipo de terreno en las excavaciones de la región,
	 * en porcentaje, sumando 100. El mapa no se puede modificar.
	 */
	Map<EGround, Integer> getGroundProportions();
}
