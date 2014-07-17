package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;



@Entity
public class MConcepto implements Serializable{

	private static final long serialVersionUID = 5264507555396204619L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private MModelo mmodelo;
	@Embedded
	private Posicion posicion;
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

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	private String nombre;
	
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true)
	private List<MPropiedad> mPropiedades;
	
	

	public Long getId() {
		return id;
	}
	
	/*
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		MConcepto mc = (MConcepto)obj;
		if(mc.getId()== this.getId()){
			return true;
		}
		return false;
	}
   */
	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public MConcepto() {
		super();
		this.nombre="";
		this.mPropiedades=new  ArrayList<MPropiedad>();
		this.posicion=new Posicion(10,10);
		
		
	}
	public MPropiedad getPropiedad(String nombre){
		
		for (MPropiedad propiedad : mPropiedades) {
			if(propiedad.getNombre().equals(nombre)){
				return propiedad;
			}
		}
		return null;
	}
	
	public List<MPropiedad> getmPropiedades() {
		return mPropiedades;
	}

	public void setmPropiedades(List<MPropiedad> mPropiedades) {
		this.mPropiedades = mPropiedades;
	}
	
	public MConcepto(String nombre) {
		super();
		//this.id= getNexId();
		//nexId++;
		
		this.nombre =nombre;
	
		
		mPropiedades= new ArrayList<MPropiedad>();
		
	}
	

	@Override
	public String toString()
	{
		//Se usa en controlador.MConceptoConverter, cambiar este método afectaría al converter.
		return mmodelo.getId()+":"+idTemporal+":"+nombre;
	} 

	public MConcepto(String nombre, List<MReferencia> referencias,
			List<MPropiedad> propiedades) {
		super();
		
		this.nombre = nombre;
	
		this.mPropiedades = propiedades;
	}
	 
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final MConcepto other = (MConcepto)obj;
	        if (!idTemporal.equals(other.idTemporal)) return false;
	        return true;
	    }
}
