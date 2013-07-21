package com.sh.connection.validator;

import static com.sh.connection.util.Messages.MISMATCHED_PASSWORDS;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.sh.connection.validator.confirmPassword")
public class ConfirmPasswordValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
	        Object value) throws ValidatorException {

		UIInput passwordInput = (UIInput) component.getAttributes().get(
		        "passwordInput");
		String password = (String) passwordInput.getValue();
		String confirmPassword = (String) value;

		if (confirmPassword != null && !confirmPassword.equals(password)) {
			FacesMessage message = new FacesMessage(MISMATCHED_PASSWORDS);
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

}
