
package cl.ucn.poo.taller3.service;

import cl.ucn.poo.taller3.domain.models.Proyecto;
import cl.ucn.poo.taller3.persistence.GestorProyectos;
import java.util.List;

/**
 * Servicio específico para operaciones de proyectos
 */
public class ProyectoService {
	private static ProyectoService instancia;
	private GestorProyectos gestorProyectos;

	private ProyectoService() {
		this.gestorProyectos = GestorProyectos.getInstancia();
	}

	public static ProyectoService getInstancia() {
		if (instancia == null) {
			instancia = new ProyectoService();
		}
		return instancia;
	}

	public List<Proyecto> obtenerTodosLosProyectos() {
		return gestorProyectos.getProyectos();
	}

	public Proyecto buscarProyecto(String id) {
		return gestorProyectos.buscarProyectoPorId(id);
	}

	public boolean validarProyecto(String id, String nombre, String responsable) {
		return id != null && !id.trim().isEmpty() && nombre != null && !nombre.trim().isEmpty() && responsable != null
				&& !responsable.trim().isEmpty();
	}
}
