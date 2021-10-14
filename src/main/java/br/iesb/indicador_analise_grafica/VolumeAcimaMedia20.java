package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;
import java.util.List;

public enum VolumeAcimaMedia20 {
	
	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	VolumeAcimaMedia20(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<VolumeAcimaMedia20> getListVolumeAcimaMedia20(){
		List<VolumeAcimaMedia20> list = new ArrayList<VolumeAcimaMedia20>();
		
		list.add(SIM);
		list.add(NAO);
		list.add(NULL);
		
		return list;
	}

}
