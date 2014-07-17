package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class MReferencia  implements Serializable{
	
	private static final long serialVersionUID = 4784913522157467248L;
	@ManyToOne
	private MModelo mmodelo;
	@Transient
	private Long idTemporal=0L;
	public Long getIdTemporal() {
		return idTemporal;
	}

	public void setIdTemporal(Long idTemporal) {
		this.idTemporal = idTemporal;
	}
	@Transient
	private boolean esNueva;// sirve para bloquear propiedades de dominios con anotaciones creadas.
	
	public boolean isEsNueva() {
		return esNueva;
	}
	public void setEsNueva(boolean esNueva) {
		this.esNueva = esNueva;
	}
	public MModelo getMmodelo() {
		return mmodelo;
	}

	public void setMmodelo(MModelo mmodelo) {
		this.mmodelo = mmodelo;
	}
	@OneToOne
	private MConcepto referenciante;
	@OneToOne
	private MConcepto referenciado;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String etiqueta;
	public MReferencia() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MReferencia(MConcepto referenciante, MConcepto referenciado,
			String etiqueta) {
		super();
		//this.id= getNewId();
		this.referenciante = referenciante;
		this.referenciado = referenciado;
		this.etiqueta = etiqueta;
	}
	public MConcepto getReferenciante() {
		return referenciante;
	}
	public void setReferenciante(MConcepto referenciante) {
		this.referenciante = referenciante;
	}
	public MConcepto getReferenciado() {
		return referenciado;
	}
	public void setReferenciado(MConcepto referenciado) {
		this.referenciado = referenciado;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	@Override
	public String toString()
	{
		return id+": "+ referenciante + " " +  etiqueta + " " + referenciado;
	}
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final MReferencia other = (MReferencia)obj;
	        if (!idTemporal.equals(other.idTemporal)) return false;
	        return true;
	    }
}
