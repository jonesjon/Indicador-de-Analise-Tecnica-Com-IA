package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;
import java.util.List;

public enum PrecoAcimaMedia200 {
	
	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	PrecoAcimaMedia200(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<PrecoAcimaMedia200> getListPrecoAcimaMedia200(){
		List<PrecoAcimaMedia200> list = new ArrayList<PrecoAcimaMedia200>();
		
		list.add(SIM);
		list.add(NAO);
		list.add(NULL);
		
		return list;
	}

}
