package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.List;

public enum PrecoAcimaMedia20Enum {

	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	PrecoAcimaMedia20Enum(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<PrecoAcimaMedia20Enum> getListPrecoAcimaMedia20(){
		List<PrecoAcimaMedia20Enum> list = new ArrayList<PrecoAcimaMedia20Enum>();
		
		list.add(SIM);
		list.add(NAO);
		
		return list;
	}
	
	public static PrecoAcimaMedia20Enum comparaPrecoAcimaMedia20(Boolean valor) {
		
		PrecoAcimaMedia20Enum precoRetorno = NULL;
		
		if(valor == true) {
			precoRetorno = PrecoAcimaMedia20Enum.SIM;
		}else {
			precoRetorno = PrecoAcimaMedia20Enum.NAO;
		}
		
		return precoRetorno;
		
	}
	
}
