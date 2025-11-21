package cl.ucn.poo.taller3.domain.constants;

	/**
	 * Clase con constantes para roles de usuario
	 */
	public final class RolUsuario {
	    public static final String ADMINISTRADOR = "Administrador";
	    public static final String COLABORADOR = "Colaborador";
	    
	    private RolUsuario() {} // No instanciable
	    
	    /**
	     * Valida si un rol es válido
	     */
	    public static boolean esRolValido(String rol) {
	        return rol != null && (
	            rol.equals(ADMINISTRADOR) || 
	            rol.equals(COLABORADOR)
	        );
	    }
	    
	    /**
	     * Obtiene rol por defecto
	     */
	    public static String getRolPorDefecto() {
	        return COLABORADOR;
	    }
	}


