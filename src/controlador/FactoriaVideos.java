package controlador;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;

import modelo.Concepto;
import modelo.FragmentoVideo;
import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Modelo;
import modelo.ModeloException;
import modelo.Posicion;
import modelo.Referencia;
import modelo.TipoDeDato;
import modelo.Video;

public class FactoriaVideos implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5395907412892638420L;
	private static FactoriaVideos fv;
	public static FactoriaVideos instancia(){
		if(fv==null){
			fv=new FactoriaVideos();
		}
		return fv;
	}


	public FactoriaVideos() {
		super();
		
	}
	public Video createVideo(String titulo, String url,Float duracion) throws MalformedURLException{
		return new Video(titulo,url,duracion);
	}
	
	public FragmentoVideo createFragmentoVideo(Modelo modelo,Video video){
		
		return new FragmentoVideo(modelo,video);
	}
	

	
}
