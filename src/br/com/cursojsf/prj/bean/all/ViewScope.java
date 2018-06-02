package br.com.cursojsf.prj.bean.all;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

@SuppressWarnings("unchecked")
public class ViewScope implements Scope, Serializable {


	private static final long serialVersionUID = 1L;
	
	//Retorna o componente raiz que está associado a esta solicitação(request)
	//getViewMap - Retorna um Map que atua como a interface para o armazenamento de dados
	private Map<String, Object> getViewMap(){
		if (FacesContext.getCurrentInstance() != null) {
			return FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		}else {
			return new HashMap<String, Object>();
		}
	}
	
	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callBacks";

	@Override
	public Object get(String name, ObjectFactory<?> objFactory) {
		Object instancia = getViewMap().get(name);
		if (instancia == null) {
			instancia = objFactory.getObject();
			getViewMap().put(name, instancia);
		}
		return instancia;
	}

	@Override
	public String getConversationId() {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesRequestAttributes fra = new FacesRequestAttributes(fc);
		return fra.getSessionId() + "-" + fc.getViewRoot().getViewId();
	}

	@Override
	public void registerDestructionCallback(String nome, Runnable runnable) {
		Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		if (callBacks != null) {
			callBacks.put(VIEW_SCOPE_CALLBACKS,runnable);
		}		
	}

	@Override
	public Object remove(String name) {
		Object instancia = getViewMap().remove(name);
		if (instancia == null) {
			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if (callBacks != null) {
				callBacks.remove(name);
			}
		}
		return instancia;
	}

	@Override
	public Object resolveContextualObject(String name) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesRequestAttributes fra = new FacesRequestAttributes(fc);
		return fra.resolveReference(name);
	}

}
