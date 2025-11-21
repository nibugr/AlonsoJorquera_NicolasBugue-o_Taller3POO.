package cl.ucn.poo.taller3.domain.models;

import cl.ucn.poo.taller3.domain.constants.TipoTarea;
import cl.ucn.poo.taller3.domain.visitor.TareaVisitor;
import java.util.Date;

/**
 * Clase que representa una tarea de tipo Bug
 */
public class Bug extends Tarea {

	public Bug(String id, String proyectoId, String descripcion, String estado, String responsable, String complejidad,
			Date fechaCreacion) {
		super(id, proyectoId, TipoTarea.BUG, descripcion, estado, responsable, complejidad, fechaCreacion);
	}

	@Override
	public void accept(TareaVisitor visitor) {
		visitor.visit(this);
	}

	public String getNivelCriticidad() {
		switch (getComplejidad()) {
		case "Alta":
			return "Crítico";
		case "Media":
			return "Importante";
		case "Baja":
			return "Menor";
		default:
			return "Importante";
		}
	}
}
