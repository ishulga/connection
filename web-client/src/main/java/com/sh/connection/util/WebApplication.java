package com.sh.connection.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

public class WebApplication {

	private final ServiceFactory serviceFactory;

	public WebApplication() {
		serviceFactory = ServiceFactory.INSTANCE;
	}

	public static <T> T getService(Class<T> serviceClass) {
		WebApplication webApplication = getBean("webApp");
		return webApplication.serviceFactory.getBean(serviceClass);
	}

	/**
	 * Returns JSF bean
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the JSF bean if found
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(context,
		        "#{" + beanName + "}", Object.class);
	}

	public static void addMessage(String message) {
		FacesMessage fm = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(fm.getSummary(), fm);
	}

	public static void error(String message) {
		FacesMessage fm = new FacesMessage(message);
		fm.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public static void error(String clientId, String message) {
		FacesMessage fm = new FacesMessage(message);
		fm.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(clientId, fm);
	}

	public static void info(String message) {
		FacesMessage fm = new FacesMessage(message);
		fm.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public static void info(String clientId, String message) {
		FacesMessage fm = new FacesMessage(message);
		fm.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(clientId, fm);
	}

	public static String getRequestParameterAsString(String name) {
		return FacesContext.getCurrentInstance().getExternalContext()
		        .getRequestParameterMap().get(name);
	}

	public static Long getRequestParameterAsLong(String name) {
		String param = getRequestParameterAsString(name);
		if (StringUtils.isEmpty(param)) {
			return null;
		}
		return Long.parseLong(param);
	}

	public static Long getComponentAttributeAsLong(UIComponent component,
	        String attrName) {
		String attrValue = component.getAttributes().get(attrName).toString();
		if (StringUtils.isEmpty(attrValue)) {
			return null;
		}
		return Long.parseLong(attrValue);
	}
}
