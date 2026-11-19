package commons.repositories;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase padre de los repositorios
 Guarda los elementos de un tipo por su clave, en el orden en que se registran
 * Cada repositorio hijo dice cuál es la clave de sus elementos, cómo se llena y añade sus propias búsquedas.
 * @param <T> El tipo de los elementos que guarda.
 */
public abstract class Repository<T> {

	/** Los elementos por su clave, en el orden en que se registran. */
	private final Map<String, T> elements = new LinkedHashMap<>();

	/**
	 * Llena el repositorio con todos sus elementos. Se llama una vez, desde el preInit.
	 */
	public abstract void populate();

	/**
	 * @param element Un elemento del repositorio.
	 * @return La clave con la que se guarda y se busca ese elemento.
	 */
	protected abstract String getKey(T element);

	/**
	 * Registra un elemento. Devuelve el mismo elemento con su tipo exacto, para poder guardarlo
	 * en una variable al registrarlo: <code>Skill extra = repositorio.register(new TreasureSkill(...))</code>.
	 * @param <E> El tipo exacto del elemento, el del repositorio o uno que herede de él.
	 * @param element El elemento a registrar.
	 * @return El mismo elemento.
	 * @throws IllegalArgumentException Si ya hay un elemento registrado con esa clave.
	 */
	public <E extends T> E register(E element) {
		String key = getKey(element);
		if (elements.containsKey(key)) {
			throw new IllegalArgumentException(key + " ya está registrado.");
		}
		elements.put(key, element);
		return element;
	}

	/**
	 * @param key La clave del elemento.
	 * @return El elemento, o null si no está registrado.
	 */
	public T get(String key) {
		return elements.get(key);
	}

	/**
	 * @param key La clave del elemento.
	 * @return true si hay un elemento registrado con esa clave.
	 */
	public boolean contains(String key) {
		return elements.containsKey(key);
	}

	/**
	 * @return Todos los elementos, en el orden en que se registraron. Es una copia: cambiarla no
	 * cambia el repositorio.
	 */
	public List<T> getAll() {
		return new ArrayList<>(elements.values());
	}
}
