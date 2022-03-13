package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.List;

public enum PrecoAcimaMedia8 {

	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	PrecoAcimaMedia8(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<PrecoAcimaMedia8> getListPrecoAcimaMedia8(){
		List<PrecoAcimaMedia8> list = new ArrayList<PrecoAcimaMedia8>();
		
		list.add(SIM);
		list.add(NAO);
		
		return list;
	}
	
	public static PrecoAcimaMedia8 comparaPrecoAcimaMedia8(Boolean valor) {
		
		PrecoAcimaMedia8 precoRetorno = NULL;
		
		if(valor == true) {
			precoRetorno = PrecoAcimaMedia8.SIM;
		}else {
			precoRetorno = PrecoAcimaMedia8.NAO;
		}
		
		return precoRetorno;
		
	}
	
}
