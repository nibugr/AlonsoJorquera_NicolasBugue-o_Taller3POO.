package cl.ucn.poo.taller3.domain.strategy;

import cl.ucn.poo.taller3.domain.models.Tarea;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Estrategia de prioridad por fecha de creación
 */
public class PrioridadFecha implements EstrategiaPrioridad {

	@Override
	public List<Tarea> priorizar(List<Tarea> tareas) {
		List<Tarea> tareasOrdenadas = new ArrayList<>(tareas);
		Collections.sort(tareasOrdenadas, new Comparator<Tarea>() {
			@Override
			public int compare(Tarea t1, Tarea t2) {
				return t1.getFechaCreacion().compareTo(t2.getFechaCreacion());
			}
		});
		return tareasOrdenadas;
	}

	@Override
	public String getNombre() {
		return "Prioridad por Fecha (más antiguas primero)";
	}
}
