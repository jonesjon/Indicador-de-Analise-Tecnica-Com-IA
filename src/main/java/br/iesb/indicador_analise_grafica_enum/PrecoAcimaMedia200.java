package br.iesb.indicador_analise_grafica_enum;

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
	
	public static PrecoAcimaMedia200 comparaPrecoAcimaMedia200(Boolean valor) {
		
		PrecoAcimaMedia200 precoRetorno = NULL;
		
		if(valor == true) {
			precoRetorno = PrecoAcimaMedia200.SIM;
		}else {
			precoRetorno = PrecoAcimaMedia200.NAO;
		}
		
		return precoRetorno;
		
	}

}
