package br.com.cursojsf.prj.acesso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Permissao {
	
	ADMIN("ADMIN"                        ,"Arministrador"),
	USER("USER"                          ,"Usuario pardão"),
	CADASTRO_ACESSO("CADASTRO_ACESSO"    ,"Cadastro - Acessar"),
	FINANCEIRO_ACESSO("FINANCEIRO_ACESSO","Financeio - Acessar"),
	MENSAGEM_ACESSO("MENSAGEM_ACESSO"    ,"Mensagem recebida - Acessar")
	;

	private String valor = "";
	private String descricao = "";
	
	private Permissao() {
		// TODO Auto-generated constructor stub
	}

	private Permissao(String name, String description) {
		this.valor = name;
		this.descricao = description;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.getValor();
	}
	
	public static List<Permissao> getListaPermissao(){
		List<Permissao> permissoes = new ArrayList<Permissao>();
		for (Permissao permissao : Permissao.values()) {
			permissoes.add(permissao);
		}
    Collections.sort(permissoes, new Comparator<Permissao>() {

			@Override
			public int compare(Permissao o1, Permissao o2) {
				// TODO Auto-generated method stub
				return new Integer(o1.ordinal()).compareTo(new Integer(o2.ordinal()));
			}
    	
    });
		return permissoes;
	}
	
}
