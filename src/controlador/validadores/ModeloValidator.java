package controlador.validadores;


import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import modelo.FragmentoVideo;
import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.Modelo;

import org.apache.commons.validator.routines.EmailValidator;

import com.sun.jersey.spi.StringReader.ValidateDefaultValue;

import controlador.Catalogo;
import controlador.backingBeans.MConceptoBean;
import controlador.backingBeans.MModeloBean;
import controlador.backingBeans.ModeloBean;


@FacesValidator("modeloValidator")
public class ModeloValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
	
		System.out.println("ModeloValidator.validate()");
		String nombre=(String)value;
		 if(nombre.equals("")){
			 String message="Debe introducir un nombre para el momento.";
				String title="Debe introducir un nombre.";
				
				FacesMessage msg = new FacesMessage(title, message);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		 }
		 FacesContext facesContext = FacesContext.getCurrentInstance();
			//	controladorPrincipal   = (ControladorPrincipal) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "controladorPrincipalBean");
				Catalogo catalogo   = (Catalogo) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "catalogoBean");
				ModeloBean modeloBean = (ModeloBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "modeloBean");
				
		 boolean editar=((String)component.getAttributes().get("modo")).equals("editar");
		 			
				for (FragmentoVideo fv : modeloBean.getVideoSeleccionado().getFragmentos()) {
					
					if(  (!editar || (editar && !modeloBean.getModeloSeleccionado().equals(fv.getModelo()))) && (fv.getModelo().getNombre().equals(nombre))){
						 String message="Ya existe otro momento con el mismo nombre para este vídeo.";
							String title="Nombre de momento repetido.";
							
							FacesMessage msg = new FacesMessage(title, message);
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							
							throw new ValidatorException(msg);
					}
					
				}
			 
		
		 
		 
		

		    
		
		}

	}


