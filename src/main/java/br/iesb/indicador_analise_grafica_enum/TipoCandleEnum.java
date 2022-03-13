package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum TipoCandleEnum {
	
	POSITIVO("Positivo", 1), NEGATIVO("Negativo", 2), NEUTRO("Neutro", 3), NULL("Null", 4);
	
	private String tipo;
	private int ID;
	
	TipoCandleEnum(String tipo, int ID){
		this.tipo = tipo;
		this.ID = ID;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public int getID() {
		return ID;
	}
	
	public static List<TipoCandleEnum> retornaTipos() {
		return Arrays.asList(TipoCandleEnum.values());
	}
	
	public static TipoCandleEnum comparaTipoCandle(String tipoString) {
		List<TipoCandleEnum> tipos = retornaTipos();
		
		TipoCandleEnum tipoRetorno = TipoCandleEnum.NULL;
		
		for(int i=0; i<tipos.size(); i++) {
			if(tipos.get(i).getTipo().equals(tipoString)){
				tipoRetorno = tipos.get(i);
			}
		}
		
		return tipoRetorno;
	}
	
	public static List<TipoCandleEnum> retornaTiposSemNeutro() {
		List<TipoCandleEnum> tipo = new ArrayList<TipoCandleEnum>();
		
		tipo.add(NEGATIVO);
		tipo.add(POSITIVO);
		
		return tipo;
		
	}
	
	

}
