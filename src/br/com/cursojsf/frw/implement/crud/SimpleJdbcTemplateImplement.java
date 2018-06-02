package br.com.cursojsf.frw.implement.crud;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)

public class SimpleJdbcTemplateImplement extends SimpleJdbcTemplate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public SimpleJdbcTemplateImplement(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

}
