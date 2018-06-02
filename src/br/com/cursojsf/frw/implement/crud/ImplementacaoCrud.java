package br.com.cursojsf.frw.implement.crud;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.JDBCContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursojsf.frw.hibernate.session.HibernateUtil;
import br.com.cursojsf.frw.interfac.crud.InterfaceCrud;

@Component
@Transactional
public class ImplementacaoCrud<E> implements InterfaceCrud<E>{

	private static final long serialVersionUID = 1L;

	private static SessionFactory sf = HibernateUtil.getSessionFactory();
	@Autowired
	private JdbcTemplateImplement jdbcTemplate;
	@Autowired
	private SimpleJdbcTemplateImplement sjti;
	@Autowired
	private SimpleJdbcInsertImplement sjii;
	@Autowired
	private SimpleJdbcClassImplement sjci;
	
	private void validaSessionFactory() {
		if (sf == null) {
			sf = HibernateUtil.getSessionFactory();
		}
	}
	private void validaTransacao() {
		if (!sf.getCurrentSession().getTransaction().isActive()) {
			sf.getCurrentSession().beginTransaction();
		}
	}
	private void commitAjax() {
		sf.getCurrentSession().beginTransaction().commit();
	}
	private void rolbackAjax() {
		sf.getCurrentSession().beginTransaction().rollback();
	}
	
	
	// --------------------------------------------------------------------------------
	@Override
	public void save(E entidade) throws Exception {
		// TODO Auto-generated method stub
		validaSessionFactory();
		sf.getCurrentSession().save(entidade);
		sf.getCurrentSession().flush();
	}

	@Override
	public void persist(E entidade) throws Exception {
		// TODO Auto-generated method stub
		validaSessionFactory();
		sf.getCurrentSession().persist(entidade);
		sf.getCurrentSession().flush();		
	}

	@Override
	public void saveOrUpdate(E entidade) throws Exception {
		// TODO Auto-generated method stub
		validaSessionFactory();
		sf.getCurrentSession().saveOrUpdate(entidade);
		sf.getCurrentSession().flush();
		
	}

	@Override
	public void update(E entidade) throws Exception {
		// TODO Auto-generated method stub
		validaSessionFactory();
		sf.getCurrentSession().update(entidade);
		sf.getCurrentSession().flush();
	}

	@Override
	public void delete(E entidade) throws Exception {
		// TODO Auto-generated method stub
		validaSessionFactory();
		sf.getCurrentSession().delete(entidade);
		sf.getCurrentSession().flush();
	}

	@Override
	public E merge(E entidade) throws Exception {
		validaSessionFactory();
		E saida = (E) sf.getCurrentSession().merge(entidade);
		sf.getCurrentSession().flush();
		return saida;
	}
  // -------------------  PESQUISAS  -------------------------------
	@Override
	public List<E> findList(Class<E> entidade) throws Exception {
		// TODO Auto-generated method stub
		validaSessionFactory();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT DISTINC(ENTITY) FROM ");
		hql.append(entidade.getSimpleName());
		hql.append(" ENTITY ");
		List<E> lista = sf.getCurrentSession().createQuery(hql.toString()).list();
		//sf.getCurrentSession().flush();
		return lista;
	}

	@Override
	public E findById(Class<E> entidade, Long id) throws Exception {
		validaSessionFactory();
		E saida = (E) sf.getCurrentSession().load(entidade.getClass(), id);
		return saida;
	}
	
	@Override
	public List<E> findListByQuery(String hql) throws Exception {
		validaSessionFactory();
		List<E> lista = new ArrayList<E>();
		lista = sf.getCurrentSession().createQuery(hql).list();
		return lista;
	}

	@Override
	public List<E> findListByQuery(String hql, int ini, int fim) throws Exception {
		validaSessionFactory();
		List<E> lista = new ArrayList<E>();
		lista = sf.getCurrentSession().createQuery(hql).setFirstResult(ini).setMaxResults(fim).list();
		return lista;
	}
	
	@Override
	public List<?> getListByQuery(String sql) throws Exception {
		validaSessionFactory();
		List<?> lista = sf.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}

	@Override
	public List<Object[]> getListArrayByQuery(String sql){
		validaSessionFactory();
		List<Object[]> lista = (List<Object[]>) sf.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}
	
	@Override
	public void executeHQL(String hql) throws Exception {
		validaSessionFactory();
		sf.getCurrentSession().createQuery(hql).executeUpdate();
		sf.getCurrentSession().flush();
	}
	
	public void executeSQL(String sql) throws Exception {
		validaSessionFactory();
		sf.getCurrentSession().createSQLQuery(sql).executeUpdate();
		sf.getCurrentSession().flush();
	}
	
	@Override
	public void clearSession() throws Exception {
		validaSessionFactory();
		sf.getCurrentSession().clear();
		
	}

	@Override
	public void evict(Object obj) throws Exception {
		validaSessionFactory();
		sf.getCurrentSession().evict(obj);
	}

	@Override
	public Session getSession() throws Exception {
		validaSessionFactory();
		return sf.getCurrentSession();
	}

	@Override
	public JdbcTemplate getJdbcTemplate() throws Exception {
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countLines(String table) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(1) FROM ").append(table);
	  //validaSessionFactory(); //Não precisa pois esta usando a conexao Spring
		//long qtd = sf.getCurrentSession().createSQLQuery(sql.toString()).executeUpdate();
		long qtd = getJdbcTemplate().queryForLong(sql.toString());
		return qtd;
	}
	
	@Override
	public Query getQuery(String sql) throws Exception {
		validaSessionFactory();
		Query queryReturn = sf.getCurrentSession().createQuery(sql);
		return queryReturn;
	}

  //--------------------------------------------------------------------------------
	public static SessionFactory getSessionFactory() {
		return sf;
	}

	public SimpleJdbcTemplateImplement getSjti() {
		return sjti;
	}

	public SimpleJdbcInsertImplement getSjii() {
		return sjii;
	}

	public SimpleJdbcClassImplement getSjci() {
		return sjci;
	}

	
	

}
