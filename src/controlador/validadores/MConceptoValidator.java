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
import modelo.MModelo;
import modelo.MPropiedad;

import org.apache.commons.validator.routines.EmailValidator;

import com.sun.jersey.spi.StringReader.ValidateDefaultValue;

import controlador.Catalogo;
import controlador.backingBeans.MConceptoBean;
import controlador.backingBeans.MModeloBean;
import controlador.backingBeans.ModeloBean;


@FacesValidator("mConceptoValidator")
public class MConceptoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
	
		System.out.println("MPropiedadValidator.validate()");
		String nombre=(String)value;
		 if(nombre.equals("")){
			 String message="Debe introducir un nombre para el concepto.";
				String title="Debe introducir un nombre.";
				
				FacesMessage msg = new FacesMessage(title, message);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		 }
		 String modo=(String)component.getAttributes().get("modo");
		 if(modo.equals("crear")){
			 MModelo mmodelo = (MModelo) component.getAttributes().get("mmodelo");
				for (MConcepto mc : mmodelo.getmConceptos()) {
					if((mc.getNombre().equals(nombre))){
						 String message="Ya existe otro concepto con el mismo nombre en este dominio.";
							String title="Nombre de concepto repetido.";
							
							FacesMessage msg = new FacesMessage(title, message);
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							
							throw new ValidatorException(msg);
					}
				}
			 
		 }
		 else{//editar
		
			 MConcepto mconcepto = (MConcepto) component.getAttributes().get("mconcepto");
				for (MConcepto mc : mconcepto.getMmodelo().getmConceptos()) {
					if((mc!=mconcepto)&&(mc.getNombre().equals(nombre))){
						 String message="Ya existe otro concepto con el mismo nombre en este dominio.";
							String title="Nombre de concepto repetido.";
							
							FacesMessage msg = new FacesMessage(title, message);
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							
							throw new ValidatorException(msg);
					}
				}
		 }
		 
		

		    
		
		}

	}


