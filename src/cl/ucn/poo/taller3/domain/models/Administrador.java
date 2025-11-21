package cl.ucn.poo.taller3.domain.models;

	import cl.ucn.poo.taller3.domain.constants.RolUsuario;

	/**
	 * Clase que representa un usuario Administrador
	 */
	public class Administrador extends Usuario {
	    
	    public Administrador(String username, String password) {
	        super(username, password, RolUsuario.ADMINISTRADOR);
	    }
	    
	    @Override
	    public void mostrarMenu() {
	        System.out.println("\n=== MENÚ ADMINISTRADOR ===");
	        System.out.println("1. Ver lista completa de proyectos y tareas");
	        System.out.println("2. Agregar o eliminar proyecto");
	        System.out.println("3. Agregar o eliminar tarea");
	        System.out.println("4. Asignar prioridades a tareas");
	        System.out.println("5. Generar reporte de proyectos");
	        System.out.println("6. Cerrar sesión");
	    }
	    
	    @Override
	    public String toString() {
	        return "Administrador{" +
	                "username='" + getUsername() + '\'' +
	                '}';
	    }
	}


