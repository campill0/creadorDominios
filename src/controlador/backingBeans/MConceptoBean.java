package controlador.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;


import controlador.Catalogo;
import controlador.CatalogoException;






import modelo.MConcepto;

import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Posicion;
import modelo.TipoDeDato;
import modelo.dao.DAOException;


@ManagedBean (name= "mConceptoBean")
@ViewScoped
public class MConceptoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914919854377124254L;
	public MConcepto getMconceptoSeleccionado() {
		return mconceptoSeleccionado;
	}

	//private ControladorPrincipal controladorPrincipalo;
	private String nombreMConcepto;
	private List<MPropiedad> mpropiedades;
	private MModeloBean mmodeloBean;
	private Catalogo catalogo;
	GeneradorIds generador;
	private MConcepto mconceptoSeleccionado;


public String getNombreMConcepto() {
		return nombreMConcepto;
	}


	public void setNombreMConcepto(String nombreMConcepto) {
		this.nombreMConcepto = nombreMConcepto;
	}


	public List<MPropiedad> getMpropiedades() {
		return mpropiedades;
	}


	public void setMpropiedades(List<MPropiedad> mpropiedades) {
		this.mpropiedades = mpropiedades;
	}


public void addPropiedadesNuevoMConcepto() {
	 
		
	 MPropiedad mp= new MPropiedad(getNextNombreMPropiedad(1,getMpropiedades()), TipoDeDato.TEXT);
		mpropiedades.add(mp);
	    }
public String getNextNombreMPropiedad(int sufijo, List<MPropiedad> mpropiedades){
	String nombreNuevaPropiedad="propiedad_";
	
	for (MPropiedad mp : mpropiedades) {
		if (mp.getNombre().equals(nombreNuevaPropiedad+String.format("%03d", sufijo))){
			return getNextNombreMPropiedad(sufijo+1,mpropiedades);
		}
	}
	return nombreNuevaPropiedad+String.format("%03d", sufijo);
}
public void addPropiedadMConceptoSeleccionado() {
	
	 MPropiedad mp= new MPropiedad(getNextNombreMPropiedad(1,mconceptoSeleccionado.getmPropiedades()), TipoDeDato.TEXT);
		mconceptoSeleccionado.getmPropiedades().add(mp);
	  
	
   }
