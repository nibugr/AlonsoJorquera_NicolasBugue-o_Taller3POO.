	package cl.ucn.poo.taller3.domain.constants;

	/**
	 * Clase con constantes para tipos de tarea
	 */
	public final class TipoTarea {
	    public static final String BUG = "Bug";
	    public static final String FEATURE = "Feature";
	    public static final String DOCUMENTACION = "Documentacion";
	    
	    private TipoTarea() {} // No instanciable
	    
	    /**
	     * Valida si un tipo es válido
	     */
	    public static boolean esTipoValido(String tipo) {
	        return tipo != null && (
	            tipo.equals(BUG) || 
	            tipo.equals(FEATURE) || 
	            tipo.equals(DOCUMENTACION)
	        );
	    }
	    
	    /**
	     * Obtiene tipo por defecto
	     */
	    public static String getTipoPorDefecto() {
	        return FEATURE;
	    }
	}

