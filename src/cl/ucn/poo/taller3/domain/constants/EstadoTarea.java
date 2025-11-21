	package cl.ucn.poo.taller3.domain.constants;

	/**
	 * Clase con constantes para estados de tarea
	 */
	public final class EstadoTarea {
	    public static final String PENDIENTE = "Pendiente";
	    public static final String EN_PROGRESO = "En progreso";
	    public static final String COMPLETADA = "Completada";
	    
	    private EstadoTarea() {} // No instanciable
	    
	    /**
	     * Valida si un estado es válido
	     */
	    public static boolean esEstadoValido(String estado) {
	        return estado != null && (
	            estado.equals(PENDIENTE) || 
	            estado.equals(EN_PROGRESO) || 
	            estado.equals(COMPLETADA)
	        );
	    }
	    
	    /**
	     * Obtiene estado por defecto
	     */
	    public static String getEstadoPorDefecto() {
	        return PENDIENTE;
	    }
	}

