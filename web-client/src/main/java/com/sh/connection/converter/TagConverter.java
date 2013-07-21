package com.sh.connection.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.sh.connection.converter.tag")
public class TagConverter implements Converter {

	// private TagService tagService =
	// WebApplication.getService(TagService.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
	        String value) {

		// String[] tagsStr = value.split(", ");
		// List<Tag> tags = new LinkedList<Tag>(tagsStr.length);
		// for (String tagStr : tagsStr) {
		// try {
		// tags.add(tagService.lookup(tagStr));
		// } catch (Exception e) {
		// throw new ConverterException(new FacesMessage(
		// FacesMessage.SEVERITY_ERROR, "required", "required"));
		// }
		// }
		//
		// return tags;
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
	        Object value) {

		// if (value != null) {
		// StringBuilder tags = new StringBuilder();
		// for (Tag tag : (List<Tag>) value) {
		// tags.append(tag.getTag()).append(", ");
		// }
		// tags.delete(tags.length() - 2, tags.length());
		//
		// return tags.toString();
		// }

		return "-1";
	}
}
