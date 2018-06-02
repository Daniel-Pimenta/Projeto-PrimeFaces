package br.com.cursojsf.frw.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<E> extends Serializable{
	
	public void    save(E entidade) throws Exception;
	public void    persist(E entidade) throws Exception;
	public void    saveOrUpdate(E entidade) throws Exception;
	public void    update(E entidade) throws Exception;
	public void    delete(E entidade) throws Exception;
	//
	public E       merge(E entidade) throws Exception;
	public E       findById(Class<E> obj, Long id) throws Exception;
	//
	public List<E> findList(Class<E> obj) throws Exception;
	public List<E> findListByQuery(String hql) throws Exception;
	public List<E> findListByQuery(String hql, int ini, int fim) throws Exception;
	public List<?> getListByQuery(String sql) throws Exception;
	public List<Object[]> getListArrayByQuery(String sql) throws Exception;
	//
	public void    executeHQL(String hql) throws Exception;
	public void    executeSQL(String sql) throws Exception;
	//
	public void    clearSession()         throws Exception;
	public void    evict(Object obj)      throws Exception;
	//
	public Session getSession()           throws Exception;
	
	// JDBC do Spring
	public JdbcTemplate       getJdbcTemplate() throws Exception;
	public SimpleJdbcTemplate getSimpleJdbcTemplate() throws Exception;
	public SimpleJdbcInsert   getSimpleJdbcInsert() throws Exception;
	public Long               countLines(String table) throws Exception;
	public Query              getQuery(String sql) throws Exception;

}
