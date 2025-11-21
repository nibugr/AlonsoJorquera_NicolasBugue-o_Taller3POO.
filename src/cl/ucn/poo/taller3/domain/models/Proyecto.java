package cl.ucn.poo.taller3.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un proyecto
 */
public class Proyecto {
	private String id;
	private String nombre;
	private String responsable;
	private List<Tarea> tareas;

	public Proyecto(String id, String nombre, String responsable) {
		this.id = id;
		this.nombre = nombre;
		this.responsable = responsable;
		this.tareas = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public List<Tarea> getTareas() {
		return new ArrayList<>(tareas);
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = new ArrayList<>(tareas);
	}

	public void agregarTarea(Tarea tarea) {
		if (tarea != null) {
			this.tareas.add(tarea);
		}
	}

	public boolean eliminarTarea(Tarea tarea) {
		return this.tareas.remove(tarea);
	}

	public boolean eliminarTareaPorId(String tareaId) {
		return this.tareas.removeIf(tarea -> tarea.getId().equals(tareaId));
	}

	public Tarea buscarTareaPorId(String tareaId) {
		for (Tarea tarea : tareas) {
			if (tarea.getId().equals(tareaId)) {
				return tarea;
			}
		}
		return null;
	}

	public int getNumeroTareas() {
		return tareas.size();
	}

	@Override
	public String toString() {
		return "Proyecto{" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' + ", responsable='" + responsable + '\''
				+ ", tareas=" + tareas.size() + '}';
	}

	public void mostrarInformacion() {
		System.out.println("ID: " + id);
		System.out.println("Nombre: " + nombre);
		System.out.println("Responsable: " + responsable);
		System.out.println("Número de tareas: " + tareas.size());
	}
}
