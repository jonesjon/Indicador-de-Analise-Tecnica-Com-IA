package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;

public class Indicador {

	private static double mediaMovel;
	private static double soma;
	
	public static Double mediaMovel(int parametro, ArrayList<InfoCandle> listaInfoCandle, Candle candle) {
		
		if(listaInfoCandle.size()>=parametro-1) {
			
			mediaMovel = 0;
			soma = 0;
			
			for(int i=0; i<parametro-1; i++) {
				soma += listaInfoCandle.get(i).getFechamento();
			}
			
			soma+= candle.fechamento;
			mediaMovel = soma/parametro;
			
			return mediaMovel;
		}
		
		return null;
	}
	
	public static Double mediaMovelVolume(int parametro, ArrayList<InfoCandle> grafico, Candle candle) {
			
		if(grafico.size()>=parametro-1) {
		
			mediaMovel = 0;
			soma = 0;
		
			for(int i=0; i<parametro-1; i++) {
				soma += grafico.get(i).getVolume();
			}
			
			soma+= candle.volume;
			mediaMovel = soma/parametro;
		
			return mediaMovel;
		}
		return null;
	}

}
