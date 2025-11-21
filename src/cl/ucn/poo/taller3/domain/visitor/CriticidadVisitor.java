
package cl.ucn.poo.taller3.domain.visitor;

import cl.ucn.poo.taller3.domain.models.Bug;
import cl.ucn.poo.taller3.domain.models.Feature;
import cl.ucn.poo.taller3.domain.models.Documentacion;

/**
 * Visitor para calcular criticidad (específico para Bugs)
 */
public class CriticidadVisitor implements TareaVisitor {

	private String resultado;

	public CriticidadVisitor() {
		this.resultado = "";
	}

	@Override
	public void visit(Bug bug) {
		String nivel = bug.getNivelCriticidad();
		this.resultado = "BUG - Afecta criticidad del proyecto. Nivel: " + nivel;
		if ("Crítico".equals(nivel)) {
			this.resultado += " ⚠️ REQUIERE ATENCIÓN INMEDIATA";
		}
	}

	@Override
	public void visit(Feature feature) {
		this.resultado = "FEATURE - No afecta directamente la criticidad del proyecto";
	}

	@Override
	public void visit(Documentacion documentacion) {
		this.resultado = "DOCUMENTACIÓN - No afecta directamente la criticidad del proyecto";
	}

	public String getResultado() {
		return resultado;
	}
}
