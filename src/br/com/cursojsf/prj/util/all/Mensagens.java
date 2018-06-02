package br.com.cursojsf.prj.util.all;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class Mensagens extends FacesContext implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Mensagens() {
		// TODO Auto-generated constructor stub
	}
	
	public static void erroOperacao() {
		if(isValid()) {
			msgSeverityFatal(Constante.ERRO_NA_OPERACAO);
		}
	}
	public static void msg(String msg) {
		if(isValid()) {
			getFacesContext().addMessage("msg", new FacesMessage(msg));
		}
	}
	public static void sucesso() {
		if(isValid()) {
			msgSeverityFatal(Constante.SUCESSO);
		}
	}
	
	public static void responseOperation(StatusPersistencia sp) {
		if(sp != null && sp.equals(StatusPersistencia.SUCESSO)) {
			sucesso();
		}else if (sp != null && sp.equals(StatusPersistencia.OBJETO_REFERENCIADO)){
			msgSeverityFatal(StatusPersistencia.OBJETO_REFERENCIADO.toString());
		}else {
			erroOperacao();
		}
	}
	
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public static boolean isValid() {
		return getFacesContext() != null;
	}
	
	public static void msgSeverityWarn(String msg) {
		if (isValid()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
		}
	}

	public static void msgSeverityErr(String msg) {
		if (isValid()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public static void msgSeverityFatal(String msg) {
		if (isValid()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg));
		}
	}

	
	public static void msgSeverityInfo(String msg) {
		if (isValid()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
		}
	}
}
