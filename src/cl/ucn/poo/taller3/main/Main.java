/**
NICOLAS IGNACIO BUGUEÑO REMENTERIA ICCI/ 20.007.300-2
   ALONSO HERNAN JORQUERA RODRIGUEZ ITI /  20.948.058-1
 * 
 * Taller 3 - Programación Orientada a Objetos II Semestre 2025
 */
package cl.ucn.poo.taller3.main;

import cl.ucn.poo.taller3.domain.models.*;
import cl.ucn.poo.taller3.domain.constants.*;
import cl.ucn.poo.taller3.service.Sistema;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Clase principal del sistema de gestión de proyectos
 * 
 */
public class Main {
	private static Sistema sistema;
	private static Scanner scanner;
	private static SimpleDateFormat dateFormat;

	public static void main(String[] args) {
		sistema = Sistema.getInstancia();
		scanner = new Scanner(System.in);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println("=========================================");
		System.out.println("     SISTEMA DE GESTIÓN DE PROYECTOS");
		System.out.println("           TASKFORGE Ltda.");
		System.out.println("=========================================");

		ejecutarSistema();
	}

	private static void ejecutarSistema() {
		while (true) {
			if (!sistema.estaAutenticado()) {
				mostrarMenuLogin();
			} else {
				sistema.getUsuarioActual().mostrarMenu();
				int opcion = leerEntero("Seleccione una opción: ");
				procesarOpcionMenu(opcion);
			}
		}
	}

	private static void mostrarMenuLogin() {
		System.out.println("\n=== INICIO DE SESIÓN ===");
		System.out.print("Usuario: ");
		String username = scanner.nextLine();
		System.out.print("Contraseña: ");
		String password = scanner.nextLine();

		if (sistema.autenticar(username, password)) {
			System.out.println("\n¡Bienvenido, " + username + "!");
		} else {
			System.out.println("\n Error: Usuario o contraseña incorrectos");
		}
	}

	private static void procesarOpcionMenu(int opcion) {
		if (sistema.esAdministrador()) {
			procesarMenuAdministrador(opcion);
		} else {
			procesarMenuColaborador(opcion);
		}
	}

	// ==================== MENÚ ADMINISTRADOR ====================

	private static void procesarMenuAdministrador(int opcion) {
		switch (opcion) {
		case 1:
			verProyectosYTareasCompletas();
			break;
		case 2:
			menuGestionProyectos();
			break;
		case 3:
			menuGestionTareas();
			break;
		case 4:
			menuAsignarPrioridades();
			break;
		case 5:
			generarReporteProyectos();
			break;
		case 6:
			cerrarSesion();
			break;
		default:
			System.out.println("❌ Opción no válida");
		}
	}

	private static void verProyectosYTareasCompletas() {
		System.out.println("\n=== LISTA COMPLETA DE PROYECTOS Y TAREAS ===");

		List<Proyecto> proyectos = sistema.obtenerProyectosCompletos();

		if (proyectos.isEmpty()) {
			System.out.println("No hay proyectos registrados.");
			return;
		}

		for (Proyecto proyecto : proyectos) {
			System.out.println("\n📁 PROYECTO: " + proyecto.getNombre());
			System.out.println("   ID: " + proyecto.getId());
			System.out.println("   Responsable: " + proyecto.getResponsable());
			System.out.println("   Total tareas: " + proyecto.getNumeroTareas());

			List<Tarea> tareas = proyecto.getTareas();
			if (tareas.isEmpty()) {
				System.out.println("   No hay tareas en este proyecto.");
			} else {
				System.out.println("   TAREAS:");
				for (Tarea tarea : tareas) {
					System.out.println("   └─ " + tarea.getTipo() + " [" + tarea.getId() + "]");
					System.out.println("      Descripción: " + tarea.getDescripcion());
					System.out.println("      Estado: " + tarea.getEstado());
					System.out.println("      Responsable: " + tarea.getResponsable());
					System.out.println("      Complejidad: " + tarea.getComplejidad());
					System.out.println("      Fecha: " + dateFormat.format(tarea.getFechaCreacion()));
				}
			}
			System.out.println("─────────────────────────────────────────");
		}
	}

	private static void menuGestionProyectos() {
		System.out.println("\n=== GESTIÓN DE PROYECTOS ===");
		System.out.println("1. Agregar proyecto");
		System.out.println("2. Eliminar proyecto");
		System.out.println("3. Volver");

		int opcion = leerEntero("Seleccione una opción: ");

		switch (opcion) {
		case 1:
			agregarProyecto();
			break;
		case 2:
			eliminarProyecto();
			break;
		case 3:
			return;
		default:
			System.out.println(" Opción no válida");
		}
	}

