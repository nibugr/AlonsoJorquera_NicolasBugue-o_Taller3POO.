package cl.ucn.poo.taller3.domain.models;

import cl.ucn.poo.taller3.domain.constants.TipoTarea;
import cl.ucn.poo.taller3.domain.constants.EstadoTarea;
import cl.ucn.poo.taller3.domain.constants.Complejidad;
import cl.ucn.poo.taller3.domain.visitor.TareaVisitor;
import java.util.Date;

/**
 * Clase abstracta que representa una tarea (Patrón Factory)
 */
public abstract class Tarea {
	private String id;
	private String proyectoId;
	private String tipo;
	private String descripcion;
	private String estado;
	private String responsable;
	private String complejidad;
	private Date fechaCreacion;

	protected Tarea(String id, String proyectoId, String tipo, String descripcion, String estado, String responsable,
			String complejidad, Date fechaCreacion) {
		this.id = id;
		this.proyectoId = proyectoId;
		this.tipo = TipoTarea.esTipoValido(tipo) ? tipo : TipoTarea.getTipoPorDefecto();
		this.descripcion = descripcion;
		this.estado = EstadoTarea.esEstadoValido(estado) ? estado : EstadoTarea.getEstadoPorDefecto();
		this.responsable = responsable;
		this.complejidad = Complejidad.esComplejidadValida(complejidad) ? complejidad
				: Complejidad.getComplejidadPorDefecto();
		this.fechaCreacion = fechaCreacion != null ? fechaCreacion : new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		if (TipoTarea.esTipoValido(tipo))
			this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		if (EstadoTarea.esEstadoValido(estado))
			this.estado = estado;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getComplejidad() {
		return complejidad;
	}

	public void setComplejidad(String complejidad) {
		if (Complejidad.esComplejidadValida(complejidad))
			this.complejidad = complejidad;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public abstract void accept(TareaVisitor visitor);

	public static Tarea crearTarea(String id, String proyectoId, String tipo, String descripcion, String estado,
			String responsable, String complejidad, Date fechaCreacion) {
		switch (tipo) {
		case TipoTarea.BUG:
			return new Bug(id, proyectoId, descripcion, estado, responsable, complejidad, fechaCreacion);
		case TipoTarea.FEATURE:
			return new Feature(id, proyectoId, descripcion, estado, responsable, complejidad, fechaCreacion);
		case TipoTarea.DOCUMENTACION:
			return new Documentacion(id, proyectoId, descripcion, estado, responsable, complejidad, fechaCreacion);
		default:
			return new Feature(id, proyectoId, descripcion, estado, responsable, complejidad, fechaCreacion);
		}
	}

	@Override
	public String toString() {
		return "Tarea{" + "id='" + id + '\'' + ", proyectoId='" + proyectoId + '\'' + ", tipo='" + tipo + '\''
				+ ", descripcion='" + descripcion + '\'' + ", estado='" + estado + '\'' + ", responsable='"
				+ responsable + '\'' + ", complejidad='" + complejidad + '\'' + ", fechaCreacion=" + fechaCreacion
				+ '}';
	}

	public void mostrarInformacion() {
		System.out.println("ID: " + id);
		System.out.println("Proyecto: " + proyectoId);
		System.out.println("Tipo: " + tipo);
		System.out.println("Descripción: " + descripcion);
		System.out.println("Estado: " + estado);
		System.out.println("Responsable: " + responsable);
		System.out.println("Complejidad: " + complejidad);
		System.out.println("Fecha: " + fechaCreacion);
	}
}
