package controlador.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;


import controlador.Catalogo;
import controlador.CatalogoException;






import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Posicion;
import modelo.TipoDeDato;
import modelo.Video;
import modelo.dao.DAOException;
import modelo.dao.DAOFactory;
import modelo.dao.MModeloDAO;
import modelo.dao.DAOFactory.Type;


@ManagedBean (name= "mModeloBean")
@ViewScoped
public class MModeloBean implements Serializable{
	private static final long serialVersionUID = -6415459828856583145L;
	//private ControladorPrincipal controladorPrincipal;
	private Catalogo catalogo;
	private List<MModelo> metamodelosFiltrados;
    
    private boolean mmodeloSeleccionadoTieneModelos=true;
	private MModelo mModeloSeleccionado;

	private MReferencia mReferenciaSeleccionada;
	public 	MReferencia getmReferenciaSeleccionada() {
		return mReferenciaSeleccionada;
	}
	
	public void setmReferenciaSeleccionada(MReferencia mReferenciaSeleccionada) {
		this.mReferenciaSeleccionada = mReferenciaSeleccionada;
	}
	private String nuevoMetaModelo;
	public String getNuevoMetaModelo() {
		return nuevoMetaModelo;
	}
 
	public boolean isMmodeloSeleccionadoTieneModelos() {
		return mmodeloSeleccionadoTieneModelos;
	}

	public void setMmodeloSeleccionadoTieneModelos(
			boolean mmodeloSeleccionadoTieneModelos) {
		this.mmodeloSeleccionadoTieneModelos = mmodeloSeleccionadoTieneModelos;
	}

	public List<MModelo> getMetamodelosFiltrados() {
		return metamodelosFiltrados;
	}
	
	public boolean metamodeloTieneModelos() throws DAOException{
		
		System.out.println("MModeloBean.isMetamodeloTieneModelos()");
		if(mModeloSeleccionado==null){return false;}
		DAOFactory factoria = DAOFactory.getDAOFactory(Type.JPA);
		MModeloDAO mmodeloDAO=factoria.getMModeloDAO();
		
		return (mmodeloDAO.getModelos(mModeloSeleccionado.getId()).size()>0);
	}
	