	private static void agregarProyecto() {
		System.out.println("\n--- AGREGAR NUEVO PROYECTO ---");
		System.out.print("ID del proyecto: ");
		String id = scanner.nextLine();
		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Responsable: ");
		String responsable = scanner.nextLine();

		if (sistema.crearProyecto(id, nombre, responsable)) {
			System.out.println(" Proyecto agregado exitosamente");
		} else {
			System.out.println(
					" Error: No se pudo agregar el proyecto. Verifique que el ID no exista y el responsable sea válido.");
		}
	}

	private static void eliminarProyecto() {
		System.out.println("\n--- ELIMINAR PROYECTO ---");
		System.out.print("ID del proyecto a eliminar: ");
		String id = scanner.nextLine();

		System.out.print("¿Está seguro de eliminar el proyecto " + id + " y todas sus tareas? (s/n): ");
		String confirmacion = scanner.nextLine();

		if (confirmacion.equalsIgnoreCase("s")) {
			if (sistema.eliminarProyecto(id)) {
				System.out.println(" Proyecto eliminado exitosamente");
			} else {
				System.out.println(" Error: No se pudo eliminar el proyecto. Verifique que el ID exista.");
			}
		} else {
			System.out.println("Operación cancelada.");
		}
	}

	private static void menuGestionTareas() {
		System.out.println("\n=== GESTIÓN DE TAREAS ===");
		System.out.println("1. Agregar tarea");
		System.out.println("2. Eliminar tarea");
		System.out.println("3. Volver");

		int opcion = leerEntero("Seleccione una opción: ");

		switch (opcion) {
		case 1:
			agregarTarea();
			break;
		case 2:
			eliminarTarea();
			break;
		case 3:
			return;
		default:
			System.out.println(" Opción no válida");
		}
	}

	private static void agregarTarea() {
		System.out.println("\n--- AGREGAR NUEVA TAREA ---");
		System.out.print("ID de la tarea: ");
		String id = scanner.nextLine();
		System.out.print("ID del proyecto: ");
		String proyectoId = scanner.nextLine();

		if (!sistema.existeProyecto(proyectoId)) {
			System.out.println(" Error: El proyecto no existe.");
			return;
		}

		System.out.println("Tipos de tarea disponibles:");
		System.out.println("1. " + TipoTarea.BUG);
		System.out.println("2. " + TipoTarea.FEATURE);
		System.out.println("3. " + TipoTarea.DOCUMENTACION);
		int tipoOp = leerEntero("Seleccione tipo (1-3): ");

		String tipo;
		switch (tipoOp) {
		case 1:
			tipo = TipoTarea.BUG;
			break;
		case 2:
			tipo = TipoTarea.FEATURE;
			break;
		case 3:
			tipo = TipoTarea.DOCUMENTACION;
			break;
		default:
			System.out.println(" Tipo no válido, se usará Feature por defecto");
			tipo = TipoTarea.FEATURE;
		}

		System.out.print("Descripción: ");
		String descripcion = scanner.nextLine();

		System.out.println("Colaboradores disponibles:");
		List<Usuario> colaboradores = sistema.obtenerColaboradores();
		for (int i = 0; i < colaboradores.size(); i++) {
			System.out.println((i + 1) + ". " + colaboradores.get(i).getUsername());
		}
		int colabOp = leerEntero("Seleccione colaborador (1-" + colaboradores.size() + "): ");

		String responsable;
		if (colabOp >= 1 && colabOp <= colaboradores.size()) {
			responsable = colaboradores.get(colabOp - 1).getUsername();
		} else {
			System.out.println("❌ Selección no válida");
			return;
		}

		System.out.println("Niveles de complejidad:");
		System.out.println("1. " + Complejidad.BAJA);
		System.out.println("2. " + Complejidad.MEDIA);
		System.out.println("3. " + Complejidad.ALTA);
		int compOp = leerEntero("Seleccione complejidad (1-3): ");

		String complejidad;
		switch (compOp) {
		case 1:
			complejidad = Complejidad.BAJA;
			break;
		case 2:
			complejidad = Complejidad.MEDIA;
			break;
		case 3:
			complejidad = Complejidad.ALTA;
			break;
		default:
			System.out.println(" Complejidad no válida, se usará Media por defecto");
			complejidad = Complejidad.MEDIA;
		}

		String estado = EstadoTarea.PENDIENTE;

		if (sistema.crearTarea(id, proyectoId, tipo, descripcion, estado, responsable, complejidad)) {
			System.out.println(" Tarea agregada exitosamente");
		} else {
			System.out.println(
					" Error: No se pudo agregar la tarea. Verifique que el ID no exista y el colaborador no tenga tareas en la misma fecha.");
		}
	}

