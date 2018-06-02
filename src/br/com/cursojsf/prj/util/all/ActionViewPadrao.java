package br.com.cursojsf.prj.util.all;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public interface ActionViewPadrao extends Serializable{

	abstract void   limparLista()     throws Exception;
	abstract void   saveNoReturn()    throws Exception;
	abstract String save()            throws Exception;
	abstract void   saveEdit()        throws Exception;
	abstract void   excluir()         throws Exception;
	abstract String ativar()          throws Exception;
	
	@PostConstruct
	abstract String novo()            throws Exception;
	abstract String editar()          throws Exception;
	abstract void   setNull()         throws Exception;
	abstract void   consultEntity()   throws Exception;
	
	abstract void   statusOperation(StatusPersistencia s) throws Exception;
	
	abstract String redirectNewEntity() throws Exception;
	abstract String redirectFindEntity() throws Exception;
	abstract void   addMsg() throws Exception;
	
	abstract void   addMsg(String msg); 
	
	
}
