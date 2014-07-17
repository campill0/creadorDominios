package modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Entity
public class Referencia implements Serializable{
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
	public Referencia() {
		super();
		// TODO Auto-generated constructor stub
	}
	@OneToOne
	private Concepto referenciante;
	@OneToOne
	private Concepto referenciado;
	@ManyToOne
	private Modelo modelo;
	public Modelo getMmodelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	private MReferencia mReferencia;

	public Referencia(Concepto referenciante, Concepto referenciado,
			MReferencia mreferencia) throws ModeloException {
		super();
		this.referenciante = referenciante;
		this.referenciado = referenciado;
		this.mReferencia=mreferencia;
		String mensaje="";
		if(!referenciante.getmConcepto().equals(mreferencia.getReferenciante())){
			mensaje+="En esta referencia el referenciante debe ser una instancia de " + mreferencia.getReferenciante().getNombre() +" y no de " + referenciante.getmConcepto().getNombre()+"\n";
			}
		if(!referenciado.getmConcepto().equals(mreferencia.getReferenciado())){
			mensaje+="En esta referencia el referenciado debe ser una instancia de " + mreferencia.getReferenciado().getNombre() +" y no de " + referenciado.getmConcepto().getNombre()+"\n";
			}
		if(mensaje!=""){throw new ModeloException(mensaje);}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Concepto getReferenciante() {
		return referenciante;
	}

	public void setReferenciante(Concepto referenciante) {
		this.referenciante = referenciante;
	}

	public Concepto getReferenciado() {
		return referenciado;
	}

	public void setReferenciado(Concepto referenciado) {
		this.referenciado = referenciado;
	}

	public MReferencia getmReferencia() {
		return mReferencia;
	}

	public void setmReferencia(MReferencia mReferencia) {
		this.mReferencia = mReferencia;
	}


	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final Referencia other = (Referencia)obj;
	        if (!idTemporal.equals(other.idTemporal)) return false;
	        return true;
	    }
}