public boolean renderedEditarPropiedad(MPropiedad mp){
	
	if(mp==null){return false;}
	else{
		if(mp.isEsNueva()){
			System.out.println("hey! " + mp.getNombre());
		}
	return !mmodeloBean.isMmodeloSeleccionadoTieneModelos()||mp.isEsNueva();
	}
}
	

	public MConceptoBean() {
		super();
		// TODO Auto-generated constructor stub
		
			FacesContext facesContext = FacesContext.getCurrentInstance();
			//controladorPrincipalo   = (ControladorPrincipal) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "controladorPrincipalBean");
			catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
			mmodeloBean=(MModeloBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "mModeloBean");
			generador=(GeneradorIds) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "generadorIdsBean");
			reset();
			
			  
		
	}

	public TipoDeDato[] getTipos(){
	
		return TipoDeDato.values();
	}
	public void reset(){
		
		nombreMConcepto="";
		mpropiedades= new ArrayList<MPropiedad>();
	}
	
	public void removeMPropiedadDeMConceptoSeleccionado(MPropiedad mp){
		mconceptoSeleccionado.getmPropiedades().remove(mp);
	}
	public void removeMPropiedadDeNuevoMConcepto(MPropiedad mp){
		mpropiedades.remove(mp);
	}
	
	
	
	public void rcActualizarPosicionMConcepto(){
		System.out.println("rcActualizarPosicionMConcepto");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  String strid=(String) map.get("id");
		  Long id =  Long.parseLong( (String) map.get("id"));
		  
		  float x =  (int) Float.parseFloat( (String) map.get("x"));
		  float y =  (int) Float.parseFloat( (String) map.get("y"));
		  try {
			
			catalogo.getMConcepto( mmodeloBean.getmModeloSeleccionado(), id).setPosicion(new Posicion(x,y));
			
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public void rcSetMConceptoSeleccionado(){
		System.out.println("rcSetMConceptoSeleccionado");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  String strid=(String) map.get("id");
		  Long id =  Long.parseLong( (String) map.get("id"));
	
		  try {
			  
			  setMconceptoSeleccionado(	catalogo.getMConcepto(mmodeloBean.getmModeloSeleccionado(), id));
			
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	
	}
	public void setMconceptoSeleccionado(MConcepto mconceptoSeleccionado) {
		this.mconceptoSeleccionado = mconceptoSeleccionado;
	}
	public void rcCreateMConcepto(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('widgetCrearMetaConcepto').show();");  
		
	} 
	public void createMConcepto(){
		if(mmodeloBean.isMetaModeloSeleccionado()){
		MConcepto mconcepto=new MConcepto(nombreMConcepto);
		mconcepto.setPosicion(new Posicion(0,0));
		mconcepto.setIdTemporal(generador.getNextIdTmp());
		mconcepto.setMmodelo(mmodeloBean.getmModeloSeleccionado());
		for (MPropiedad mPropiedad : mpropiedades) {
			mPropiedad.setIdTemporal(generador.getNextIdTmp());
		}
		mconcepto.getmPropiedades().addAll(mpropiedades);
		
		mmodeloBean.getmModeloSeleccionado().getmConceptos().add(mconcepto);
	
		}
		else{
			Util.facesMessage(FacesMessage.SEVERITY_INFO, "Modelo no seleccionado","No ha seleccionado ningún modelo");  
			  
		      
		}
		reset();
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('widgetCrearMetaConcepto').hide();");  
		
	}
	public void removeMConceptoDeMMS(MConcepto mc){
		List<MReferencia> mreferencias= mmodeloBean.getmModeloSeleccionado().getmReferencias();
		List<MReferencia> mreferenciasABorrar=new ArrayList<MReferencia>();
		for (MReferencia mReferencia : mreferencias) {
			if((mReferencia.getReferenciado().equals(mc)) || (mReferencia.getReferenciante().equals(mc)) ){
				mreferenciasABorrar.add(mReferencia);
			}
			
		}
		
		mmodeloBean.getmModeloSeleccionado().getmReferencias().removeAll(mreferenciasABorrar);
		mmodeloBean.getmModeloSeleccionado().getmConceptos().remove(mc);
		catalogo.getmModelos().remove(mmodeloBean.getmModeloSeleccionado());
		catalogo.getmModelos().add(mmodeloBean.getmModeloSeleccionado());

		}

	public void rcRemoveMConcepto() {
		System.out.println("borrando....");
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String strid = (String) map.get("id");
		Long id = Long.parseLong((String) map.get("id"));
		MConcepto mc;
		
		try {
			mc=catalogo.getMConcepto(mmodeloBean.getmModeloSeleccionado(), id);
			if (!mmodeloBean.isMmodeloSeleccionadoTieneModelos() || (mc.getId()==null)) {
				removeMConceptoDeMMS(mc);
			}
			else{
				Util.facesMessage(FacesMessage.SEVERITY_WARN, "No se puede modificar el dominio. No se puede eliminar el concepto.", "Este dominio tiene anotaciones creadas y para modificar el dominio deberá eliminar esas anotaciones. No obstante hay ciertos aspectos que si podrá cambiar, como el nombre del dominio, los nombres de los conceptos, los nombres de las propiedades, las etiquetas de los conceptos y las etiquetas de las referencias. Además se podrán añadir nuevos conceptos y propiedades a los conceptos.");
			}
			
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}
	
	public void removeMPropiedad(MConcepto mc,MPropiedad mp){
		mc.getmPropiedades().remove(mp);
	}

}
