package regions;

/**
 * Marca las regiones difíciles: las de las expansiones.
 * 
 */
public interface IDifficultRegion {

	/**
	 * @return La expansión a la que pertenece la región.
	 */
	EExpansion getExpansion();
}
