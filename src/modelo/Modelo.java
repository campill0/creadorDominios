package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import controlador.FactoriaVideos;

@Entity
public class Modelo implements Serializable {
	//================== attributos =====================
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true, mappedBy="modelo")
	private ArrayList<Concepto> conceptos;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true, mappedBy="modelo")
	private ArrayList<Referencia> referencias;
	@ManyToOne
	private MModelo mmodelo;
	private String nombre;
	private String descripcion;
	@OneToOne(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true,mappedBy="modelo")
	private FragmentoVideo fragmentoVideo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	@Transient
	private Long idTemporal=0L;
	public Long getIdTemporal() {
		return idTemporal;
	}
	public void setIdTemporal(Long idTemporal) {
		this.idTemporal = idTemporal;
	}
	public MModelo getMmodelo() {
		return mmodelo;
	}

	public void setMmodelo(MModelo mmodelo) {
		this.mmodelo = mmodelo;
	}

	//================== constructores =====================
	public Modelo() {
		super();
		
		this.fechaCreacion= new Date();
		this.conceptos = new ArrayList<Concepto>();
		this.referencias = new ArrayList<Referencia>();
	}
	public Modelo(MModelo mmodelo, String nombre, String descripcion, Video video) {
		super();
		this.fechaCreacion= new Date();
		this.mmodelo=mmodelo;
		this.nombre=nombre;
		this.descripcion=descripcion;
		FactoriaVideos fv= FactoriaVideos.instancia();
		this.fragmentoVideo=fv.createFragmentoVideo(this, video);
		conceptos = new ArrayList<Concepto>();
		referencias = new ArrayList<Referencia>();
	}
	
	
	//================== Setters & getters =====================
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl(){
		return fragmentoVideo.getUrl();
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public ArrayList<Concepto> getConceptos() {
		return conceptos;
	}
	public void setConceptos(ArrayList<Concepto> conceptos) {
		this.conceptos = conceptos;
	}
	public ArrayList<Referencia> getReferencias() {
		return referencias;
	}
	public void setReferencias(ArrayList<Referencia> referencias) {
		this.referencias = referencias;
	}
	

	 public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final Modelo other = (Modelo)obj;
	        if (!idTemporal.equals(other.idTemporal)) return false;
	        return true;
	    }
	public FragmentoVideo getFragmentoVideo() {
		return fragmentoVideo;
	}
	public void setFragmentoVideo(FragmentoVideo fragmentoVideo) {
		this.fragmentoVideo = fragmentoVideo;
	}

	

}