	private static void eliminarTarea() {
		System.out.println("\n--- ELIMINAR TAREA ---");
		System.out.print("ID de la tarea a eliminar: ");
		String id = scanner.nextLine();

		if (sistema.eliminarTarea(id)) {
			System.out.println(" Tarea eliminada exitosamente");
		} else {
			System.out.println(" Error: No se pudo eliminar la tarea. Verifique que el ID exista.");
		}
	}

	private static void menuAsignarPrioridades() {
		System.out.println("\n=== ASIGNAR PRIORIDADES A TAREAS ===");

		List<Tarea> todasLasTareas = new ArrayList<>();
		List<Proyecto> proyectos = sistema.obtenerProyectosCompletos();
		for (Proyecto proyecto : proyectos) {
			todasLasTareas.addAll(proyecto.getTareas());
		}

		if (todasLasTareas.isEmpty()) {
			System.out.println("No hay tareas para priorizar.");
			return;
		}

		System.out.println("Estrategias de priorización disponibles:");
		List<String> estrategias = sistema.obtenerEstrategiasPrioridad();
		for (int i = 0; i < estrategias.size(); i++) {
			System.out.println((i + 1) + ". " + estrategias.get(i));
		}

		int opcion = leerEntero("Seleccione estrategia (1-3): ");
		String estrategiaSeleccionada;

		switch (opcion) {
		case 1:
			estrategiaSeleccionada = "fecha";
			break;
		case 2:
			estrategiaSeleccionada = "impacto";
			break;
		case 3:
			estrategiaSeleccionada = "complejidad";
			break;
		default:
			System.out.println("❌ Opción no válida, usando prioridad por fecha");
			estrategiaSeleccionada = "fecha";
		}

		List<Tarea> tareasPriorizadas = sistema.priorizarTareas(todasLasTareas, estrategiaSeleccionada);

		System.out.println("\n📊 TAREAS PRIORIZADAS (" + estrategiaSeleccionada.toUpperCase() + ")");
		System.out.println("=========================================");

		for (int i = 0; i < tareasPriorizadas.size(); i++) {
			Tarea tarea = tareasPriorizadas.get(i);
			System.out.println((i + 1) + ". " + tarea.getTipo() + " - " + tarea.getDescripcion());
			System.out.println("   Proyecto: " + tarea.getProyectoId() + " | Estado: " + tarea.getEstado());
			System.out
					.println("   Responsable: " + tarea.getResponsable() + " | Complejidad: " + tarea.getComplejidad());
			System.out.println("   Fecha: " + dateFormat.format(tarea.getFechaCreacion()));
		}
	}

	private static void generarReporteProyectos() {
		System.out.println("\n--- GENERANDO REPORTE ---");

		if (sistema.generarReporteProyectos()) {
			System.out.println(" Reporte generado exitosamente en: reportes/reporte.txt");
		} else {
			System.out.println(" Error al generar el reporte");
		}
	}

	// ==================== MENÚ COLABORADOR ====================

	private static void procesarMenuColaborador(int opcion) {
		switch (opcion) {
		case 1:
			verProyectosDisponibles();
			break;
		case 2:
			verMisTareasAsignadas();
			break;
		case 3:
			actualizarEstadoTarea();
			break;
		case 4:
			menuAplicarVisitor();
			break;
		case 5:
			cerrarSesion();
			break;
		default:
			System.out.println(" Opción no válida");
		}
	}

	private static void verProyectosDisponibles() {
		System.out.println("\n=== PROYECTOS DISPONIBLES ===");

		List<Proyecto> proyectos = sistema.obtenerProyectosDisponibles();

		if (proyectos.isEmpty()) {
			System.out.println("No hay proyectos disponibles.");
			return;
		}

		for (Proyecto proyecto : proyectos) {
			proyecto.mostrarInformacion();
			System.out.println("────────────────────");
		}
	}

	private static void verMisTareasAsignadas() {
		System.out.println("\n=== MIS TAREAS ASIGNADAS ===");

		List<Tarea> misTareas = sistema.obtenerMisTareas();

		if (misTareas.isEmpty()) {
			System.out.println("No tienes tareas asignadas.");
			return;
		}

		for (Tarea tarea : misTareas) {
			System.out.println("📝 " + tarea.getTipo() + " [" + tarea.getId() + "]");
			System.out.println("   Proyecto: " + tarea.getProyectoId());
			System.out.println("   Descripción: " + tarea.getDescripcion());
			System.out.println("   Estado: " + tarea.getEstado());
			System.out.println("   Complejidad: " + tarea.getComplejidad());
			System.out.println("   Fecha: " + dateFormat.format(tarea.getFechaCreacion()));
			System.out.println("   ────────────────────");
		}
	}

