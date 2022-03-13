package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.List;

public enum PrecoAcimaMedia200Enum {
	
	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	PrecoAcimaMedia200Enum(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<PrecoAcimaMedia200Enum> getListPrecoAcimaMedia200(){
		List<PrecoAcimaMedia200Enum> list = new ArrayList<PrecoAcimaMedia200Enum>();
		
		list.add(SIM);
		list.add(NAO);
		
		return list;
	}
	
	public static PrecoAcimaMedia200Enum comparaPrecoAcimaMedia200(Boolean valor) {
		
		PrecoAcimaMedia200Enum precoRetorno = NULL;
		
		if(valor == true) {
			precoRetorno = PrecoAcimaMedia200Enum.SIM;
		}else {
			precoRetorno = PrecoAcimaMedia200Enum.NAO;
		}
		
		return precoRetorno;
		
	}
	
	public PrecoAcimaMedia200Enum[] getAll() {
		return PrecoAcimaMedia200Enum.values();
	}

}
