package modelo;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Propiedad implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch=FetchType.EAGER)
	private MPropiedad mPropiedad;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Transient
	private Long idTemporal=0L;
	public Long getIdTemporal() {
		return idTemporal;
	}
	public void setIdTemporal(Long idTemporal) {
		this.idTemporal = idTemporal;
	}
	
	@Lob
	private Serializable valor;
	
	public Propiedad() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Propiedad(MPropiedad mPropiedad,Serializable valor) {
		super();
		this.mPropiedad=mPropiedad;
		this.valor = valor;
	}
	public MPropiedad getmPropiedad() {
		return mPropiedad;
	}
	public void setmPropiedad(MPropiedad mPropiedad) {
		this.mPropiedad = mPropiedad;
	}
	public Serializable getValor() {
		return valor;
	}
	
	public void setValorString (String valor){
		
	}
	public void setValor(Serializable valor) {
		this.valor = valor;
	}
	@Override
	public String toString(){
		return  this.mPropiedad.getNombre() + " tipo:" + this.mPropiedad.getTipo() + " =>" + valor;
	}
	
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final Propiedad other = (Propiedad)obj;
	        if (!idTemporal.equals(other.idTemporal)) return false;
	        return true;
	    }
}
