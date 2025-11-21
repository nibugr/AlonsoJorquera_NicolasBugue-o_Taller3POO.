
package cl.ucn.poo.taller3.persistence;

import cl.ucn.poo.taller3.domain.models.Tarea;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Gestor de tareas - Maneja la persistencia de tareas
 */
public class GestorTareas {
	private static GestorTareas instancia;
	private List<Tarea> tareas;
	private GestorArchivos gestorArchivos;
	private SimpleDateFormat dateFormat;

	private GestorTareas() {
		this.gestorArchivos = GestorArchivos.getInstancia();
		this.tareas = new ArrayList<>();
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cargarTareas();
	}

	public static GestorTareas getInstancia() {
		if (instancia == null) {
			instancia = new GestorTareas();
		}
		return instancia;
	}

	private void cargarTareas() {
		String[] lineas = gestorArchivos.leerArchivo("tareas.txt");

		for (String linea : lineas) {
			String[] partes = linea.split("\\|");
			if (partes.length == 8) {
				try {
					String proyectoId = partes[0].trim();
					String id = partes[1].trim();
					String tipo = partes[2].trim();
					String descripcion = partes[3].trim();
					String estado = partes[4].trim();
					String responsable = partes[5].trim();
					String complejidad = partes[6].trim();
					String fechaStr = partes[7].trim();

					Date fecha = dateFormat.parse(fechaStr);

					Tarea tarea = Tarea.crearTarea(id, proyectoId, tipo, descripcion, estado, responsable, complejidad,
							fecha);
					tareas.add(tarea);

				} catch (Exception e) {
					System.out.println("Error al cargar tarea: " + e.getMessage());
				}
			}
		}
	}

	public List<Tarea> getTareas() {
		return new ArrayList<>(tareas);
	}

	public List<Tarea> getTareasPorProyecto(String proyectoId) {
		List<Tarea> tareasProyecto = new ArrayList<>();
		for (Tarea tarea : tareas) {
			if (tarea.getProyectoId().equals(proyectoId)) {
				tareasProyecto.add(tarea);
			}
		}
		return tareasProyecto;
	}

	public List<Tarea> getTareasPorUsuario(String username) {
		List<Tarea> tareasUsuario = new ArrayList<>();
		for (Tarea tarea : tareas) {
			if (tarea.getResponsable().equals(username)) {
				tareasUsuario.add(tarea);
			}
		}
		return tareasUsuario;
	}

	public Tarea buscarTareaPorId(String id) {
		for (Tarea tarea : tareas) {
			if (tarea.getId().equals(id)) {
				return tarea;
			}
		}
		return null;
	}

	public boolean agregarTarea(Tarea tarea) {
		if (buscarTareaPorId(tarea.getId()) == null) {
			tareas.add(tarea);
			return guardarTareas();
		}
		return false;
	}

	public boolean eliminarTarea(String id) {
		Tarea tarea = buscarTareaPorId(id);
		if (tarea != null) {
			tareas.remove(tarea);
			return guardarTareas();
		}
		return false;
	}

	public boolean eliminarTareasPorProyecto(String proyectoId) {
		tareas.removeIf(tarea -> tarea.getProyectoId().equals(proyectoId));
		return guardarTareas();
	}

	public boolean actualizarEstadoTarea(String tareaId, String nuevoEstado) {
		Tarea tarea = buscarTareaPorId(tareaId);
		if (tarea != null) {
			tarea.setEstado(nuevoEstado);
			return guardarTareas();
		}
		return false;
	}

	public boolean tieneTareasEnFecha(String username, Date fecha) {
		for (Tarea tarea : tareas) {
			if (tarea.getResponsable().equals(username) && esMismaFecha(tarea.getFechaCreacion(), fecha)) {
				return true;
			}
		}
		return false;
	}

	private boolean esMismaFecha(Date fecha1, Date fecha2) {
		return dateFormat.format(fecha1).equals(dateFormat.format(fecha2));
	}

	private boolean guardarTareas() {
		List<String> lineas = new ArrayList<>();
		for (Tarea tarea : tareas) {
			String linea = tarea.getProyectoId() + "|" + tarea.getId() + "|" + tarea.getTipo() + "|"
					+ tarea.getDescripcion() + "|" + tarea.getEstado() + "|" + tarea.getResponsable() + "|"
					+ tarea.getComplejidad() + "|" + dateFormat.format(tarea.getFechaCreacion());
			lineas.add(linea);
		}
		return gestorArchivos.escribirArchivo("tareas.txt", lineas, false);
	}
}
