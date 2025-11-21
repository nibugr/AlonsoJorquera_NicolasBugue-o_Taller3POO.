
package cl.ucn.poo.taller3.domain.visitor;

import cl.ucn.poo.taller3.domain.models.Bug;
import cl.ucn.poo.taller3.domain.models.Feature;
import cl.ucn.poo.taller3.domain.models.Documentacion;

/**
 * Visitor para calcular tiempo (específico para Features)
 */
public class TiempoVisitor implements TareaVisitor {

	private String resultado;

	public TiempoVisitor() {
		this.resultado = "";
	}

	@Override
	public void visit(Bug bug) {
		this.resultado = "BUG - Tiempo de resolución estimado: 2-4 horas (depende de complejidad)";
	}

	@Override
	public void visit(Feature feature) {
		int tiempo = feature.getEstimacionTiempo();
		this.resultado = "FEATURE - Impacta en estimación de tiempo. Tiempo estimado: " + tiempo + " horas";
	}

	@Override
	public void visit(Documentacion documentacion) {
		this.resultado = "DOCUMENTACIÓN - Tiempo estimado: 1-3 horas (depende de extensión)";
	}

	public String getResultado() {
		return resultado;
	}
}
