package br.com.cursojsf.prj.listner;

import java.io.Serializable;

import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Service;

import br.com.cursojsf.frw.util.UtilFramework;
import br.com.cursojsf.prj.model.classes.Entidade;
import br.com.cursojsf.prj.model.classes.InformacaoRevisao;

@Service
public class CustomListner implements RevisionListener, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public void newRevision(Object revisionEntity) {
		InformacaoRevisao infRev = (InformacaoRevisao) revisionEntity;
		
		Long codUser = UtilFramework.getThreadLocal().get();
		
		Entidade entidade = new Entidade();
		
		if (codUser != null && codUser !=0L) {
			entidade.setEnt_codigo(codUser);
			infRev.setEntidade(entidade);
		}
		
	}
	
	

}
