package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
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
		
		this.abertura = Double.parseDouble(formataNumero(abertura));
		this.fechamento = Double.parseDouble(formataNumero(fechamento));
		this.maxima = Double.parseDouble(formataNumero(maxima));
		this.minima = Double.parseDouble(formataNumero(minima));
		this.volume = Double.parseDouble(volume);
		this.data = date;
		this.papel = papel;
		
	}
	
	private static String formataNumero(String dado) {
        Long lo = Long.parseLong(dado);
        Float l = lo/100f;
        return l.toString();
	}
	
	public LocalDate getDate() {
		return data;
	}

	public String getPapel() {
		return papel;
	}

	public double getMaxima() {
		return this.maxima;
	}


	
}
