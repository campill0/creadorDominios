package controlador;

import java.io.Serializable;
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

public class FactoriaModelos implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5395907412892638420L;
	private static FactoriaModelos fm;
	public static FactoriaModelos instancia(){
		if(fm==null){
			fm=new FactoriaModelos();
		}
		return fm;
	}


	public FactoriaModelos() {
		super();
		
	}

	public Modelo createModelo(MModelo mmodelo, String nombre,String descripcion, Video video ){
		return new Modelo(mmodelo,nombre,descripcion,video);

	}
	public Modelo createReferencia(Modelo modelo,Concepto source,Concepto target,MReferencia mreferencia,Long idTemporal) throws ModeloException{
		Referencia referencia=new Referencia(source,target,mreferencia);
		referencia.setIdTemporal(idTemporal);
		referencia.setModelo(modelo);
		modelo.getReferencias().add(referencia);
		return modelo;
	}

}
