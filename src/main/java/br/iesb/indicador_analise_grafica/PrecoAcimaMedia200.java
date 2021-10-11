package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;
import java.util.List;

public enum PrecoAcimaMedia200 {
	
	SIM("1"), NAO("0"), NULL("null");
	
	PrecoAcimaMedia200(String variavel) {
		this.valor = variavel;
	}
	
	private String valor;
	
	public String getValor() {
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