	public void setMetamodelosFiltrados(List<MModelo> metamodelosFiltrados) {
		this.metamodelosFiltrados = metamodelosFiltrados;
	}
	public String getDisableClass(){
		if(isMmodeloSeleccionadoTieneModelos()){
			return "disableInput";
		}
		else{return "";}
	}
	public String conceptoDisableClass(MConcepto mc){
		if(isMmodeloSeleccionadoTieneModelos() && (mc.getId()!=null)){
			return "disableInput";
		}
		else{return "";}
	}
	public void setNuevoMetaModelo(String nuevoMetaModelo) {
		this.nuevoMetaModelo = nuevoMetaModelo;
	}
	GeneradorIds generador;
	public MModeloBean() {
		super();

		FacesContext facesContext = FacesContext.getCurrentInstance();
	//	controladorPrincipal   = (ControladorPrincipal) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "controladorPrincipalBean");
		catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		generador=(GeneradorIds) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "generadorIdsBean");
		for (MModelo mm : catalogo.getmModelos()) {
			mm.setIdTemporal(generador.getNextIdTmp());
		}
		metamodelosFiltrados=new ArrayList<MModelo>();
	
		
		metamodelosFiltrados.addAll(catalogo.getmModelos());
	}
	
	public MModelo getmModeloSeleccionado() {
		return mModeloSeleccionado;
	}

	public void createMModelo(){
		// descartamos todos los cambios
		mModeloSeleccionado=new MModelo(nuevoMetaModelo);
		nuevoMetaModelo="";
		mModeloSeleccionado.setIdTemporal(generador.getNextIdTmp());
		catalogo.getmModelos().add(mModeloSeleccionado);
		
		saveMModelo();
		refreshCatalogo();
		metamodelosFiltrados.clear();
		metamodelosFiltrados.addAll(catalogo.getmModelos());
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('widgetNuevoMetaModelo').hide();");  
		context.update("formNuevoMetaModelo");
	}
	public List<MConcepto> getMMSMConceptos(){
		//	System.out.println("getMMSMConceptos");
			if(mModeloSeleccionado==null){return new ArrayList<MConcepto>();}
			return mModeloSeleccionado.getmConceptos();
		}

		public List<MReferencia> getMMSMReferencias(){
			System.out.println("getMMSMReferencias");
			if(mModeloSeleccionado==null){return new ArrayList<MReferencia>();}
			return mModeloSeleccionado.getmReferencias();
		}
		
		public void setmModeloSeleccionado(MModelo mModeloSeleccionado) {
			System.out.println("setmModeloSeleccionado");
			
		
			
			this.mModeloSeleccionado = mModeloSeleccionado;
			
			try {
				setMmodeloSeleccionadoTieneModelos(metamodeloTieneModelos());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(this.mModeloSeleccionado!=null)
			{
			List<MConcepto> mconceptos=mModeloSeleccionado.getmConceptos();
			for (MConcepto mConcepto : mconceptos) {
				mConcepto.setIdTemporal(generador.getNextIdTmp());
				List<MPropiedad> mpropiedades=mConcepto.getmPropiedades();
				for (MPropiedad mPropiedad : mpropiedades) {
					mPropiedad.setIdTemporal(generador.getNextIdTmp());
				}
			}
			List<MReferencia> mreferencias=mModeloSeleccionado.getmReferencias();
			for (MReferencia mReferencia : mreferencias) {
				mReferencia.setIdTemporal(generador.getNextIdTmp());
			}
			}
		}
	public void saveMModelo(){ 
		System.out.println("saveModelo....");
	try { 
		DAOFactory factoria = DAOFactory.getDAOFactory(Type.JPA);
		MModeloDAO mmodeloDAO=factoria.getMModeloDAO();
		if(mModeloSeleccionado!=null){
		mmodeloDAO.save(mModeloSeleccionado);
		refreshCatalogo();
		}
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public void removeMModeloSeleccionado(){
		catalogo.getmModelos().remove(mModeloSeleccionado);
		metamodelosFiltrados.clear();
		metamodelosFiltrados.addAll(catalogo.getmModelos());
		System.out.println("remove....");
		try { 
			DAOFactory factoria = DAOFactory.getDAOFactory(Type.JPA);
			MModeloDAO mmodeloDAO=factoria.getMModeloDAO();
			
			if(isEnBD(mModeloSeleccionado)){
			mmodeloDAO.remove(mModeloSeleccionado);
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mModeloSeleccionado=null;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('comfirmEliminarMetamodelo').hide();");  
	}
	
	public void fake(){
	System.out.println("MModeloBean.fake()");	
	}
	
	public boolean isEnMemoria(MModelo mm){
		if(mm==null){return false;}
		if((mm.getId()==null)){return true;}
		else{return false;}
		
	}
	public boolean isEnBD(MModelo mm){
		if(mm==null){return false;}
		if((mm.getId()!=null)){return true;}
		else{return false;}
		
	}
	
	public void refreshCatalogo(){

		System.out.println("refrescando catalogo.... ");
		try {
			catalogo.refreshCatalogo();
		//	refreshCatalogo2();
			if (isEnMemoria(mModeloSeleccionado)){
				
				setmModeloSeleccionado(null);
			}
			else if(isEnBD(mModeloSeleccionado)){
				setmModeloSeleccionado(catalogo.getMModelo(mModeloSeleccionado.getIdTemporal()));
			}
		
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	
	public boolean isMetaModeloSeleccionado(){
		return mModeloSeleccionado!=null;
	}
	public boolean isHayMModelos(){
		return catalogo.getmModelos().size()>0;
	}
	
	public void refreshSeleccionado() {
		try {
			mModeloSeleccionado=catalogo.refreshMModelo(mModeloSeleccionado.getId());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resetNuevoMetaModelo(){
		nuevoMetaModelo="";
	}

}
