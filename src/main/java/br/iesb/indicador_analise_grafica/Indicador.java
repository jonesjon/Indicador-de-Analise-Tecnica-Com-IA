package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;

public class Indicador {

	private static double mediaMovel;
	private static double soma;
	
	public static Double mediaMovel(int parametro, ArrayList<Candle> grafico) {
		
		
		if(grafico.size() >= parametro) {
			
			mediaMovel = 0;
			soma = 0;
			int aux = grafico.size()-1;
			
			for(int i=0; i<parametro; i++) {
				soma += grafico.get(aux).fechamento;
				aux--;
			}
				
			mediaMovel = soma/parametro;
			return mediaMovel;
		}
		return null;
	}
	
	public static Double mediaMovelVolume(int parametro, ArrayList<Candle> grafico) {
			
		if(grafico.size()>=parametro) {
		
			mediaMovel = 0;
			soma = 0;
			int aux = grafico.size()-1;
		
			for(int i=0; i<parametro; i++) {
				soma += grafico.get(aux).volume;
				aux--;
			}
			
			mediaMovel = soma/parametro;
		
			return mediaMovel;
		}
		return null;
	}

}
