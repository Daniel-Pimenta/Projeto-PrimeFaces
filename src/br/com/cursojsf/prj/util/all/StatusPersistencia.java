package br.com.cursojsf.prj.util.all;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum StatusPersistencia {

	ERRO("Erro"),
	SUCESSO("Sucesso"),
	OBJETO_REFERENCIADO("Este objeto não pode ser apagado por possuir referencias ao mesmo."),
	;

	private String valor = "";
	//private String descricao = "";
	
	private StatusPersistencia() {
		// TODO Auto-generated constructor stub
	}

	private StatusPersistencia(String name) {
		this.valor = name;
		//this.descricao = description;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	
	@Override
	public String toString() {
		return this.getValor();
	}
	
	public static List<StatusPersistencia> getListaStatus(){
		List<StatusPersistencia> listStatus = new ArrayList<StatusPersistencia>();
		for (StatusPersistencia status : StatusPersistencia.values()) {
			listStatus.add(status);
		}
    Collections.sort(listStatus, new Comparator<StatusPersistencia>() {

			@Override
			public int compare(StatusPersistencia o1, StatusPersistencia o2) {
				// TODO Auto-generated method stub
				return new Integer(o1.ordinal()).compareTo(new Integer(o2.ordinal()));
			}
    	
    });
		return listStatus;
	}
	
}
