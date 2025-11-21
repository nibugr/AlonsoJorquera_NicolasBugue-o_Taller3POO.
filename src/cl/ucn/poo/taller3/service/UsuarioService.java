
package cl.ucn.poo.taller3.service;

import cl.ucn.poo.taller3.domain.models.Usuario;
import cl.ucn.poo.taller3.persistence.GestorUsuarios;
import java.util.List;

/**
 * Servicio específico para operaciones de usuarios
 */
public class UsuarioService {
	private static UsuarioService instancia;
	private GestorUsuarios gestorUsuarios;

	private UsuarioService() {
		this.gestorUsuarios = GestorUsuarios.getInstancia();
	}

	public static UsuarioService getInstancia() {
		if (instancia == null) {
			instancia = new UsuarioService();
		}
		return instancia;
	}

	public List<Usuario> obtenerTodosLosUsuarios() {
		return gestorUsuarios.getUsuarios();
	}

	public boolean existeUsuario(String username) {
		return gestorUsuarios.existeUsuario(username);
	}

	public boolean validarCredenciales(String username, String password) {
		return username != null && !username.trim().isEmpty() && password != null && !password.trim().isEmpty();
	}
}

