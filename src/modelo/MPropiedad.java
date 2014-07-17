package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
public class MPropiedad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2329144330963269725L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Transient
	private boolean esNueva;// sirve para bloquear propiedades de dominios con anotaciones creadas.
	
	public boolean isEsNueva() {
		return esNueva;
	}
	public void setEsNueva(boolean esNueva) {
		this.esNueva = esNueva;
	}
	
	@Transient
	private Long idTemporal=0L;
	
	public Long getIdTemporal() {
			return idTemporal;
	}

	public void setIdTemporal(Long idTemporal) {
		this.idTemporal = idTemporal;
	}

	public MPropiedad() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String nombre;
	@Enumerated
	private TipoDeDato tipo;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public MPropiedad(String nombre, TipoDeDato tipo) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.esNueva=true;
	}
	public TipoDeDato getTipo() {
		return tipo;
	}
	public void setTipo(TipoDeDato tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString(){
		return "nombre:" + this.nombre + " tipo:" + this.tipo;
	}
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final MPropiedad other = (MPropiedad)obj;
	        if (!idTemporal.equals(other.idTemporal)) return false;
	        return true;
	    }
}
