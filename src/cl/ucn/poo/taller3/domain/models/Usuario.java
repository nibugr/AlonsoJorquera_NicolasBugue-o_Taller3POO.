package cl.ucn.poo.taller3.domain.models;

	import cl.ucn.poo.taller3.domain.constants.RolUsuario;

	/**
	 * Clase abstracta que representa un usuario del sistema
	 */
	public abstract class Usuario {
	    private String username;
	    private String password;
	    private String rol;
	    
	    /**
	     * Constructor
	     */
	    public Usuario(String username, String password, String rol) {
	        this.username = username;
	        this.password = password;
	        this.rol = RolUsuario.esRolValido(rol) ? rol : RolUsuario.getRolPorDefecto();
	    }
	    
	    public String getUsername() {
	        return username;
	    }
	    
	    public void setUsername(String username) {
	        this.username = username;
	    }
	    
	    public String getPassword() {
	        return password;
	    }
	    
	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    public String getRol() {
	        return rol;
	    }
	    
	    public void setRol(String rol) {
	        if (RolUsuario.esRolValido(rol)) {
	            this.rol = rol;
	        }
	    }
	    
	    public boolean esAdministrador() {
	        return RolUsuario.ADMINISTRADOR.equals(this.rol);
	    }
	    
	    public boolean esColaborador() {
	        return RolUsuario.COLABORADOR.equals(this.rol);
	    }
	    
	    public abstract void mostrarMenu();
	    
	    @Override
	    public String toString() {
	        return "Usuario{" +
	                "username='" + username + '\'' +
	                ", rol=" + rol +
	                '}';
	    }
	}

