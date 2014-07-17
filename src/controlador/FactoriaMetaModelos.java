package controlador;

import java.io.Serializable;
import java.util.List;

import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Posicion;
import modelo.TipoDeDato;

public class FactoriaMetaModelos implements Serializable{
	
	private static final long serialVersionUID = 4856771639165089723L;
	private static FactoriaMetaModelos fmm;
	public static FactoriaMetaModelos instancia(){
		if(fmm==null){
			fmm=new FactoriaMetaModelos();
		}
		return fmm;
	}

	public MModelo createMModelo(String nombre){
		return new MModelo(nombre);

	}
	public FactoriaMetaModelos() {
		super();
		
	}

	public MModelo createMConcepto(MModelo mmodelo,String nombre, List<MPropiedad> propiedades, Posicion posicion){
		MConcepto mconcepto=new MConcepto(nombre);
		mconcepto.setMmodelo(mmodelo);
		mconcepto.setPosicion(posicion);
		mconcepto.getmPropiedades().addAll(propiedades);
		mmodelo.getmConceptos().add(mconcepto);
		return mmodelo;
	}
	
	public MModelo createMPropiedad(MModelo mmodelo,MConcepto mconcepto,String nombre,TipoDeDato tipo){
		mconcepto.getmPropiedades().add(new MPropiedad(nombre,tipo));
		mmodelo.getmConceptos().remove(mconcepto);
		mmodelo.getmConceptos().add(mconcepto);
		return mmodelo;
	}
	public MModelo createMReferencia(MModelo mmodelo,MConcepto sourceM,MConcepto targetM,String etiqueta){
		
		
		return createMReferencia( mmodelo, sourceM, targetM, etiqueta,0L);
		
	}
	public MModelo createMReferencia(MModelo mmodelo,MConcepto sourceM,MConcepto targetM,String etiqueta,Long idTemporal){
		MReferencia referencia=new MReferencia(sourceM,targetM,etiqueta);
		referencia.setEsNueva(true);
		referencia.setMmodelo(mmodelo);
		referencia.setIdTemporal(idTemporal);
		mmodelo.getmReferencias().add(referencia);
		
		
		
		return mmodelo;
		
	}

}
