package br.iesb.indicador_analise_grafica_enum;

import java.util.Arrays;
import java.util.List;


public enum Perfuracao {
	
	POUCA(1, 50),
	MEDIANA(50, 100),
	MUITA(100, 500),
	EXTREMA(500, 9999999),
	NULL(0, 0);
	
	private int min;
	private  int max;
	
	Perfuracao(int min, int max){
		this.min = min;
		this.max = max;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public static List<Perfuracao> retornaPerfuracoes() {
		return Arrays.asList(Perfuracao.values());
	}
	
	public static Perfuracao comparaPerfuracaoComInteiro(int perf) {
		if(perf >= POUCA.min && perf < POUCA.max) {
			return Perfuracao.POUCA;
		}else if(perf >= MEDIANA.min && perf < MEDIANA.max) {
			return Perfuracao.MEDIANA;
		}else if(perf >= MUITA.min && perf < MUITA.max) {
			return Perfuracao.MUITA;
		}else if(perf >= EXTREMA.min && perf < EXTREMA.max) {
			return Perfuracao.EXTREMA;
		}
		return Perfuracao.NULL;
	}

}