	private static void actualizarEstadoTarea() {
		System.out.println("\n=== ACTUALIZAR ESTADO DE TAREA ===");

		List<Tarea> misTareas = sistema.obtenerMisTareas();

		if (misTareas.isEmpty()) {
			System.out.println("No tienes tareas asignadas.");
			return;
		}

		System.out.println("Tareas que puedes actualizar:");
		List<Tarea> tareasActualizables = new ArrayList<>();
		int index = 1;

		for (Tarea tarea : misTareas) {
			if (!EstadoTarea.COMPLETADA.equals(tarea.getEstado())) {
				System.out.println(index + ". " + tarea.getTipo() + " - " + tarea.getDescripcion() + " ("
						+ tarea.getEstado() + ")");
				tareasActualizables.add(tarea);
				index++;
			}
		}

		if (tareasActualizables.isEmpty()) {
			System.out.println("No hay tareas que puedas actualizar.");
			return;
		}

		int seleccion = leerEntero("Seleccione tarea (1-" + tareasActualizables.size() + "): ");
		if (seleccion < 1 || seleccion > tareasActualizables.size()) {
			System.out.println(" Selección no válida");
			return;
		}

		Tarea tareaSeleccionada = tareasActualizables.get(seleccion - 1);

		System.out.println("Estados disponibles:");
		String estadoActual = tareaSeleccionada.getEstado();

		if (EstadoTarea.PENDIENTE.equals(estadoActual)) {
			System.out.println("1. " + EstadoTarea.EN_PROGRESO);
			System.out.println("2. " + EstadoTarea.COMPLETADA);
		} else if (EstadoTarea.EN_PROGRESO.equals(estadoActual)) {
			System.out.println("1. " + EstadoTarea.COMPLETADA);
		}

		int estadoOp = leerEntero("Seleccione nuevo estado: ");
		String nuevoEstado;

		if (EstadoTarea.PENDIENTE.equals(estadoActual)) {
			nuevoEstado = (estadoOp == 1) ? EstadoTarea.EN_PROGRESO : EstadoTarea.COMPLETADA;
		} else {
			nuevoEstado = EstadoTarea.COMPLETADA;
		}

		if (sistema.actualizarEstadoTarea(tareaSeleccionada.getId(), nuevoEstado)) {
			System.out.println(" Estado actualizado exitosamente a: " + nuevoEstado);
		} else {
			System.out.println(" Error al actualizar el estado");
		}
	}

	private static void menuAplicarVisitor() {
		System.out.println("\n=== APLICAR ACCIONES POR TIPO DE TAREA ===");

		List<Tarea> misTareas = sistema.obtenerMisTareas();

		if (misTareas.isEmpty()) {
			System.out.println("No tienes tareas asignadas.");
			return;
		}

		System.out.println("Tus tareas:");
		for (int i = 0; i < misTareas.size(); i++) {
			Tarea tarea = misTareas.get(i);
			System.out.println((i + 1) + ". " + tarea.getTipo() + " - " + tarea.getDescripcion());
		}

		int tareaOp = leerEntero("Seleccione tarea (1-" + misTareas.size() + "): ");
		if (tareaOp < 1 || tareaOp > misTareas.size()) {
			System.out.println(" Selección no válida");
			return;
		}

		Tarea tareaSeleccionada = misTareas.get(tareaOp - 1);

		System.out.println("Acciones disponibles (Visitor Pattern):");
		List<String> visitors = sistema.obtenerVisitorsDisponibles();
		for (int i = 0; i < visitors.size(); i++) {
			System.out.println((i + 1) + ". " + visitors.get(i));
		}

		int visitorOp = leerEntero("Seleccione acción (1-3): ");
		String visitorSeleccionado;

		switch (visitorOp) {
		case 1:
			visitorSeleccionado = "criticidad";
			break;
		case 2:
			visitorSeleccionado = "tiempo";
			break;
		case 3:
			visitorSeleccionado = "calidad";
			break;
		default:
			System.out.println(" Opción no válida, usando criticidad");
			visitorSeleccionado = "criticidad";
		}

		String resultado = sistema.aplicarVisitorTarea(tareaSeleccionada, visitorSeleccionado);

		System.out.println("\n📊 RESULTADO DEL ANÁLISIS:");
		System.out.println("Tarea: " + tareaSeleccionada.getTipo() + " - " + tareaSeleccionada.getDescripcion());
		System.out.println("Análisis: " + resultado);
	}

	private static void cerrarSesion() {
		System.out.println("\nCerrando sesión... ¡Hasta pronto!");
		sistema.cerrarSesion();
	}

	// ==================== MÉTODOS UTILITARIOS ====================

	private static int leerEntero(String mensaje) {
		while (true) {
			try {
				System.out.print(mensaje);
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(" Error: Debe ingresar un número válido.");
			}
		}
	}
}

