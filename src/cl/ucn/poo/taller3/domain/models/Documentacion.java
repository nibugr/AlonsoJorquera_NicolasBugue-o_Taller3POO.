package cl.ucn.poo.taller3.domain.models;

import cl.ucn.poo.taller3.domain.constants.TipoTarea;
import cl.ucn.poo.taller3.domain.visitor.TareaVisitor;
import java.util.Date;

/**
 * Clase que representa una tarea de tipo Documentación
 */
public class Documentacion extends Tarea {

	public Documentacion(String id, String proyectoId, String descripcion, String estado, String responsable,
			String complejidad, Date fechaCreacion) {
		super(id, proyectoId, TipoTarea.DOCUMENTACION, descripcion, estado, responsable, complejidad, fechaCreacion);
	}

	@Override
	public void accept(TareaVisitor visitor) {
		visitor.visit(this);
	}

	public String getNivelCalidad() {
		switch (getComplejidad()) {
		case "Alta":
			return "Documentación exhaustiva";
		case "Media":
			return "Documentación estándar";
		case "Baja":
			return "Documentación básica";
		default:
			return "Documentación estándar";
		}
	}
}
