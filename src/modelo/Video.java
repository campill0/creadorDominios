package modelo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import controlador.backingBeans.Util;
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
import javax.persistence.PersistenceContextType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Video implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titulo;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true, mappedBy="video")
	private List<FragmentoVideo> fragmentos;
	private String url;
	private String tipoUrl;
	private float duracion;
	@Transient
	private Long idTemporal=0L;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	public Long getIdTemporal() {
		return idTemporal;
	}
	public void setIdTemporal(Long idTemporal) {
		this.idTemporal = idTemporal;
	}
	public Video() {
		super();
		// TODO Auto-generated constructor stub
		this.fechaCreacion= new Date();
	}
	public Video(String titulo, String url,Float duracion) throws MalformedURLException{
		this.titulo=titulo;
		this.url=url;
		setUrl(url);
		this.fechaCreacion=new Date();
		
		this.duracion=duracion;
		fragmentos=new ArrayList<FragmentoVideo>();
	}

	@Override
	public String toString()
	{
		return "Video:"+titulo + " url:" + url;
	}


	public String getTipoUrl(String url) throws MalformedURLException{
		
		if(url.startsWith("file://")){return "local";}
		URL aURL = new URL(url);
		if((aURL.getHost().equals("www.youtube.com"))||(aURL.getHost().equals("youtube.com"))){return "youtube";}
	 	else if((aURL.getHost().equals("www.vimeo.com"))||(aURL.getHost().equals("vimeo.com"))){return "vimeo";}
	 	else{return "remote";}
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public List<FragmentoVideo> getFragmentos() {
		return fragmentos; 
	}
	public void setFragmentos(List<FragmentoVideo> fragmentos) {
		this.fragmentos = fragmentos;
	}
	
	public String getTipoUrl() {
		return tipoUrl;
	}
	public void setTipoUrl(String tipoUrl) {
		this.tipoUrl = tipoUrl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getUrl() {
		return url;
	}
	public String getUrlSinControles() throws MalformedURLException {
		if(tipoUrl.equals("youtube")){
			 URL aURL = new URL(url);
			 String videoCode="";
			 if(aURL.getPath().startsWith("/embed/")){
			 	 videoCode=aURL.getPath().split("/embed/")[1];
			 	
			 }
			 else if(aURL.getPath().startsWith("/watch")){
			 	 videoCode=aURL.getQuery().split("v=")[1];
			 }
			return "https://www.youtube.com/embed/"+videoCode+"?controls=0&showinfo=0&modestbranding=0";
			
		}
		else{
			return url;	
		}
		
	}
	public void setUrl(String url) throws MalformedURLException {
		this.url = url;
		this.tipoUrl=getTipoUrl(url);
	}
	public float getDuracion() {
		return duracion;
	}
	public void setDuracion(float duracion) {
		this.duracion = duracion;
	} 
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Video other = (Video)obj;
        if (!id.equals(other.id)) return false;
        return true;
    }
	
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
