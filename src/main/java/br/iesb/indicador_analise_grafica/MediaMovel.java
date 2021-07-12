package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;

public class MediaMovel {
	
	private LocalDate data;
	private String nomeDoPapel;
	private final int MEDIA;
	private Double valor;
	private Double valorMediaVolume;

	MediaMovel(LocalDate data, String nomeDoPapel, int media, Double valor, Double valorMediaMovel){
		this.data = data;
		this.nomeDoPapel = nomeDoPapel;
		this.MEDIA = media;
		this.valor = valor;
		this.valorMediaVolume = valorMediaMovel;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public String getNomeDoPapel() {
		return nomeDoPapel;
	}
	
	public int getMEDIA() {
		return MEDIA;
	}
	
	public Double getValor() {
		return valor;
	}

	public Double getValorMediaVolume() {
		return valorMediaVolume;
	}
	
	

}
