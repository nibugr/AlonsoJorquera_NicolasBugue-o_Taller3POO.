
package cl.ucn.poo.taller3.persistence;

import cl.ucn.poo.taller3.domain.models.Proyecto;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de proyectos - Maneja la persistencia de proyectos
 */
public class GestorProyectos {
	private static GestorProyectos instancia;
	private List<Proyecto> proyectos;
	private GestorArchivos gestorArchivos;

	private GestorProyectos() {
		this.gestorArchivos = GestorArchivos.getInstancia();
		this.proyectos = new ArrayList<>();
		cargarProyectos();
	}

	public static GestorProyectos getInstancia() {
		if (instancia == null) {
			instancia = new GestorProyectos();
		}
		return instancia;
	}

	private void cargarProyectos() {
		String[] lineas = gestorArchivos.leerArchivo("proyectos.txt");

		for (String linea : lineas) {
			String[] partes = linea.split("\\|");
			if (partes.length == 3) {
				String id = partes[0].trim();
				String nombre = partes[1].trim();
				String responsable = partes[2].trim();

				Proyecto proyecto = new Proyecto(id, nombre, responsable);
				proyectos.add(proyecto);
			}
		}
	}

	public List<Proyecto> getProyectos() {
		return new ArrayList<>(proyectos);
	}

	public Proyecto buscarProyectoPorId(String id) {
		for (Proyecto proyecto : proyectos) {
			if (proyecto.getId().equals(id)) {
				return proyecto;
			}
		}
		return null;
	}

	public boolean agregarProyecto(Proyecto proyecto) {
		if (buscarProyectoPorId(proyecto.getId()) == null) {
			proyectos.add(proyecto);
			return guardarProyectos();
		}
		return false;
	}

	public boolean eliminarProyecto(String id) {
		Proyecto proyecto = buscarProyectoPorId(id);
		if (proyecto != null) {
			proyectos.remove(proyecto);
			return guardarProyectos();
		}
		return false;
	}

	private boolean guardarProyectos() {
		List<String> lineas = new ArrayList<>();
		for (Proyecto proyecto : proyectos) {
			String linea = proyecto.getId() + "|" + proyecto.getNombre() + "|" + proyecto.getResponsable();
			lineas.add(linea);
		}
		return gestorArchivos.escribirArchivo("proyectos.txt", lineas, false);
	}
}


