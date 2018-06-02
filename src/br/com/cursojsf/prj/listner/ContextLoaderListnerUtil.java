package br.com.cursojsf.prj.listner;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.cursojsf.prj.util.all.Mensagens;

@ApplicationScoped
public class ContextLoaderListnerUtil extends ContextLoaderListener implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static WebApplicationContext getWebApp() {
		return WebApplicationContextUtils.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
	}
	
	public Object getBean(String idNomeBean) {
		Object obj = null;
		if (getWebApp().containsBean(idNomeBean)) {
			obj = getWebApp().getBean(idNomeBean);
		}else{
			Mensagens.msgSeverityFatal("Bean não definido no XML");
		}
		return obj;
	}

	public Object getBean(Class<?> classe) {
		Object obj = getWebApp().getBean(classe);
		return obj;
	}
	
	
}
