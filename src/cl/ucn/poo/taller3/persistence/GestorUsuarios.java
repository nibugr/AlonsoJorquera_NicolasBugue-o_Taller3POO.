
package cl.ucn.poo.taller3.persistence;

import cl.ucn.poo.taller3.domain.models.Usuario;
import cl.ucn.poo.taller3.domain.models.Administrador;
import cl.ucn.poo.taller3.domain.models.Colaborador;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de usuarios - Maneja la persistencia de usuarios
 */
public class GestorUsuarios {
	private static GestorUsuarios instancia;
	private List<Usuario> usuarios;
	private GestorArchivos gestorArchivos;

	private GestorUsuarios() {
		this.gestorArchivos = GestorArchivos.getInstancia();
		this.usuarios = new ArrayList<>();
		cargarUsuarios();
	}

	public static GestorUsuarios getInstancia() {
		if (instancia == null) {
			instancia = new GestorUsuarios();
		}
		return instancia;
	}

	private void cargarUsuarios() {
		String[] lineas = gestorArchivos.leerArchivo("usuarios.txt");

		for (String linea : lineas) {
			String[] partes = linea.split("\\|");
			if (partes.length == 3) {
				String username = partes[0].trim();
				String password = partes[1].trim();
				String rol = partes[2].trim();

				Usuario usuario;
				if ("Administrador".equalsIgnoreCase(rol)) {
					usuario = new Administrador(username, password);
				} else {
					usuario = new Colaborador(username, password);
				}

				usuarios.add(usuario);
			}
		}
	}

	public Usuario autenticar(String username, String password) {
		for (Usuario usuario : usuarios) {
			if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
				return usuario;
			}
		}
		return null;
	}

	public boolean existeUsuario(String username) {
		for (Usuario usuario : usuarios) {
			if (usuario.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	public List<Usuario> getUsuarios() {
		return new ArrayList<>(usuarios);
	}

	public List<Usuario> getColaboradores() {
		List<Usuario> colaboradores = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			if (usuario.esColaborador()) {
				colaboradores.add(usuario);
			}
		}
		return colaboradores;
	}

	public boolean agregarUsuario(Usuario usuario) {
		if (!existeUsuario(usuario.getUsername())) {
			usuarios.add(usuario);
			return guardarUsuarios();
		}
		return false;
	}

	private boolean guardarUsuarios() {
		List<String> lineas = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			String linea = usuario.getUsername() + "|" + usuario.getPassword() + "|" + usuario.getRol();
			lineas.add(linea);
		}
		return gestorArchivos.escribirArchivo("usuarios.txt", lineas, false);
	}
}

