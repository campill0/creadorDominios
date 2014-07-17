package controlador.validadores;


import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import modelo.MConcepto;
import modelo.MPropiedad;
import modelo.MReferencia;

import org.apache.commons.validator.routines.EmailValidator;

import com.sun.jersey.spi.StringReader.ValidateDefaultValue;

import controlador.Catalogo;
import controlador.backingBeans.MConceptoBean;
import controlador.backingBeans.MModeloBean;
import controlador.backingBeans.MReferenciaBean;
import controlador.backingBeans.ModeloBean;


@FacesValidator("mReferenciaValidator")
public class MReferenciaValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
	
		System.out.println("MReferenciaValidator.validate()");
		String etiqueta=(String)value;
		 
		
		 if(etiqueta.equals("")){
			 String message="Debe introducir una etiqueta para la referencia.";
				String title="Debe introducir una etiqueta.";
				
				ValidatorsUtil.lanzarExcepcionValidacion(message, title);
		 }
		 FacesContext facesContext = FacesContext.getCurrentInstance();
			
			
		 MReferenciaBean mReferenciaBean   = (MReferenciaBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "mReferenciaBean");
			
		 
		 String modo = (String) component.getAttributes().get("modo");
		 if(modo.equals("crear")){
			 List<MReferencia> mreferencias = (List<MReferencia>) component.getAttributes().get("mreferencias");
			 MConcepto referenciante= (MConcepto) component.getAttributes().get("referenciante");
			 MConcepto referenciado= (MConcepto) component.getAttributes().get("referenciado");
			 for (MReferencia mr : mreferencias) {
					if( (mr.getEtiqueta().equals(etiqueta)) && mr.getReferenciante().equals(referenciante) && mr.getReferenciado().equals(referenciado)){
						lanzarExcepcionEtiquetaRepetida();
					}
				}
		 }else{//editar
			 MReferencia mreferencia = (MReferencia) component.getAttributes().get("mreferencia");
			 List<MReferencia> mreferencias = (List<MReferencia>) component.getAttributes().get("mreferencias");
			 for (MReferencia mr : mreferencias) {
					if((!mr.equals(mreferencia)) && (mr.getEtiqueta().equals(etiqueta)) && mr.getReferenciante().equals(mreferencia.getReferenciante()) && mr.getReferenciado().equals(mreferencia.getReferenciado())){
						lanzarExcepcionEtiquetaRepetida();
					}
				}
		 }
		 
		
		
		    
		
		}

	private void lanzarExcepcionEtiquetaRepetida() {
		String message="Ya existe una referencia con la misma etiqueta, con mismo concepto origen y mismo concepto destino.";
		String title="Etiqueta de referencia repetida.";
		
		ValidatorsUtil.lanzarExcepcionValidacion(message, title);
	}

	

	}


