package br.com.cursojsf.prj.exception;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.cursojsf.frw.hibernate.session.HibernateUtil;
import br.com.cursojsf.prj.util.all.Mensagens;


public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;
	
	final FacesContext fc = FacesContext.getCurrentInstance();
	final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
	final NavigationHandler nh = fc.getApplication().getNavigationHandler();
	
	
	public CustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	
	// Sobrescreve o metodo ExceptionHandler que retorna a pilha de exce�oes
	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	// Sobrescreve o metodo handler que � responsavel por manipular as exce��es do JSF
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		
		while(i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			// Recupera a exce��o do contexto
			Throwable ex = context.getException();
			
			// Aqui trabalhamos a exce��o
			try {
				
				requestMap.put("execptionMessage", ex.getMessage());
				
				if(ex != null && ex.getMessage() != null && ex.getMessage().indexOf("ConstraintViolationException") != -1) {			
					Mensagens.msgSeverityErr("Registro n�o pode ser Removido - Viola��o de chave estrangeira");
				} else if(ex != null && ex.getMessage() != null && ex.getMessage().indexOf("StaleObjectStateException") != -1) {			
					Mensagens.msgSeverityWarn("Registro esta sendo manipulado por outro usu�rio - Consulte novamente");
				} else {
					Mensagens.msgSeverityFatal("O Sistema se recuperou de um erro inesperado !!!");
					Mensagens.msgSeverityInfo ("Voc� pode continuar a usar o sistema normalmente !!!");
					Mensagens.msgSeverityFatal("O erro foi causado por :\n"+ ex.getMessage());
					
					// PRIMEFACES
					// Essas msg sa� exibidas apenas se a pagina n�o for redirecionada 
					RequestContext.getCurrentInstance().execute("alert('O Sistema se recuperou de um erro inesperado !!!')");
					// Objeto Dialog do primefaces
					RequestContext.getCurrentInstance().showMessageInDialog( 
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Erro","O Sistema se recuperou de um erro inesperado !!!")
					);
					// Redireciona a pagina para um pagina de ERRO Padr�o.
					nh.handleNavigation(fc, null, "/error/error.jsf/faces-redirect=true&expired=true");
				}
				
			}finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
				
				//Imprime log no console.
				ex.printStackTrace();
				i.remove();
			}
		}
		getWrapped().handle();
		
	}
	
}
