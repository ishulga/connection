package com.sh.connection.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.sh.connection.converter.tag")
public class TagConverter implements Converter {


	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
	        String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
	        Object value) {
		return "-1";
	}
}
