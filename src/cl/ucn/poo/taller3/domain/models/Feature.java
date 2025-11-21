package cl.ucn.poo.taller3.domain.models;

import cl.ucn.poo.taller3.domain.constants.TipoTarea;
import cl.ucn.poo.taller3.domain.visitor.TareaVisitor;
import java.util.Date;

/**
 * Clase que representa una tarea de tipo Feature
 */
public class Feature extends Tarea {

	public Feature(String id, String proyectoId, String descripcion, String estado, String responsable,
			String complejidad, Date fechaCreacion) {
		super(id, proyectoId, TipoTarea.FEATURE, descripcion, estado, responsable, complejidad, fechaCreacion);
	}

	@Override
	public void accept(TareaVisitor visitor) {
		visitor.visit(this);
	}

	public int getEstimacionTiempo() {
		switch (getComplejidad()) {
		case "Alta":
			return 8;
		case "Media":
			return 4;
		case "Baja":
			return 2;
		default:
			return 4;
		}
	}
}

