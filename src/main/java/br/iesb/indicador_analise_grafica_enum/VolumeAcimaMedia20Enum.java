package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.List;

public enum VolumeAcimaMedia20Enum {
	
	SIM("1", 1), NAO("0", 0), NULL("null", -1);
	
	private String nome;
	private int valor;
	
	VolumeAcimaMedia20Enum(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getString() {
		return nome;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static List<VolumeAcimaMedia20Enum> getListVolumeAcimaMedia20(){
		List<VolumeAcimaMedia20Enum> list = new ArrayList<VolumeAcimaMedia20Enum>();
		
		list.add(SIM);
		list.add(NAO);
		
		return list;
	}
	
	public static VolumeAcimaMedia20Enum comparaVolumeAcimaMedia20(Boolean valor) {
		VolumeAcimaMedia20Enum volRetorno = NULL;
		
		if(valor == true) {
			volRetorno = VolumeAcimaMedia20Enum.SIM;
		}else {
			volRetorno = VolumeAcimaMedia20Enum.NAO;
		}
		
		return volRetorno;
		
	}

}
