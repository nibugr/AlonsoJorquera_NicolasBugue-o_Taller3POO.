package cl.ucn.poo.taller3.domain.strategy;

import cl.ucn.poo.taller3.domain.models.Tarea;
import java.util.List;

/**
 * Interface para estrategias de prioridad (Patrón Strategy)
 */
public interface EstrategiaPrioridad {
	List<Tarea> priorizar(List<Tarea> tareas);

	String getNombre();
}
