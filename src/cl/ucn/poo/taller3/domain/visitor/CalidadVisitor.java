
package cl.ucn.poo.taller3.domain.visitor;

import cl.ucn.poo.taller3.domain.models.Bug;
import cl.ucn.poo.taller3.domain.models.Feature;
import cl.ucn.poo.taller3.domain.models.Documentacion;

/**
 * Visitor para calcular calidad (específico para Documentación)
 */
public class CalidadVisitor implements TareaVisitor {

	private String resultado;

	public CalidadVisitor() {
		this.resultado = "";
	}

	@Override
	public void visit(Bug bug) {
		this.resultado = "BUG - Resolver mejora la calidad percibida por el usuario";
	}

	@Override
	public void visit(Feature feature) {
		this.resultado = "FEATURE - Agrega valor pero requiere documentación posterior";
	}

	@Override
	public void visit(Documentacion documentacion) {
		String nivel = documentacion.getNivelCalidad();
		this.resultado = "DOCUMENTACIÓN - Mejora la calidad del proyecto. Nivel: " + nivel;
	}

	public String getResultado() {
		return resultado;
	}
}
	


