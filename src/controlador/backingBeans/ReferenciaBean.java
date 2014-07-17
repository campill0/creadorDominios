package controlador.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Concepto;
import modelo.MConcepto;
import modelo.MReferencia;
import modelo.ModeloException;
import modelo.Referencia;
import controlador.Catalogo;
import controlador.CatalogoException;
import controlador.FactoriaModelos;
@ManagedBean(name="referenciaBean")
@ViewScoped
public class ReferenciaBean  implements Serializable{
	private MReferenciaBean mReferenciaBean;
	private ModeloBean modeloBean;
	
	// 0 false, 1 true
	// Se una en app-modelo.js
	private int hayReferenciasDisponibles=0;
	private Concepto sourceSeleccionado;
	private GeneradorIds generador;
	private FactoriaModelos factoriaModelos;
	private Concepto targetSeleccionado;
	private List<MReferencia> mReferenciasPosibles;
	private MReferencia mReferenciaSeleccionada;
	private Referencia referenciaSeleccionada;
	public Referencia getReferenciaSeleccionada() {
		return referenciaSeleccionada;
	}
	public void setReferenciaSeleccionada(Referencia referenciaSeleccionada) {
		this.referenciaSeleccionada = referenciaSeleccionada;
	}
	public MReferencia getmReferenciaSeleccionada() {
		return mReferenciaSeleccionada;
	}
	public void setmReferenciaSeleccionada(MReferencia mReferenciaSeleccionada) {
		System.out.println("setMReferenciaSeleccionada");
		this.mReferenciaSeleccionada = mReferenciaSeleccionada;
	}
	private Catalogo catalogo;
	public ReferenciaBean(){
		super();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		//controladorPrincipal   = (ControladorPrincipal) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "controladorPrincipalBean");
		mReferenciaBean   = (MReferenciaBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "mReferenciaBean");
		modeloBean   = (ModeloBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "modeloBean");
		catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		mReferenciasPosibles= new ArrayList<MReferencia>();
		//mReferenciasPosibles.addAll( catalogo.getmModelos().get(1).getmReferencias());
		generador=(GeneradorIds) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "generadorIdsBean");
		factoriaModelos=FactoriaModelos.instancia();
		
		
	}

	public void rcSetReferenciaSeleccionada(){
		 FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		
		  Long id = Long.parseLong( (String) map.get("id"));
		  System.out.println("id a seleccionar:" + id);
		  
		  FacesContext facesContext = FacesContext.getCurrentInstance();
		  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
			try {
				referenciaSeleccionada= catalogo.getReferencia(modeloBean.getModeloSeleccionado(), id);
			} catch (CatalogoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}

	// referencias que ya se han realizado desde un concepto a otro
	public Set<MReferencia> mReferenciasYaUsadas(Concepto referenciante,Concepto referenciado,List<Referencia> referencias){
		Set<MReferencia> mReferencias=new HashSet<MReferencia>();
		for (Referencia ref : referencias) {
			if( (ref.getReferenciante().equals(referenciante)) &&
				(ref.getReferenciado().equals(referenciado))) 
					{
				mReferencias.add(ref.getmReferencia());
			}
		}
		return mReferencias;
	}
	private Set<MReferencia> mReferenciasDisponibles(
			MConcepto mconceptoSourceSeleccionado,
			MConcepto mconceptoTargetSeleccionado,
			List<MReferencia> mreferencias) {
		Set<MReferencia> mreferenciasPosibles= new HashSet<MReferencia>();
		for (MReferencia mReferencia : mreferencias) {
			if(
				(mReferencia.getReferenciante().equals(mconceptoSourceSeleccionado))&&
				(mReferencia.getReferenciado().equals(mconceptoTargetSeleccionado))
			  
			  ){
				mreferenciasPosibles.add(mReferencia);
				}
		}
		return mreferenciasPosibles;
	}
	public void setMReferenciasPosibles(){
		hayReferenciasDisponibles=0;// No hay referencias posibles
		if((sourceSeleccionado==null)||(targetSeleccionado==null)){
			mReferenciasPosibles.clear();
			return;
		}
		List<Referencia> referencias=sourceSeleccionado.getModelo().getReferencias();
		MConcepto mconceptoSourceSeleccionado =sourceSeleccionado.getmConcepto();
		MConcepto mconceptoTargetSeleccionado =targetSeleccionado.getmConcepto();
		List<MReferencia> mreferencias=mconceptoSourceSeleccionado.getMmodelo().getmReferencias();
		Set<MReferencia> mreferenciasPosibles = mReferenciasDisponibles(mconceptoSourceSeleccionado, mconceptoTargetSeleccionado,mreferencias);
		Set<MReferencia> mreferenciasYausadas=mReferenciasYaUsadas(sourceSeleccionado,targetSeleccionado,referencias);
		mreferenciasPosibles.removeAll(mreferenciasYausadas);
		
	
		
		mReferenciasPosibles.clear();
		mReferenciasPosibles.addAll(mreferenciasPosibles);
		if(mReferenciasPosibles.size()>0){
			hayReferenciasDisponibles=1;
			
		}
		else{
			if(mreferenciasYausadas.size()>0){
				Util.facesMessage(FacesMessage.SEVERITY_ERROR,"Todas las referencias posibles entre ", mconceptoSourceSeleccionado.getNombre()+ " y " + mconceptoTargetSeleccionado.getNombre() + " han sido ya utilizadas para estos dos conceptos concretos.");	
			}
			else{
			Util.facesMessage(FacesMessage.SEVERITY_ERROR,"No hay referencias posibles entre estos conceptos", mconceptoSourceSeleccionado.getNombre()+ " no tiene definida ninguna relación hacia " + mconceptoTargetSeleccionado.getNombre());
			}
			
		}
	}

	
	public void rcAlertaReferenciasNoDisponibles(){
		/*
		 FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		
		  Long source = Long.parseLong( (String) map.get("source"));
		  Long target = Long.parseLong((String) map.get("target"));
		  
		  FacesContext facesContext = FacesContext.getCurrentInstance();
		  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		  try {
			 Concepto referenciante = catalogo.getConcepto(	modeloBean.getModeloSeleccionado(), source) ;
			Concepto referenciado =  catalogo.getConcepto(modeloBean.getModeloSeleccionado(), target) ;
			Util.facesMessage("No hay referencias posibles entre estos conceptos", referenciante.getmConcepto().getNombre()+ " no tiene definida ninguna relación hacia" + referenciante.getmConcepto().getNombre());
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	
		
	}
	public int getHayReferenciasDisponibles() {
		return hayReferenciasDisponibles;
	}
	public void setHayReferenciasDisponibles(int hayReferenciasDisponibles) {
		this.hayReferenciasDisponibles = hayReferenciasDisponibles;
	}
	public List<MReferencia> getmReferenciasPosibles() {
	
		return mReferenciasPosibles;
	}
	public void fake(){
		System.out.println("ola");
	}
	public void setmReferenciasPosibles(List<MReferencia> mReferenciasPosibles) {
		this.mReferenciasPosibles = mReferenciasPosibles;
	}
	public void rcPreCreateReferencia()
	{ 
		 FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		
		  Long source = Long.parseLong( (String) map.get("source"));
		  Long target = Long.parseLong((String) map.get("target"));
		  
		  FacesContext facesContext = FacesContext.getCurrentInstance();
		  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
		  try {
			  this.sourceSeleccionado = catalogo.getConcepto(	modeloBean.getModeloSeleccionado(), source) ;
			this.targetSeleccionado =  catalogo.getConcepto(modeloBean.getModeloSeleccionado(), target) ;
			setMReferenciasPosibles();
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void rcCreateReferencia(){
		System.out.println("nuevaReferencia");
		FacesContext context = FacesContext.getCurrentInstance();
		  Map map = context.getExternalContext().getRequestParameterMap();
		  
		  Long source = Long.parseLong( (String) map.get("source"));
		 // String etiqueta = (String) map.get("etiqueta");
		  Long target = Long.parseLong((String) map.get("target"));
		  //System.out.println(source + " " + etiqueta + " "+  target);

		  try {
			factoriaModelos.createReferencia(modeloBean.getModeloSeleccionado(),catalogo.getConcepto(modeloBean.getModeloSeleccionado(), source), 		  catalogo.getConcepto(modeloBean.getModeloSeleccionado(), target),mReferenciaSeleccionada, generador.getNextIdTmp());
		} catch (CatalogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModeloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	
		
	}
	public void deseleccionarReferenciaSeleccionada(){
		referenciaSeleccionada=null;
	}
	
	public void removeReferenciaSeleccionada(){
		  FacesContext facesContext = FacesContext.getCurrentInstance();
			  Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
			try {
				modeloBean.getModeloSeleccionado().getReferencias().remove(catalogo.getReferencia(modeloBean.getModeloSeleccionado(), referenciaSeleccionada.getIdTemporal()));
				referenciaSeleccionada=null;
			} catch (CatalogoException e) {
		//TODO Gestionar mensajes de error con p:growl o p:message
				e.printStackTrace();
			}
		}

	
	
}
