
package cl.ucn.poo.taller3.domain.visitor;

import cl.ucn.poo.taller3.domain.models.Bug;
import cl.ucn.poo.taller3.domain.models.Feature;
import cl.ucn.poo.taller3.domain.models.Documentacion;

/**
 * Interface para visitors de tareas (Patrón Visitor)
 */
public interface TareaVisitor {
	void visit(Bug bug);

	void visit(Feature feature);

	void visit(Documentacion documentacion);
}
