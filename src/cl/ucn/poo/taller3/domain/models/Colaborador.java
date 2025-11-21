package cl.ucn.poo.taller3.domain.models;

import cl.ucn.poo.taller3.domain.constants.RolUsuario;

/**
 * Clase que representa un usuario Colaborador
 */
public class Colaborador extends Usuario {

	public Colaborador(String username, String password) {
		super(username, password, RolUsuario.COLABORADOR);
	}

	@Override
	public void mostrarMenu() {
		System.out.println("\n=== MENÚ COLABORADOR ===");
		System.out.println("1. Ver proyectos disponibles");
		System.out.println("2. Ver mis tareas asignadas");
		System.out.println("3. Actualizar estado de tarea");
		System.out.println("4. Aplicar acciones por tipo de tarea");
		System.out.println("5. Cerrar sesión");
	}

	@Override
	public String toString() {
		return "Colaborador{" + "username='" + getUsername() + '\'' + '}';
	}
}
