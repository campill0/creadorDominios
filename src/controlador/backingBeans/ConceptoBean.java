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

import modelo.Concepto;
import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Posicion;
import modelo.Propiedad;
import modelo.Referencia;
import modelo.TipoDeDato;

import controlador.Catalogo;
import controlador.CatalogoException;

@ManagedBean (name= "conceptoBean")
@ViewScoped
public class ConceptoBean implements Serializable {
	private ModeloBean modeloBean;
	private GeneradorIds generador;
	private List<String> propiedades;

	public List<String> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(List<String> propiedades) {
		this.propiedades = propiedades;
	}

	private Catalogo catalogo;
	private Concepto conceptoSeleccionado;
	public Concepto getConceptoSeleccionado() {
		return conceptoSeleccionado;
	}

	public void setConceptoSeleccionado(Concepto conceptoSeleccionado) {
		this.conceptoSeleccionado = conceptoSeleccionado;
	}

public boolean renderedCalendarWidget(Propiedad p){

return	(p.getmPropiedad().getTipo()==TipoDeDato.DATE);
}
public boolean renderedBooleanWidget(Propiedad p){

return	(p.getmPropiedad().getTipo()==TipoDeDato.BOOLEAN);
}
public boolean renderedTextWidget(Propiedad p){

return	(p.getmPropiedad().getTipo()==TipoDeDato.TEXT);
}
public boolean renderedLongTextWidget(Propiedad p){

return	(p.getmPropiedad().getTipo()==TipoDeDato.LONG_TEXT);
}
public boolean renderedLongWidget(Propiedad p){

return	(p.getmPropiedad().getTipo()==TipoDeDato.LONG);
}
public boolean renderedDoubleWidget(Propiedad p){

return	((p.getmPropiedad().getTipo()==TipoDeDato.DOUBLE));
}
public void submitFormEditarConcepto(){
	
	System.out.println("submitFormEditarConcepto");
	RequestContext context = RequestContext.getCurrentInstance();
	context.execute("PF('widgetEditarConcepto').hide();");  
	
}
	public ConceptoBean() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		//controladorPrincipalo   = (ControladorPrincipal) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "controladorPrincipalBean");
		catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		modeloBean=(ModeloBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "modeloBean");
		generador=(GeneradorIds) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "generadorIdsBean");
		propiedades= new ArrayList<String>();
		propiedades.add("pepe");
		propiedades.add("juan");
		propiedades.add("sara");
	}
	
	public void rcSetConceptoSeleccionado(){
		System.out.println("rcSetConceptoSeleccionado");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  String strid=(String) map.get("id");
		  Long id =  Long.parseLong( (String) map.get("id"));
	
		  try {
			  
			  setConceptoSeleccionado(	catalogo.getConcepto(modeloBean.getModeloSeleccionado(), id));
			
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	
	}
	
	public void createConcepto(MConcepto mconcepto,int x, int y){
		if(modeloBean.getModeloSeleccionado()!=null){
			
		Concepto concepto=new Concepto(mconcepto);
		concepto.setPosicion(new Posicion(x,y));
		concepto.setIdTemporal(generador.getNextIdTmp());
		concepto.setModelo(modeloBean.getModeloSeleccionado());
		concepto.setEtiqueta("etiqueta");
		for (MPropiedad mp : mconcepto.getmPropiedades()) {
			concepto.getPropiedades().add(new Propiedad(mp, null));
		}
		modeloBean.getModeloSeleccionado().getConceptos().add(concepto);
	
		}
		else{
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modelo no seleccionado","No ha seleccionado ningún modelo");  
			  
		        FacesContext.getCurrentInstance().addMessage(null, message); 
		}
		
		
	}
	public void rcRemoveConcepto(){
		System.out.println("borrando....");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  String strid=(String) map.get("id");
		  Long id =  Long.parseLong( (String) map.get("id"));
		  
		  try {
			  removeConceptoDeModeloSeleccionado(catalogo.getConcepto(	modeloBean.getModeloSeleccionado(), id));
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeConceptoDeModeloSeleccionado(Concepto c){
		List<Referencia> referencias= modeloBean.getModeloSeleccionado().getReferencias();
		List<Referencia> referenciasABorrar=new ArrayList<Referencia>();
		for (Referencia referencia : referencias) {
			if((referencia.getReferenciado().equals(c)) || (referencia.getReferenciante().equals(c)) ){
				referenciasABorrar.add(referencia);
			}
			
		}
		
		modeloBean.getModeloSeleccionado().getReferencias().removeAll(referenciasABorrar);
		modeloBean.getModeloSeleccionado().getConceptos().remove(c);

		}
	public void rcCreateConcepto(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  Long id = Long.parseLong( (String) map.get("id"));
		  int x = Integer.parseInt( (String) map.get("x"));
		  int y = Integer.parseInt( (String) map.get("y"));
		  MModelo mmodelo=modeloBean.getModeloSeleccionado().getMmodelo();
		  
		  try {
			MConcepto mconcepto= catalogo.getMConcepto(mmodelo, id);
			 createConcepto(mconcepto,x,y);
		  } catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		System.out.println("rcCreateConcepto: "+id);
		  
	
		
	}
	 
	public void rcActualizarPosicionConcepto(){
		System.out.println("rcActualizarPosicionMConcepto");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  String strid=(String) map.get("id");
		  Long id =  Long.parseLong( (String) map.get("id"));
		  float x =  (int) Float.parseFloat( (String) map.get("x"));
		  float y =  (int) Float.parseFloat( (String) map.get("y"));
		  try {
			
			catalogo.getConcepto( modeloBean.getModeloSeleccionado(), id).setPosicion(new Posicion(x,y));
			
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	
	}
}
