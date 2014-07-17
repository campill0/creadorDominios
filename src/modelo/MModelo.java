package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
@Entity
public class MModelo implements Serializable {
	//================== attributos =====================	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Transient
	private Long idTemporal=0L;
	public Long getIdTemporal() {
		return idTemporal;
	}
	public void setIdTemporal(Long idTemporal) {
		this.idTemporal = idTemporal;
	}
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true, mappedBy="mmodelo")
	private List<MReferencia> mReferencias;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true, mappedBy="mmodelo")
	
	private List<MConcepto> mConceptos;
	private String descripcion;
	private String nombre; 
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	
	public List<MConcepto> getmConceptos() {
		//System.out.println("esto dando conceptos cuantos? " + mConceptos.size());
		return mConceptos;
	}
	public void setmConceptos(List<MConcepto> mConceptos) {
		this.mConceptos = mConceptos;
	}
	public List<MReferencia> getmReferencias() {
		return mReferencias;
	}
	public void setmReferencias(List<MReferencia> mReferencias) {
		this.mReferencias = mReferencias;
	}
	public String getNombre() {
		return nombre; 
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	//================== constructores =====================	
	public MModelo() {
		super();
		this.fechaCreacion=new Date();
		this.mConceptos = new ArrayList<MConcepto>();
		this.mReferencias = new ArrayList<MReferencia>();
		
		
	}
	public MModelo(String nombre) {
		super();
		this.fechaCreacion=new Date();
		this.mConceptos = new ArrayList<MConcepto>();
		this.mReferencias = new ArrayList<MReferencia>();
		this.nombre=nombre;
		
		
	}

	//================== Setters & getters =====================	
	
	
	public Long getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	@Override
	public String toString()
	{
		return id+":"+this.nombre;
	} 
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final MModelo other = (MModelo)obj;
	        if (!id.equals(other.id)) return false;
	        return true;
	    }

}
