package modelo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.PersistenceContextType;
import javax.persistence.Transient;

@Entity
public class Concepto implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Embedded
	private Posicion posicion;
	private String etiqueta;
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}



	@Transient
	private Long idTemporal=0L;
	public Posicion getPosicion() {
		return posicion;
	}
	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}
	public Long getIdTemporal() {
		return idTemporal;
	}
	public void setIdTemporal(Long idTemporal) {
		this.idTemporal = idTemporal;
	}
	public MConcepto getmConcepto() {
		return mConcepto;
	}

	public void setmConcepto(MConcepto mConcepto) {
		this.mConcepto = mConcepto;
	}
	


	@ManyToOne(fetch=FetchType.EAGER)
	private MConcepto mConcepto;
	
	@ManyToOne
	private Modelo modelo;
	
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Modelo getModelo() {
		return modelo;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	//private List<Concepto> contenido;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true)
	private List<Propiedad> propiedades;
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}



/*
	public List<Concepto> getContenido() {
		return contenido;
	}

	public void setContenido(List<Concepto> contenido) {
		this.contenido = contenido;
	}
*/
	public Concepto(MConcepto mConcepto){
		super();
		//this.id= getNexId();
		this.mConcepto=mConcepto;
		propiedades = new ArrayList<Propiedad>();
		/*List<MPropiedad> props = concepto.getPropiedades();
		for (MPropiedad mPropiedad : props) {
				propiedades.add(new Propiedad(mPropiedad,null));
			
		
		}*/
		
	
	//	contenido= new ArrayList<Concepto>();
	}
	public Concepto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Propiedad getPropiedad(String nombre){
		
		for (Propiedad propiedad : propiedades) {
			if(	propiedad.getmPropiedad().getNombre().equals(nombre)){
				return propiedad;
			}
		}
		return null;
	}
	@Override
	public String toString()
	{
		return id+": Instancia de->"+mConcepto.getNombre();
	} 
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final Concepto other = (Concepto)obj;
	        if (!idTemporal.equals(other.idTemporal)) return false;
	       
	        return true;
	    }
}
