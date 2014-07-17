package controlador.validadores;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.routines.EmailValidator;

import com.sun.jersey.spi.StringReader.ValidateDefaultValue;


@FacesValidator("videoValidator")
public class VideoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		
		 String campo=((String)component.getAttributes().get("campo"));
		switch (campo) {
		case "TITULO":
			System.out.println("videoValidator.validate()");
			String titulo=(String)value;
			if(titulo==""){
			String message="No puede dejar el campo nombre vacío.";
			String title="Debe introducir un nombre.";
			FacesMessage msg = new FacesMessage(title, message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
			}
			break;
		case "DURACION":
			System.out.println("videoValidator.validate()");
			Float duracion=(Float)value;
			if(duracion==0.0){
			String message="Seleccione una url válida.";
			String title="Url no válida";
			FacesMessage msg = new FacesMessage(title, message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
			}
			break;
		case "URL":
			System.out.println("videoValidator.validate()");
			String url=(String)value;
			if(url==""){
			String message="No puede dejar el campo url vacío.";
			String title="Debe introducir una url.";
			FacesMessage msg = new FacesMessage(title, message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
			}
			break;

		default:
			break;
		}	
	

	}


}