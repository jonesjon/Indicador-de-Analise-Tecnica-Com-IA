package br.iesb.indicador_analise_grafica;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Candle {
	
	LocalDate data;
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	
	private String papel;
	double abertura, fechamento, maxima, minima, volume;
	
		
	public Candle(String dia, String mes, String ano, String abertura, String maxima,
																			String minima, String fechamento, String volume, String papel) {
		
		this.abertura = Double.parseDouble(abertura)/100;
		this.fechamento = Double.parseDouble(fechamento)/100;
		this.maxima = Double.parseDouble(maxima)/100;
		this.minima = Double.parseDouble(minima)/100;
		this.volume = Double.parseDouble(volume)/100;
		this.data = LocalDate.parse(dia + "/" + mes + "/" + ano, formato);
		this.papel = papel;
		
	}

	public String getPapel() {
		return papel;
	}

	
}
