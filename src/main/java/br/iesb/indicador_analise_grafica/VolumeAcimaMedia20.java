package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;
import java.util.List;

public enum VolumeAcimaMedia20 {
	
	SIM("1"), NAO("0"), NULL("null");
	
	VolumeAcimaMedia20(String variavel) {
		this.valor = variavel;
	}
	
	private String valor;
	
	public String getValor() {
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
