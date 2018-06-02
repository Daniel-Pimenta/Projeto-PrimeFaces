package br.com.cursojsf.prj.util.all;

import org.springframework.stereotype.Component;

@Component
public abstract class BeanViewAbstract implements ActionViewPadrao {

	private static final long serialVersionUID = 1L;

	@Override
	public void limparLista() throws Exception {

	}

	@Override
	public void saveNoReturn() throws Exception {

	}

	@Override
	public String save() throws Exception {
		return null;
	}

	@Override
	public void saveEdit() throws Exception {

	}

	@Override
	public void excluir() throws Exception {

	}

	@Override
	public String ativar() throws Exception {
		return null;
	}

	@Override
	public String novo() throws Exception {
		return null;
	}

	@Override
	public String editar() throws Exception {
		return null;
	}

	@Override
	public void setNull() throws Exception {

	}

	@Override
	public void consultEntity() throws Exception {

	}

	@Override
	public void statusOperation(StatusPersistencia s) throws Exception {
    Mensagens.responseOperation(s);
	}

	@Override
	public String redirectNewEntity() throws Exception {
		return null;
	}

	@Override
	public String redirectFindEntity() throws Exception {
		return null;
	}

	@Override
	public void addMsg() throws Exception {

	}

	@Override
	public void addMsg(String msg) {
    Mensagens.msg(msg);
	}
	// ---------------------------------------------------------------------------------------
	
	protected void sucesso() throws Exception{
		statusOperation(StatusPersistencia.SUCESSO);
	}
  protected void error() throws Exception {
  	statusOperation(StatusPersistencia.ERRO);
  }
}
