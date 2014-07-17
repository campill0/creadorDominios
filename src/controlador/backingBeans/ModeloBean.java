package controlador.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ocpsoft.prettytime.PrettyTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SlideEndEvent;

import controlador.Catalogo;
import controlador.CatalogoException;
import controlador.FactoriaModelos;

import modelo.Concepto;
import modelo.FragmentoVideo;
import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Modelo;
import modelo.Posicion;
import modelo.Propiedad;
import modelo.Referencia;
import modelo.Video;
import modelo.dao.DAOException;
import modelo.dao.DAOFactory;
import modelo.dao.MModeloDAO;
import modelo.dao.DAOFactory.Type;
import modelo.dao.ModeloDAO;

@ManagedBean( name="modeloBean")
@ViewScoped
public class ModeloBean implements Serializable {

	private MModelo mmodelo;
    
    private List<Video> videosFiltrados;
    private List<Video> videos;
    private Video videoSeleccionado;
    
    private int fragmentoVideoInicio=30;
    private int fragmentoVideoFin=80;

    private Catalogo catalogo;
    private String nuevoModeloNombre;
    private String nuevoModeloDescripcion;
    private String nuevoModeloEtiquetaVideo;
    private FragmentoVideo nuevoModelofragmentoVideo;
    private String nuevoVideoTitulo;
    private String nuevoVideoUrl;
    private GeneradorIds generador;
    private List<Modelo> modelos;
    private FragmentoVideo fragmentoSeleccionado;
    
	public ModeloBean() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
			
		catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		generador=(GeneradorIds) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "generadorIdsBean");
		
			try {
				catalogo.refreshCatalogo();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			modelos=new ArrayList<Modelo>(); 
			modelos.addAll(catalogo.getModelos());
			
			videosFiltrados=new ArrayList<Video>();
			videos= new ArrayList<Video>();
			videos.addAll(catalogo.getVideos());
			if(catalogo.getmModelos().size()>0){
			mmodelo=catalogo.getmModelos().get(0);
			}
			
			videosFiltrados.addAll(videos);
	nuevoModeloNombre="";
	nuevoModeloDescripcion="";
	nuevoModeloEtiquetaVideo="";
	nuevoVideoUrl="";
	/*
	Map<String, String> params =facesContext. getExternalContext().getRequestParameterMap();
	String idModeloStr = params.get("modelo");
	if(idModeloStr!=null){
		long idModelo=Long.parseLong(idModeloStr);	
		try {
			setModeloSeleccionado(catalogo.getModeloFromId(idModelo));
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	}

	public List<MModelo> completeMModelos(String query) {  
        List<MModelo> results = new ArrayList<MModelo>();  
          List<MModelo> mmodelos = catalogo.getmModelos();
          for (MModelo mm : mmodelos) {
			if(mm.getNombre().toUpperCase().contains(query.toUpperCase())){
				results.add(mm);
			}
		}
          
        return results;  
    }

	public void createModelo() throws DAOException{
		//refreshCatalogo();// descartamos todos los cambios
		FactoriaModelos fm = FactoriaModelos.instancia();
		
		
		
		
		Modelo m=fm.createModelo(mmodelo,nuevoModeloNombre,nuevoModeloDescripcion,videoSeleccionado);
		m.setIdTemporal(generador.getNextIdTmp());
		saveModelo(m);
		//videoSeleccionado.getFragmentos().add(m.getFragmentoVideo());
		//catalogo.refreshCatalogo();
		setFragmentoSeleccionado(m.getFragmentoVideo());
		
		catalogo.getModelos().add(this.fragmentoSeleccionado.getModelo());
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('widgetCrearModelo').hide();");  
		//context.execute("loadVideo('"+ m.getUrl()+"');");  
		nuevoModeloNombre="";
		nuevoModeloDescripcion="";
	//	mmodelo=catalogo.getmModelos().get(0);
	}
	public void fake(){
		System.out.println();
	}
	public FragmentoVideo getFragmentoSeleccionado(){
		return this.fragmentoSeleccionado;
	}
	public int getFragmentoVideoFin() {
		return fragmentoVideoFin;
	}

	public int getFragmentoVideoInicio() {
		return fragmentoVideoInicio;
	}

	public MModelo getMmodelo() {
		return mmodelo;
	}

	public List<Modelo> getModelos(){
		return catalogo.getModelos();
	}
	public Modelo getModeloSeleccionado() {
		return fragmentoSeleccionado.getModelo();
	}  
	public List<Concepto> getModeloSeleccionadoConceptos(){
		if (fragmentoSeleccionado!=null){
		
			return fragmentoSeleccionado.getModelo().getConceptos();
		}
		else return null;
	}
	public List<MConcepto> getModeloSeleccionadoMConceptos(){
		if (fragmentoSeleccionado!=null){
		
			return fragmentoSeleccionado.getModelo().getMmodelo().getmConceptos();
		
		}
		else return null;
	}
	public List<Referencia> getModeloSeleccionadoReferencias(){
		if (fragmentoSeleccionado!=null){
		
			return fragmentoSeleccionado.getModelo().getReferencias();
		}
		else return null;
	}

	public String getNuevoModeloDescripcion() {
		return nuevoModeloDescripcion;
	}

	public String getNuevoModeloEtiquetaVideo() {
		return nuevoModeloEtiquetaVideo;
	}
	public String getNuevoModeloNombre() {
		return nuevoModeloNombre;
	}
	public String getNuevoVideoTitulo() {
		return nuevoVideoTitulo;
	}

	public String getNuevoVideoUrl() {
		return nuevoVideoUrl;
	}
	
	public List<Video> getVideos() {
		return videos;
	}
	public Video getVideoSeleccionado() {
		return videoSeleccionado;
	}
	
	public String getVideoSeleccionadoUrl(){
		if(videoSeleccionado==null){return "";}
		else{return videoSeleccionado.getUrl();}
	}
	public List<Video> getVideosFiltrados() {
		return videosFiltrados;
	}
	public boolean isEnBD(Modelo mm){
		if(fragmentoSeleccionado.getModelo()==null){return false;}
		if((fragmentoSeleccionado.getModelo().getId()!=null)){return true;}
		else{return false;}
		
	}

	public boolean isEnMemoria(Modelo mm){
		if(fragmentoSeleccionado.getModelo()==null){return false;}
		if((fragmentoSeleccionado.getModelo().getId()==null)){return true;}
		else{return false;}
		
	}

	public boolean isHayFragmentoSeleccionado(){
		return fragmentoSeleccionado!=null;
	}
	public boolean isHayModelos(){
		return catalogo.getModelos().size()>0;
	}

	public boolean isHayModeloSeleccionado(){
		return fragmentoSeleccionado.getModelo()!=null;
	}
	public boolean isHayVideoSeleccionado()
	{
		System.out.println("VideoBean.isHayVideoSeleccionado()" + (videoSeleccionado!=null));
		return videoSeleccionado!=null;
	}
	public boolean isRenderedNuevoModelo(){
		return (isHayVideoSeleccionado() && (catalogo.getmModelos().size()>0));
	}

	public void momentoSeleccionado(Object o){
		System.out.println("ModeloBean.momentoSeleccionado()");
	}
	public void onSlideEnd(SlideEndEvent event) {  
    	 
	        FacesMessage msg = new FacesMessage("Slide Ended", "Value: " + event.getValue());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }
	public void rcSetDuracionVideo(){
		System.out.println("rcSetDuracionVideo");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  
		  float duracion =  Float.parseFloat( (String) map.get("duracion"));
		  
		  if(fragmentoSeleccionado.getModelo()!=null){
			  fragmentoSeleccionado.getVideo().setDuracion(duracion);
		  }
		  
	
	}
	public void refresh(){
		FragmentoVideo fv = fragmentoSeleccionado.getModelo().getFragmentoVideo();
		fragmentoSeleccionado.setInicio(fv.getInicio());
		fragmentoSeleccionado.setFin(fv.getFin());
		
		setFragmentoSeleccionado(fragmentoSeleccionado);
	}
	public void refreshCatalogo(){

		System.out.println("refrescando catalogo.... ");
		try {
			catalogo.refreshCatalogo();
		//	refreshCatalogo2();
			if (isEnMemoria(fragmentoSeleccionado.getModelo())){
				
				setFragmentoSeleccionado(null);
			}
			else if(isEnBD(fragmentoSeleccionado.getModelo())){
				setFragmentoSeleccionado(catalogo.getModelo(fragmentoSeleccionado.getModelo().getIdTemporal()).getFragmentoVideo());
			}
		
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshModeloSeleccionado(){
		if(isHayModeloSeleccionado()){
			try {
				this.fragmentoSeleccionado.setModelo(catalogo.getModelo(fragmentoSeleccionado.getModelo().getId()));
			} catch (CatalogoException | DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public int removeModeloSeleccionado(){
		catalogo.getModelos().remove(fragmentoSeleccionado.getModelo());
		
	
		System.out.println("remove....");
		try { 
			DAOFactory factoria = DAOFactory.getDAOFactory(Type.JPA);
			ModeloDAO modeloDAO=factoria.getModeloDAO();
			
			if(isEnBD(fragmentoSeleccionado.getModelo())){
			modeloDAO.remove(fragmentoSeleccionado.getModelo());
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fragmentoSeleccionado.setModelo(null);
		videoSeleccionado.getFragmentos().remove(fragmentoSeleccionado);
		fragmentoSeleccionado=null;
		return 0;
	}
	
	public void resetNuevoModelo(){
		nuevoModeloDescripcion="";
		nuevoModeloNombre="";
	}

	
	public void saveModelo(Modelo m){
		System.out.println("saveModelo....");
	try { 
		DAOFactory factoria = DAOFactory.getDAOFactory(Type.JPA);
		ModeloDAO modeloDAO=factoria.getModeloDAO();
		if(m!=null){
			
		modeloDAO.save(m);
		}
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("ModeloBean.saveModelo()");
	
	}
	public void saveModeloSeleccionado(){
		Modelo modelo = fragmentoSeleccionado.getModelo();
		modelo.setFragmentoVideo(fragmentoSeleccionado);
		
		saveModelo(fragmentoSeleccionado.getModelo());
	}
	public String secondToHMS(float time){
		return Util.secondToHMS(time);
	}
	public void setFragmentoSeleccionado(FragmentoVideo fragmentoSeleccionado){
			
			this.fragmentoSeleccionado=fragmentoSeleccionado;
			
			
			
			
			System.out.println("setModeloSeleccionado");
			
			if(this.fragmentoSeleccionado.getModelo()!=null)
			{
				refreshModeloSeleccionado();
			List<Concepto> conceptos=this.fragmentoSeleccionado.getModelo().getConceptos();
			for (Concepto concepto : conceptos) {
				concepto.setIdTemporal(generador.getNextIdTmp());
				List<Propiedad> propiedades=concepto.getPropiedades();
				for (Propiedad propiedad : propiedades) {
					propiedad.setIdTemporal(generador.getNextIdTmp());
				}
			}
			List<Referencia> referencias=this.fragmentoSeleccionado.getModelo().getReferencias();
			for (Referencia referencia : referencias) {
				referencia.setIdTemporal(generador.getNextIdTmp());
			}
			List<MConcepto> mconceptos = this.fragmentoSeleccionado.getModelo().getMmodelo().getmConceptos();
			
			for(MConcepto mconcepto : mconceptos){
				mconcepto.setIdTemporal(generador.getNextIdTmp());
			}
			
			List<MReferencia> mreferencias = this.fragmentoSeleccionado.getModelo().getMmodelo().getmReferencias();
			
			for(MReferencia mreferencia : mreferencias){
				mreferencia.setIdTemporal(generador.getNextIdTmp());
			}
			
			}
		
	}
	public void setFragmentoVideoFin(int fragmentoVideoFin) {
		this.fragmentoVideoFin = fragmentoVideoFin;
	}

	public void setFragmentoVideoInicio(int fragmentoVideoInicio) {
		this.fragmentoVideoInicio = fragmentoVideoInicio;
	}
	public void setMmodelo(MModelo mmodelo) {
		this.mmodelo = mmodelo;
	}
	public void setNuevoModeloDescripcion(String nuevoModeloDescripcion) {
		this.nuevoModeloDescripcion = nuevoModeloDescripcion;
	}
	public void setNuevoModeloEtiquetaVideo(String nuevoModeloEtiquetaVideo) {
		this.nuevoModeloEtiquetaVideo = nuevoModeloEtiquetaVideo;
	}
	public void setNuevoModeloNombre(String nuevoModeloNombre) {
		this.nuevoModeloNombre = nuevoModeloNombre;
	}
	public void setNuevoVideoTitulo(String nuevoVideoTitulo) {
		this.nuevoVideoTitulo = nuevoVideoTitulo;
	}
	
	public void setNuevoVideoUrl(String nuevoVideoUrl) {
		this.nuevoVideoUrl = nuevoVideoUrl;
	}
	  
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	public void setVideoSeleccionado(Video videoSeleccionado) {
		this.videoSeleccionado = videoSeleccionado;
if(videoSeleccionado!= null){
		for (FragmentoVideo fragmento : videoSeleccionado.getFragmentos()) {
			fragmento.getModelo().setIdTemporal(generador.getNextIdTmp());
		}
}
	}
	public void setVideosFiltrados(List<Video> videosFiltrados) {
		this.videosFiltrados = videosFiltrados;
	}
	
	
	
	public void submitFormEditarMomento(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('widgetEditarModeloActual').hide();");  
		
	}

}
