package modelo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContextType;
import javax.persistence.Transient;

@Entity
public class FragmentoVideo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private float inicio;
	private float fin;
	@OneToOne
	private Modelo modelo;
	@ManyToOne
	private Video video;
	
	public Long getIdTemporal() {
		return modelo.getIdTemporal();
	}
	public FragmentoVideo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FragmentoVideo(Modelo modelo,Video video){
		
		this.modelo=modelo;
		modelo.setFragmentoVideo(this);
		this.video=video;
		video.getFragmentos().add(this);
		inicio=0.0F;
		fin=video.getDuracion();
		
	}

	@Override
	public String toString()
	{
		return "FragmentoVideo:"+modelo.getNombre() + " inicio:" + inicio +" fin:" + fin;
	}

	public String getUrl(){
		return video.getUrl();
	}
	public Modelo getModelo() {
		return modelo;
	}
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getDuracion(){
		return (getFin()-getInicio());
	}


	public float getInicio() {
		return inicio;
	}

	public void setInicio(float inicio) {
		this.inicio = inicio;
	}

	public float getFin() {
		return fin;
	}

	public void setFin(float fin) {
		this.fin = fin;
	}

	
	
	public void incrementa1SegundoInicio(){
		if((getInicio()+1)<getFin()){
			 setInicio(getInicio()+1);
		}
		System.out.println("FragmentoVideo.incrementa1SegundoInicio()");
	}
	public void incrementa1SegundoFin(){
		 if((getFin()+1)<=getVideo().getDuracion()){
			 setFin(getFin()+1);
		}
		 System.out.println("FragmentoVideo.incrementa1SegundoFin()");
	}
public void decrementa1SegundoInicio(){
	 if(getInicio()<1){setInicio(0);}
	 else{setInicio(getInicio()-1); };
	 System.out.println("FragmentoVideo.decrementa1SegundoInicio()");
	}
	public void decrementa1SegundoFin(){
		 if((getFin()-1)>getInicio()){
			 setFin(getFin()-1);
		}
		 
		System.out.println("FragmentoVideo.decrementa1SegundoFin()");
	}
	
	
}
