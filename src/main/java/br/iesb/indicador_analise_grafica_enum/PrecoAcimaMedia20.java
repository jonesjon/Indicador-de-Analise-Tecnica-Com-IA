package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.List;

public enum PrecoAcimaMedia20 {

	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	PrecoAcimaMedia20(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<PrecoAcimaMedia20> getListPrecoAcimaMedia20(){
		List<PrecoAcimaMedia20> list = new ArrayList<PrecoAcimaMedia20>();
		
		list.add(SIM);
		list.add(NAO);
		list.add(NULL);
		
		return list;
	}
	
	public static PrecoAcimaMedia20 comparaPrecoAcimaMedia20(Boolean valor) {
		
		PrecoAcimaMedia20 precoRetorno = NULL;
		
		if(valor == true) {
			precoRetorno = PrecoAcimaMedia20.SIM;
		}else {
			precoRetorno = PrecoAcimaMedia20.NAO;
		}
		
		return precoRetorno;
		
	}
	
}
