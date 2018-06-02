package br.com.cursojsf.frw.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 * Responsavel por estabelecer uma conexão com o Hibernate
 * @author Daniel
 *
 */

@ApplicationScoped
public class HibernateUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static String JAVA_COMP_ENV_JDBC_DATA_SOURCE = "java:/comp/env/jdbc/datasource";
	private static SessionFactory sessionfactory = buildSessionFactory();
	
	/**
	 * Responsavel por ler o arquivo de configuração hibernate.cfg.xml
	 * @return SessionFactory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionfactory == null) {
				sessionfactory = new Configuration().configure().buildSessionFactory();
			}
			return sessionfactory;
		}catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao criar conexão SessionFactor");
		}
	}
	
	/**
	 * Retorna a SessionFactory corrente.
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionfactory;
	}

  /**
   * Retorna a Session corrente do SessionFactory
   * @return Session
   */
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 * Abre uma nova Session
	 * @return Session
	 */
	public static Session openSession() {
		if (sessionfactory == null) {
			buildSessionFactory();
		}
		return sessionfactory.openSession();
	}
	
	/**
	 * Retorna a Connection do provedor de conexões configurado 
	 * @return Connection SQL
	 * @throws SQLException
	 */
	public static Connection getConnectionProvider() throws SQLException {	
		return ((SessionFactoryImplementor) sessionfactory).getConnectionProvider().getConnection();	
	}
	
	/**
	 * Retorna a Conection JNDI do TOMCAT
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		return getDataSourceJNDI().getConnection();
	}
	
	/**
	 * Retorna o DataSource JNDI do TOMCAT
	 * @return
	 * @throws NamingException
	 */
	public static DataSource getDataSourceJNDI() throws NamingException {
		InitialContext context = new  InitialContext();
		DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		return ds;		
	}
}
