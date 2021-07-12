package br.iesb.indicador_analise_grafica;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Candle {
	

	LocalDate data;
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	private String papel;
	double abertura; 
	double fechamento; 
	double maxima; 
	double minima; 
	double volume;
	
	public Candle() {
		
	}
	
		
	public Candle(LocalDate date, String abertura, String maxima,
				String minima, String fechamento, String volume, String papel) {
		
		this.abertura = Double.parseDouble(abertura)/100;
		this.fechamento = Double.parseDouble(fechamento)/100;
		this.maxima = Double.parseDouble(maxima)/100;
		this.minima = Double.parseDouble(minima)/100;
		this.volume = Double.parseDouble(volume)/100;
		this.data = date;
		this.papel = papel;
		
	}
	
	public LocalDate getDate() {
		return data;
	}

	public String getPapel() {
		return papel;
	}


	
}
