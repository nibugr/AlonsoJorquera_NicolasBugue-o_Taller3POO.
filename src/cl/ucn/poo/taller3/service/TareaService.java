
package cl.ucn.poo.taller3.service;

import cl.ucn.poo.taller3.domain.models.Tarea;
import cl.ucn.poo.taller3.persistence.GestorTareas;
import java.util.List;

/**
 * Servicio específico para operaciones de tareas
 */
public class TareaService {
	private static TareaService instancia;
	private GestorTareas gestorTareas;

	private TareaService() {
		this.gestorTareas = GestorTareas.getInstancia();
	}

	public static TareaService getInstancia() {
		if (instancia == null) {
			instancia = new TareaService();
		}
		return instancia;
	}

	public List<Tarea> obtenerTodasLasTareas() {
		return gestorTareas.getTareas();
	}

	public boolean validarTarea(String id, String proyectoId, String tipo, String descripcion, String responsable,
			String complejidad) {
		return id != null && !id.trim().isEmpty() && proyectoId != null && !proyectoId.trim().isEmpty() && tipo != null
				&& !tipo.trim().isEmpty() && descripcion != null && !descripcion.trim().isEmpty() && responsable != null
				&& !responsable.trim().isEmpty() && complejidad != null && !complejidad.trim().isEmpty();
	}

	public List<Tarea> filtrarTareasPorEstado(List<Tarea> tareas, String estado) {
		List<Tarea> filtradas = new java.util.ArrayList<>();
		for (Tarea tarea : tareas) {
			if (tarea.getEstado().equals(estado)) {
				filtradas.add(tarea);
			}
		}
		return filtradas;
	}

	public List<Tarea> filtrarTareasPorTipo(List<Tarea> tareas, String tipo) {
		List<Tarea> filtradas = new java.util.ArrayList<>();
		for (Tarea tarea : tareas) {
			if (tarea.getTipo().equals(tipo)) {
				filtradas.add(tarea);
			}
		}
		return filtradas;
	}
}


