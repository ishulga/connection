package com.sh.connection.validator;

import static com.sh.connection.util.Messages.INVALID_EMAIL;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.sh.connection.validator.email")
public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
	        Object value) throws ValidatorException {

		if (!org.apache.commons.validator.routines.EmailValidator.getInstance()
		        .isValid(((String) value).trim())) {

			FacesMessage message = new FacesMessage(INVALID_EMAIL);
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}

	}
}
