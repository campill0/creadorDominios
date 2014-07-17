package controlador.backingBeans;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.eclipse.persistence.queries.UpdateAllQuery;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.SlideEndEvent;

import controlador.Catalogo;
import controlador.CatalogoException;
import controlador.FactoriaModelos;
import controlador.FactoriaVideos;

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
import modelo.dao.VideoDAO;

@ManagedBean( name="videoBean")
@ViewScoped
public class VideoBean implements Serializable {

	private boolean hayVideos=false;
    private String nuevoVideoTitulo;
    private Float nuevoVideoDuracion=0F;
    private String nuevoVideoUrl;
    private Video videoSeleccionado;
    private Catalogo catalogo;
    private List<Video> videosFiltrados;
    private List<Video> videos;
    
	public VideoBean() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		System.out.println("VideoBean.VideoBean()");
		videosFiltrados=new ArrayList<Video>();
		videos= new ArrayList<Video>();
		try {
			catalogo.refreshCatalogo();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		videos.addAll(catalogo.getVideos());
		
		videosFiltrados.addAll(videos);
		setHayVideos(videos.size()>0);	
	nuevoVideoTitulo="";
	nuevoVideoUrl="";

	
	}
	


	public Float getNuevoVideoDuracion() {
		return nuevoVideoDuracion;
	}



	public void setNuevoVideoDuracion(Float nuevoVideoDuracion) {
		this.nuevoVideoDuracion = nuevoVideoDuracion;
	}



	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<Video> getVideosFiltrados() {
		return videosFiltrados;
	}

	public void setVideosFiltrados(List<Video> videosFiltrados) {
		this.videosFiltrados = videosFiltrados;
	}
	public void establecerVideoSeleccionado(Long id){
		try {
			setVideoSeleccionado(catalogo.getVideo(id));
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNuevoVideoTitulo() {
		return nuevoVideoTitulo;
	}

	public void setNuevoVideoTitulo(String nuevoVideoTitulo) {
		this.nuevoVideoTitulo = nuevoVideoTitulo;
	}

	public Video getVideoSeleccionado() {
		return videoSeleccionado;
	}

	public void setVideoSeleccionado(Video videoSeleccionado) {
		this.videoSeleccionado = videoSeleccionado;
	}

	public String getNuevoVideoUrl() {
		return nuevoVideoUrl;
	}
	public boolean isHayVideoSeleccionado()
	{
		System.out.println("VideoBean.isHayVideoSeleccionado()" + (videoSeleccionado!=null));
		return videoSeleccionado!=null;
	}
	public boolean isHayVideos()
	{
		return hayVideos;
	}
	public void setHayVideos(boolean hayVideos){
		this.hayVideos=hayVideos;
	}
	public void setNuevoVideoUrl(String nuevoVideoUrl) {
		this.nuevoVideoUrl = nuevoVideoUrl;
	}
	
	
	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void removeVideoSeleccionado(){
		
		try {
			VideoDAO videoDAO;
			DAOFactory df;
			df = DAOFactory.getDAOFactory(Type.JPA);
			videoDAO=df.getVideoDAO();
			
			videoDAO.remove(videoSeleccionado);

			catalogo.refreshCatalogo();
			videos.clear();
			videos.addAll(catalogo.getVideos());
			videosFiltrados.clear();
			videosFiltrados.addAll(catalogo.getVideos());
			setHayVideos(videos.size()>0);
			
			RequestContext context = RequestContext.getCurrentInstance();
			//context.update("tablaVideos");
			//context.update("mensajeInicial");
			
			context.execute("PF('comfirmEliminarVideo').hide();");  
			context.execute("PF('widgetEditarVideo').hide()");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createVideo() throws DAOException{
		System.out.println("ModeloBean.createVideo() videoBean");
		
		
		FactoriaVideos fv = FactoriaVideos.instancia();
		try {
			videoSeleccionado=fv.createVideo(nuevoVideoTitulo,nuevoVideoUrl,nuevoVideoDuracion);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			Util.facesMessage(FacesMessage.SEVERITY_ERROR, "Url mal formada", "La url proporcionada no tiene un formato de url correcto.");
			e1.printStackTrace();
		}
		
		nuevoVideoTitulo="";
		nuevoVideoUrl="";
		nuevoVideoDuracion=0F;
		DAOFactory factoria;
		try {
			factoria = DAOFactory.getDAOFactory(Type.JPA);
			VideoDAO videoDAO=factoria.getVideoDAO();
			
			videoDAO.save(videoSeleccionado);
		} catch (DAOException e) {
			// TODO Manejar la excepción enviando un FacesMessage
			e.printStackTrace();
		}
		catalogo.refreshCatalogo();
		videos.clear();
		videos.addAll(catalogo.getVideos());
		videosFiltrados.clear();
		videosFiltrados.addAll(catalogo.getVideos());
		setHayVideos(videos.size()>0);
		//videos.add(videoSeleccionado);
		
		//videosFiltrados.add(videoSeleccionado);
		RequestContext context = RequestContext.getCurrentInstance();
		//context.update("tablaVideos");
		//context.update("mensajeInicial");
		context.execute("PF('widgetNuevoVideo').hide();");  
		context.execute("PF('widgetEditarVideo').hide();");
		
		
		
	}
	  
	  
	public String rowStyle(Video v){
		
		if(v.equals(videoSeleccionado)){
			System.out.println("fila-seleccionada");
			return "fila-seleccionada";
			
		}
		else{
			System.out.println("nada"+v);
			return "";
		}
	}
	public String getVideoSeleccionadoUrl(){
		if(videoSeleccionado==null){return "";}
		else{return videoSeleccionado.getUrl();}
	}
	public void saveVideo() throws DAOException{
		System.out.println("VideoBean.saveVideo()");
		DAOFactory factoria;
		try {
			factoria = DAOFactory.getDAOFactory(Type.JPA);
			VideoDAO videoDAO=factoria.getVideoDAO();
			
			videoDAO.save(videoSeleccionado);
		} catch (DAOException e) {
			// TODO Manejar la excepción enviando un FacesMessage
			e.printStackTrace();
		}
		catalogo.refreshCatalogo();
		RequestContext context = RequestContext.getCurrentInstance();
		//context.update("tablaVideos");
		//context.update("mensajeInicial");
		context.execute("PF('widgetEditarVideo').hide();");  
		
	}
	public void descartarCambiosVideo() throws DAOException{
		System.out.println("VideoBean.descartarCambiosVideo()");
		DAOFactory factoria;
		try {
			factoria = DAOFactory.getDAOFactory(Type.JPA);
			VideoDAO videoDAO=factoria.getVideoDAO();
			videoSeleccionado=videoDAO.find(videoSeleccionado.getId());
			
		} catch (DAOException e) {
			// TODO Manejar la excepción enviando un FacesMessage
			e.printStackTrace();
		}
		catalogo.refreshCatalogo();
	}
	public void rcSetDuracionVideo(){
		System.out.println("rcSetDuracionVideo");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  
		  float duracion =  Float.parseFloat( (String) map.get("duracion"));
		  
		  if(videoSeleccionado!=null){
			  videoSeleccionado.setDuracion(duracion);
		  }
		  
	
	}
	public String secondToHMS(float time){
		return Util.secondToHMS(time);
	}
}
