package cl.ucn.poo.taller3.domain.strategy;

import cl.ucn.poo.taller3.domain.models.Tarea;
import cl.ucn.poo.taller3.domain.constants.Complejidad;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Estrategia de prioridad por complejidad
 */
public class PrioridadComplejidad implements EstrategiaPrioridad {

	@Override
	public List<Tarea> priorizar(List<Tarea> tareas) {
		List<Tarea> tareasOrdenadas = new ArrayList<>(tareas);
		Collections.sort(tareasOrdenadas, new Comparator<Tarea>() {
			@Override
			public int compare(Tarea t1, Tarea t2) {
				return Integer.compare(getValorComplejidad(t1), getValorComplejidad(t2));
			}

			private int getValorComplejidad(Tarea tarea) {
				switch (tarea.getComplejidad()) {
				case Complejidad.ALTA:
					return 1;
				case Complejidad.MEDIA:
					return 2;
				case Complejidad.BAJA:
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
		return "Prioridad por Complejidad (Alta → Media → Baja)";
	}
}
