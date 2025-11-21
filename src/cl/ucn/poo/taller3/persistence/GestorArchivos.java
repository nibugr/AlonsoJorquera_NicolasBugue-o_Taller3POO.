
package cl.ucn.poo.taller3.persistence;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Gestor de archivos (Singleton) - Maneja lectura/escritura de archivos
 */
public class GestorArchivos {
	private static GestorArchivos instancia;
	private static final String CARPETA_DATA = "data";
	private static final String CARPETA_REPORTES = "reportes";

	private GestorArchivos() {
		crearCarpetaSiNoExiste(CARPETA_DATA);
		crearCarpetaSiNoExiste(CARPETA_REPORTES);
	}

	public static GestorArchivos getInstancia() {
		if (instancia == null) {
			instancia = new GestorArchivos();
		}
		return instancia;
	}

	private void crearCarpetaSiNoExiste(String nombreCarpeta) {
		File carpeta = new File(nombreCarpeta);
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}
	}

	public String[] leerArchivo(String nombreArchivo) {
		try {
			File archivo = new File(CARPETA_DATA + File.separator + nombreArchivo);
			if (!archivo.exists()) {
				System.out.println("Archivo no encontrado: " + nombreArchivo);
				return new String[0];
			}

			BufferedReader reader = new BufferedReader(new FileReader(archivo));
			java.util.List<String> lineas = new java.util.ArrayList<>();
			String linea;

			while ((linea = reader.readLine()) != null) {
				if (!linea.trim().isEmpty()) {
					lineas.add(linea);
				}
			}
			reader.close();

			return lineas.toArray(new String[0]);

		} catch (IOException e) {
			System.out.println("Error al leer archivo: " + e.getMessage());
			return new String[0];
		}
	}

	public boolean escribirArchivo(String nombreArchivo, java.util.List<String> lineas, boolean append) {
		try {
			File archivo = new File(CARPETA_DATA + File.separator + nombreArchivo);
			BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, append));

			for (String linea : lineas) {
				writer.write(linea);
				writer.newLine();
			}

			writer.close();
			return true;

		} catch (IOException e) {
			System.out.println("Error al escribir archivo: " + e.getMessage());
			return false;
		}
	}

	public boolean escribirReporte(String nombreArchivo, String contenido) {
		try {
			File archivo = new File(CARPETA_REPORTES + File.separator + nombreArchivo);
			BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
			writer.write(contenido);
			writer.close();
			return true;

		} catch (IOException e) {
			System.out.println("Error al escribir reporte: " + e.getMessage());
			return false;
		}
	}

	public String getFechaActual() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
}

