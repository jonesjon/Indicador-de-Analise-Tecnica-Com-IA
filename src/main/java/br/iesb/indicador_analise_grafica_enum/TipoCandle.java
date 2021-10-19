package br.iesb.indicador_analise_grafica_enum;

import java.util.Arrays;
import java.util.List;


public enum TipoCandle {
	
	POSITIVO("Positivo", 1), NEGATIVO("Negativo", 2), NEUTRO("Neutro", 3), NULL("Null", 4);
	
	private String tipo;
	private int ID;
	
	TipoCandle(String tipo, int ID){
		this.tipo = tipo;
		this.ID = ID;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public int getID() {
		return ID;
	}
	
	public static List<TipoCandle> retornaTipos() {
		return Arrays.asList(TipoCandle.values());
	}
	
	public static TipoCandle comparaTipoCandle(String tipoString) {
		List<TipoCandle> tipos = retornaTipos();
		
		TipoCandle tipoRetorno = TipoCandle.NULL;
		
		for(int i=0; i<tipos.size(); i++) {
			if(tipos.get(i).getTipo().equals(tipoString)){
				tipoRetorno = tipos.get(i);
			}
		}
		
		return tipoRetorno;
	}

}
