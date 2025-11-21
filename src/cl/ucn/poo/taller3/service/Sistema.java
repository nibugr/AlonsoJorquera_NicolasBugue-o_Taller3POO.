
package cl.ucn.poo.taller3.service;

import cl.ucn.poo.taller3.domain.models.*;
import cl.ucn.poo.taller3.domain.strategy.*;
import cl.ucn.poo.taller3.domain.visitor.*;
import cl.ucn.poo.taller3.persistence.*;
import java.util.*;

/**
 * Servicio principal del sistema (Singleton - Fachada)
 */
public class Sistema {
	private static Sistema instancia;
	private Usuario usuarioActual;
	private GestorUsuarios gestorUsuarios;
	private GestorProyectos gestorProyectos;
	private GestorTareas gestorTareas;
	private GestorArchivos gestorArchivos;

	private Sistema() {
		this.gestorUsuarios = GestorUsuarios.getInstancia();
		this.gestorProyectos = GestorProyectos.getInstancia();
		this.gestorTareas = GestorTareas.getInstancia();
		this.gestorArchivos = GestorArchivos.getInstancia();
		this.usuarioActual = null;
	}

	public static Sistema getInstancia() {
		if (instancia == null) {
			instancia = new Sistema();
		}
		return instancia;
	}

	public boolean autenticar(String username, String password) {
		Usuario usuario = gestorUsuarios.autenticar(username, password);
		if (usuario != null) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}

