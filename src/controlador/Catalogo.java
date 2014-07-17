package controlador;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;


import javax.faces.bean.ManagedBean;   
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;


import modelo.Concepto;
import modelo.FragmentoVideo;
import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Modelo;
import modelo.ModeloException;
import modelo.Propiedad;
import modelo.Referencia;
import modelo.Video;
import modelo.dao.DAOException;
import modelo.dao.DAOFactory;
import modelo.dao.VideoDAO;

import modelo.dao.MModeloDAO;


import modelo.dao.ModeloDAO;

import modelo.dao.DAOFactory.Type;
 


@SessionScoped
@ManagedBean(name = "catalogoBean")
public class Catalogo implements Serializable {

	private static final long serialVersionUID = -699840937109494079L;
	private static Catalogo instancia;
	private List<MModelo> mModelos;
	private List<Modelo> modelos;
	private List<Video> videos;
	private DAOFactory df;
	private MModeloDAO mmodeloDAO;
	private ModeloDAO modeloDAO;
	private VideoDAO videoDAO;

	public Catalogo() throws DAOException  {
		super();
		System.out.println("constructor catalogo");
		modelos=new ArrayList<Modelo>();
		mModelos =  new ArrayList<MModelo>();
		videos =  new ArrayList<Video>();
		df = DAOFactory.getDAOFactory(Type.JPA);
		mmodeloDAO=df.getMModeloDAO();
		modeloDAO=df.getModeloDAO();
		videoDAO=df.getVideoDAO();
		refreshCatalogo();
}
	public List<MModelo> getmModelos() {
		return mModelos;
	}
	public List<Modelo> getModelos() {
		return modelos;
	}
	
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	public void refreshCatalogo() throws DAOException{
		System.out.println("refrescando catalogo2...");
		
		List<MModelo> mmodelosDB=mmodeloDAO.findAll();
		List<Modelo> modelosDB=modeloDAO.findAll();
		List<Video> videosDB=videoDAO.findAll();
		
		for (MModelo mModeloDB : mmodelosDB) {
			for (MModelo mModeloMem : mModelos) {
				if(mModeloMem.getId().equals(mModeloDB.getId())){
					mModeloDB.setIdTemporal(mModeloMem.getIdTemporal());
					break;
				}
			}
		}
		
		for (Modelo modeloDB : modelosDB) {
			for (Modelo modeloMem : modelos) {
				if(modeloMem.getId().equals(modeloDB.getId())){
					modeloDB.setIdTemporal(modeloMem.getIdTemporal());
					break;
				}
			}
		}
		
		
		
		mModelos.clear();
		mModelos.addAll(mmodelosDB);
		modelos.clear();
		modelos.addAll(modelosDB);
		videos.clear();
		videos.addAll(videosDB);
		
	}
	public MModelo getMModelo(Long id) throws CatalogoException{
		
		for (MModelo mModelo : mModelos) {
			if(mModelo.getIdTemporal().equals(id)){
				return mModelo;
			}
		}
		throw new CatalogoException("MModelo con id " + id + " no encontrado.");
	}
public Modelo getModelo(Long id) throws CatalogoException, DAOException{
		
		return modeloDAO.find(id);
		
	}

public Video getVideo(Long id) throws CatalogoException{
	
	for (Video video : videos) {
		if(video.getId().equals(id)){
			return video;
		}
	}
	throw new CatalogoException("Video con id " + id + " no encontrado.");
}

public Modelo getModeloFromId(Long id) throws CatalogoException{
	
	for (Modelo modelo : modelos) {
		if(modelo.getId().equals(id)){
			return modelo;
		}
	}
	throw new CatalogoException("Modelo con id " + id + " no encontrado.");
}
	public MModelo refreshMModelo(Long id) throws DAOException{
		MModelo mm=mmodeloDAO.find(id);
		mModelos.remove(mm);
		mModelos.add(mm);
		return mm;
		
	}
	public MConcepto getMConcepto(MModelo mmodelo,String nombre) throws CatalogoException{
		List<MConcepto> mconceptos=mmodelo.getmConceptos();
		for (MConcepto mConcepto : mconceptos) {
			if(mConcepto.getNombre().equals(nombre)){return mConcepto;}
		}
		throw new CatalogoException("El concepto " + nombre + " no se encuentra en el modelo " + mmodelo.getNombre() );
	}
	public MConcepto getMConcepto(MModelo mmodelo,Long id) throws CatalogoException{
		List<MConcepto> mconceptos=mmodelo.getmConceptos();
		for (MConcepto mConcepto : mconceptos) {
			if(mConcepto.getIdTemporal().equals(id)){return mConcepto;}
		}
		throw new CatalogoException("El concepto con id " + id + " no se encuentra en el modelo " + mmodelo.getNombre() );
	}
	public List<FragmentoVideo> getFragmentos(Video video) throws CatalogoException{
		
		for (Video v : videos) {
			if(v.getId().equals(video.getId())){return v.getFragmentos();}
		}
		throw new CatalogoException("No se ha encontrado el vídeo en el catálogo." );
	}
	public Concepto getConcepto(Modelo modelo,Long id) throws CatalogoException{
		List<Concepto> conceptos=modelo.getConceptos();
		for (Concepto concepto : conceptos) {
			if(concepto.getIdTemporal().equals(id)){return concepto;}
		}
		throw new CatalogoException("El concepto con id " + id + " no se encuentra en el modelo " );
	}
	public MReferencia getMReferencia(MModelo mmodelo,Long id) throws CatalogoException{
		
		List<MReferencia> mreferencias=mmodelo.getmReferencias();
		for (MReferencia mReferencia : mreferencias) {
			if(mReferencia.getIdTemporal().equals(id)){return mReferencia;}
		}
		return null;
		
	}

public Referencia getReferencia(Modelo modelo,Long id) throws CatalogoException{
		
		List<Referencia> referencias=modelo.getReferencias();
		for (Referencia referencia : referencias) {
			if(referencia.getIdTemporal().equals(id)){return referencia;}
		}
		return null;
		
	}

	
	




	




	

}
