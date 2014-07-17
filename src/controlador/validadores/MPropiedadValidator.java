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

import org.apache.commons.validator.routines.EmailValidator;

import com.sun.jersey.spi.StringReader.ValidateDefaultValue;

import controlador.Catalogo;
import controlador.backingBeans.MConceptoBean;
import controlador.backingBeans.MModeloBean;
import controlador.backingBeans.ModeloBean;


@FacesValidator("mPropiedadValidator")
public class MPropiedadValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
	
		System.out.println("MPropiedadValidator.validate()");
		String nombre=(String)value;
		 
		
		 if(nombre.equals("")){
			 String message="Debe introducir un nombre para la propiedad.";
				String title="Debe introducir un nombre.";
				
				FacesMessage msg = new FacesMessage(title, message);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		 }
		 
		 
		
		MPropiedad mpropiedad = (MPropiedad) component.getAttributes().get("mpropiedad");
		List<MPropiedad> mPropiedades = (List<MPropiedad>) component.getAttributes().get("mpropiedades");
	
		
		for (MPropiedad mp : mPropiedades) {
			if((mp.getIdTemporal()!=mpropiedad.getIdTemporal()) && (mp.getNombre().equals(nombre)) ){
				String message="No pueden haber varias propiedades con el mismo nombre.";
				String title="Nombre de propiedad repetido.";
				
				FacesMessage msg = new FacesMessage(title, message);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
		    
		
		}

	}


