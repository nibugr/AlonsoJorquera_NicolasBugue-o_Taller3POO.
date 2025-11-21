package cl.ucn.poo.taller3.domain.strategy;

import cl.ucn.poo.taller3.domain.models.Tarea;
import cl.ucn.poo.taller3.domain.constants.TipoTarea;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Estrategia de prioridad por impacto/ tipo de tarea
 */
public class PrioridadImpacto implements EstrategiaPrioridad {

	@Override
	public List<Tarea> priorizar(List<Tarea> tareas) {
		List<Tarea> tareasOrdenadas = new ArrayList<>(tareas);
		Collections.sort(tareasOrdenadas, new Comparator<Tarea>() {
			@Override
			public int compare(Tarea t1, Tarea t2) {
				return Integer.compare(getPrioridad(t1), getPrioridad(t2));
			}

			private int getPrioridad(Tarea tarea) {
				switch (tarea.getTipo()) {
				case TipoTarea.BUG:
					return 1;
				case TipoTarea.FEATURE:
					return 2;
				case TipoTarea.DOCUMENTACION:
					return 3;
				default:
					return 2;
				}
			}
		});
		return tareasOrdenadas;
	}

	@Override
	public String getNombre() {
		return "Prioridad por Impacto (Bug → Feature → Documentación)";
	}
}
