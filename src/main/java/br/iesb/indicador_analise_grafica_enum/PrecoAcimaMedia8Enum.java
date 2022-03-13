package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.List;

public enum PrecoAcimaMedia8Enum {

	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	PrecoAcimaMedia8Enum(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<PrecoAcimaMedia8Enum> getListPrecoAcimaMedia8(){
		List<PrecoAcimaMedia8Enum> list = new ArrayList<PrecoAcimaMedia8Enum>();
		
		list.add(SIM);
		list.add(NAO);
		
		return list;
	}
	
	public static PrecoAcimaMedia8Enum comparaPrecoAcimaMedia8(Boolean valor) {
		
		PrecoAcimaMedia8Enum precoRetorno = NULL;
		
		if(valor == true) {
			precoRetorno = PrecoAcimaMedia8Enum.SIM;
		}else {
			precoRetorno = PrecoAcimaMedia8Enum.NAO;
		}
		
		return precoRetorno;
		
	}
	
}
