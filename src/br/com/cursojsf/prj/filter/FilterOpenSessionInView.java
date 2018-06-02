package br.com.cursojsf.prj.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.cursojsf.frw.hibernate.session.HibernateUtil;
import br.com.cursojsf.frw.util.UtilFramework;
import br.com.cursojsf.prj.listner.ContextLoaderListnerUtil;
import br.com.cursojsf.prj.model.classes.Entidade;

@WebFilter(filterName="conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable{

	private static final long serialVersionUID = 1L;
	private static SessionFactory sf;
	private static ContextLoaderListnerUtil cl;

	//Executado quando a aplicação esta sendo iniciada.
	@Override
	protected void initFilterBean() throws ServletException {
    sf = HibernateUtil.getSessionFactory();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//JDBC Spring
		BasicDataSource springDS = (BasicDataSource) cl.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(springDS);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			request.setCharacterEncoding("UTF-8"); 
			// Captura usuario.
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession        ses = req.getSession();
			Entidade userLogadoSess= (Entidade) ses.getAttribute("userLogadoSessao");
			
			if (userLogadoSess != null) {
				UtilFramework.getThreadLocal().set(userLogadoSess.getEnt_codigo());
			}
			
			sf.getCurrentSession().beginTransaction();
			
			// antes de executar a ação (Request) 
			filterChain.doFilter(request, response); // executa nossa ação no servido
			// apos executar a ação (Response) 	
			
			transactionManager.commit(status);
			
		  //Hibernate
			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().flush();
				sf.getCurrentSession().getTransaction().commit();
			}
			
			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}
			
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			
		}catch(Exception e) {
		  //JDBC Spring
			transactionManager.rollback(status);
			e.printStackTrace();
			
		  //Hibernate
      if (sf.getCurrentSession().getTransaction().isActive()) {
      	sf.getCurrentSession().getTransaction().rollback();
        if (sf.getCurrentSession().isOpen()) {
        	sf.getCurrentSession().close();
        }
      }
      
		}finally{
		  //Hibernate
			if (sf.getCurrentSession().isOpen()) {
        if (sf.getCurrentSession().getTransaction().isActive()) {
        	sf.getCurrentSession().flush();
        	sf.getCurrentSession().clear();
        }
        if (sf.getCurrentSession().isOpen()) {
        	sf.getCurrentSession().close();
        }
			}
		}
		
	}
	
}
