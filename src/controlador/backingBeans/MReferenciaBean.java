package controlador.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;

import controlador.Catalogo;
import controlador.CatalogoException;
import controlador.FactoriaMetaModelos;






import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Posicion;
import modelo.TipoDeDato;


@ManagedBean (name= "mReferenciaBean")
@ViewScoped
public class MReferenciaBean implements Serializable{
	private static final long serialVersionUID = -6415459828856583145L;
	//private ControladorPrincipal controladorPrincipal;
	private Catalogo catalogo;
	private MModeloBean mmodeloBean;
	public MReferencia getmReferenciaSeleccionada() {
		return mReferenciaSeleccionada;
	}

	public void setmReferenciaSeleccionada(MReferencia mReferenciaSeleccionada) {
		this.mReferenciaSeleccionada = mReferenciaSeleccionada;
	}

	private MConcepto sourceSeleccionado;
	private MReferencia mReferenciaSeleccionada;
	private MConcepto targetSeleccionado;
	private FactoriaMetaModelos factoriaMModelos; 
	private GeneradorIds generador;
	private String nuevaReferenciaEtiqueta="";
	public MReferenciaBean() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		//controladorPrincipal   = (ControladorPrincipal) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "controladorPrincipalBean");
		catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		mmodeloBean=(MModeloBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "mModeloBean");
		generador=(GeneradorIds) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "generadorIdsBean");
		factoriaMModelos=FactoriaMetaModelos.instancia();
	}
	
	public String getNuevaReferenciaEtiqueta() {
		return nuevaReferenciaEtiqueta;
	}

	public void setNuevaReferenciaEtiqueta(String nuevaReferenciaEtiqueta) {
		this.nuevaReferenciaEtiqueta = nuevaReferenciaEtiqueta;
	}

	public void rcSetMReferenciaSeleccionada(){
		 FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		
		  Long id = Long.parseLong( (String) map.get("id"));
		  System.out.println("id a seleccionar:" + id);
		  
		  FacesContext facesContext = FacesContext.getCurrentInstance();
		  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
			try {
				mReferenciaSeleccionada= catalogo.getMReferencia(mmodeloBean.getmModeloSeleccionado(), id);
			} catch (CatalogoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
	//	  Long target = Long.parseLong((String) map.get("target"));
	}
	
	public void rcPreCreateMReferencia()
	{ 
		 FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		
		  Long source = Long.parseLong( (String) map.get("source"));
		  Long target = Long.parseLong((String) map.get("target"));
		  
		  FacesContext facesContext = FacesContext.getCurrentInstance();
		  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		  try {
			  this.sourceSeleccionado = catalogo.getMConcepto(	mmodeloBean.getmModeloSeleccionado(), source) ;
			this.targetSeleccionado =  catalogo.getMConcepto(mmodeloBean.getmModeloSeleccionado(), target) ;
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public MConcepto getSourceSeleccionado() {
		return sourceSeleccionado;
	}

	public void setSourceSeleccionado(MConcepto sourceSeleccionado) {
		this.sourceSeleccionado = sourceSeleccionado;
	}

	public MConcepto getTargetSeleccionado() {
		return targetSeleccionado;
	}

	public void setTargetSeleccionado(MConcepto targetSeleccionado) {
		this.targetSeleccionado = targetSeleccionado;
	}

	public void createMReferencia(){
		System.out.println("nuevaReferencia");
	
		 
			factoriaMModelos.createMReferencia(mmodeloBean.getmModeloSeleccionado(),sourceSeleccionado, 		 targetSeleccionado, nuevaReferenciaEtiqueta, generador.getNextIdTmp());
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("recuperarDraggable();");  
			context.execute("PF('widgetNuevaMReferencia').hide();"); 
			 //oncomplete="nuevaMRef('new'); recuperarDraggable();"
		//$('.draggable').draggable({revert:true}); PF('widgetNuevaMReferencia').hide();
	}
	
	public void removeMReferenciaSeleccionada(){
		
		if (!mmodeloBean.isMmodeloSeleccionadoTieneModelos() || mReferenciaSeleccionada.isEsNueva()) {
		  FacesContext facesContext = FacesContext.getCurrentInstance();
			  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
			try {
				mmodeloBean.getmModeloSeleccionado().getmReferencias().remove(catalogo.getMReferencia(mmodeloBean.getmModeloSeleccionado(), mReferenciaSeleccionada.getIdTemporal()));
				mReferenciaSeleccionada=null;
			} catch (CatalogoException e) {
		
				e.printStackTrace();
			}
		}else{
			Util.facesMessage(FacesMessage.SEVERITY_WARN, "No se puede modificar el dominio. No se puede eliminar la referencia.", "Este dominio tiene anotaciones creadas y no se permite borrar las referencias que se crearon antes de la última vez que se guardaron los cambios. Se podrán eliminar las referencias creadas despues del último guardado de cambios.");
		}
			
		}

	public void deseleccionarMReferenciaSeleccionada(){
		mReferenciaSeleccionada=null;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('widgetEditarMReferencia').hide();");  
		
	}
	
	public boolean getNuevaReferenciaEtiquetaDisable()
	{
		
		return (nuevaReferenciaEtiqueta.length()==0);
	}
}
