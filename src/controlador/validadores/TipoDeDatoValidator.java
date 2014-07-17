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

@FacesValidator("tipoDeDatoValidator")
public class TipoDeDatoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		System.out.println("tipoDeDatoValidator.validate()");
		String valor = (String) value;

		boolean entero = ((String) component.getAttributes().get("tipo"))
				.equals("entero");
		boolean real = ((String) component.getAttributes().get("tipo"))
				.equals("real");

		if (entero) {
			try {
				Long val = Long.parseLong(valor);
			} catch (NumberFormatException e) {
				validationErrorMessage(
						"El formato de número introducido no corresponde con un número entero",
						"Formato inválido, no es un entero.");
			}
		} else if (real) {
			try {
				Double val = Double.parseDouble(valor);
			} catch (NumberFormatException e) {
				validationErrorMessage(
						"El formato de número introducido no corresponde con un número real",
						"Formato inválido, no es un real.");
			}
		}
	}

	private void validationErrorMessage(String message, String title) {

		FacesMessage msg = new FacesMessage(title, message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	}

}
