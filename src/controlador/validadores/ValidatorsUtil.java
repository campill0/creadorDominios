package controlador.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

public class ValidatorsUtil {
	protected static void lanzarExcepcionValidacion(String message, String title) {
		FacesMessage msg = new FacesMessage(title, message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	}
}