	public void cerrarSesion() {
		this.usuarioActual = null;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean estaAutenticado() {
		return usuarioActual != null;
	}

	public boolean esAdministrador() {
		return estaAutenticado() && usuarioActual.esAdministrador();
	}

	public boolean esColaborador() {
		return estaAutenticado() && usuarioActual.esColaborador();
	}

	// ==================== MÉTODOS PARA ADMINISTRADOR ====================

	public List<Proyecto> obtenerProyectosCompletos() {
		List<Proyecto> proyectos = gestorProyectos.getProyectos();

		for (Proyecto proyecto : proyectos) {
			List<Tarea> tareasProyecto = gestorTareas.getTareasPorProyecto(proyecto.getId());
			proyecto.setTareas(tareasProyecto);
		}

		return proyectos;
	}

	public boolean crearProyecto(String id, String nombre, String responsable) {
		if (!gestorUsuarios.existeUsuario(responsable)) {
			return false;
		}

		Proyecto proyecto = new Proyecto(id, nombre, responsable);
		return gestorProyectos.agregarProyecto(proyecto);
	}

	public boolean eliminarProyecto(String id) {
		gestorTareas.eliminarTareasPorProyecto(id);
		return gestorProyectos.eliminarProyecto(id);
	}

	public boolean crearTarea(String id, String proyectoId, String tipo, String descripcion, String estado,
			String responsable, String complejidad) {

		Proyecto proyecto = gestorProyectos.buscarProyectoPorId(proyectoId);
		if (proyecto == null) {
			return false;
		}

		if (!gestorUsuarios.existeUsuario(responsable)) {
			return false;
		}

		Date fechaActual = new Date();
		if (gestorTareas.tieneTareasEnFecha(responsable, fechaActual)) {
			return false;
		}

		Tarea tarea = Tarea.crearTarea(id, proyectoId, tipo, descripcion, estado, responsable, complejidad,
				fechaActual);
		return gestorTareas.agregarTarea(tarea);
	}

	public boolean eliminarTarea(String tareaId) {
		return gestorTareas.eliminarTarea(tareaId);
	}

	public List<Tarea> priorizarTareas(List<Tarea> tareas, String tipoEstrategia) {
		EstrategiaPrioridad estrategia;

		switch (tipoEstrategia.toLowerCase()) {
		case "fecha":
			estrategia = new PrioridadFecha();
			break;
		case "impacto":
			estrategia = new PrioridadImpacto();
			break;
		case "complejidad":
			estrategia = new PrioridadComplejidad();
			break;
		default:
			estrategia = new PrioridadFecha();
		}

		return estrategia.priorizar(tareas);
	}

	public boolean generarReporteProyectos() {
		List<Proyecto> proyectos = obtenerProyectosCompletos();
		StringBuilder reporte = new StringBuilder();

		reporte.append("=== REPORTE DE PROYECTOS - ").append(gestorArchivos.getFechaActual()).append(" ===\n\n");

		for (Proyecto proyecto : proyectos) {
			reporte.append("PROYECTO: ").append(proyecto.getNombre()).append("\n");
			reporte.append("ID: ").append(proyecto.getId()).append("\n");
			reporte.append("Responsable: ").append(proyecto.getResponsable()).append("\n");
			reporte.append("Total Tareas: ").append(proyecto.getNumeroTareas()).append("\n");
			reporte.append("----------------------------------------\n");

			Map<String, Integer> estadisticas = new HashMap<>();
			for (Tarea tarea : proyecto.getTareas()) {
				String estado = tarea.getEstado();
				estadisticas.put(estado, estadisticas.getOrDefault(estado, 0) + 1);
			}

			reporte.append("Estadísticas: ");
			for (Map.Entry<String, Integer> entry : estadisticas.entrySet()) {
				reporte.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
			}
			reporte.append("\n\n");

			reporte.append("TAREAS:\n");
			for (Tarea tarea : proyecto.getTareas()) {
				reporte.append("  - ").append(tarea.getTipo()).append(": ").append(tarea.getDescripcion()).append(" (")
						.append(tarea.getEstado()).append(")\n");
			}
			reporte.append("\n");
		}

		return gestorArchivos.escribirReporte("reporte.txt", reporte.toString());
	}

	// ==================== MÉTODOS PARA COLABORADOR ====================

	public List<Proyecto> obtenerProyectosDisponibles() {
		return gestorProyectos.getProyectos();
	}

	public List<Tarea> obtenerMisTareas() {
		if (usuarioActual != null) {
			return gestorTareas.getTareasPorUsuario(usuarioActual.getUsername());
		}
		return new ArrayList<>();
	}

	public boolean actualizarEstadoTarea(String tareaId, String nuevoEstado) {
		if (usuarioActual != null) {
			Tarea tarea = gestorTareas.buscarTareaPorId(tareaId);
			if (tarea != null && tarea.getResponsable().equals(usuarioActual.getUsername())) {
				return gestorTareas.actualizarEstadoTarea(tareaId, nuevoEstado);
			}
		}
		return false;
	}

	public String aplicarVisitorTarea(Tarea tarea, String tipoVisitor) {
		TareaVisitor visitor;

		switch (tipoVisitor.toLowerCase()) {
		case "criticidad":
			visitor = new CriticidadVisitor();
			break;
		case "tiempo":
			visitor = new TiempoVisitor();
			break;
		case "calidad":
			visitor = new CalidadVisitor();
			break;
		default:
			return "Tipo de visitor no válido";
		}

		tarea.accept(visitor);

		if (visitor instanceof CriticidadVisitor) {
			return ((CriticidadVisitor) visitor).getResultado();
		} else if (visitor instanceof TiempoVisitor) {
			return ((TiempoVisitor) visitor).getResultado();
		} else if (visitor instanceof CalidadVisitor) {
			return ((CalidadVisitor) visitor).getResultado();
		}

		return "Resultado no disponible";
	}

	public List<Usuario> obtenerColaboradores() {
		return gestorUsuarios.getColaboradores();
	}

	public boolean existeProyecto(String proyectoId) {
		return gestorProyectos.buscarProyectoPorId(proyectoId) != null;
	}

	public boolean existeTarea(String tareaId) {
		return gestorTareas.buscarTareaPorId(tareaId) != null;
	}

	public Tarea obtenerTareaPorId(String tareaId) {
		return gestorTareas.buscarTareaPorId(tareaId);
	}

	public List<String> obtenerEstrategiasPrioridad() {
		List<String> estrategias = new ArrayList<>();
		estrategias.add("fecha - Prioridad por Fecha (más antiguas primero)");
		estrategias.add("impacto - Prioridad por Impacto (Bug → Feature → Documentación)");
		estrategias.add("complejidad - Prioridad por Complejidad (Alta → Media → Baja)");
		return estrategias;
	}

	public List<String> obtenerVisitorsDisponibles() {
		List<String> visitors = new ArrayList<>();
		visitors.add("criticidad - Analiza criticidad (especial para Bugs)");
		visitors.add("tiempo - Analiza tiempo (especial para Features)");
		visitors.add("calidad - Analiza calidad (especial para Documentación)");
		return visitors;
	}
}


