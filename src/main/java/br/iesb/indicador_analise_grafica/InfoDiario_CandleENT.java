package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="InfoDiario_Candle")
public class InfoDiario_CandleENT {
	
	@Id
	private Long id;
	
	/*
	@Column(name="precoMedia8")
	private Double media8;
	
	@Column(name="precoMedia20")
	private Double media20;
	
	@Column(name="precoMedia200")
	private Double media200;
	
	@Column(name="precoMedia20Volume")
	private Double precoMedia20Volume;
	
	@Column(name="fk_Grafico_nomeDoPapel")
	private Grafico grafico;
	
	@Column(name="maxima")
	private Double abertura;
	
	@Column(name="maxima")
	private Double fechamento;
	
	@Column(name="maxima")
	private Double maxima;
	
	@Column(name="maxima")
	private Double minima;
	
	@Column(name="maxima")
	private Double volume;
	
	@Column(name="maxima")
	private LocalDate data;
	 
	public InfoDiario_CandleENT(Double media8, Double media20, Double media200, Double precoMedia20Volume,
			Grafico grafico, Double abertura, Double fechamento, Double maxima, Double minima, Double volume,
			LocalDate data) {
		
		this.media8 = media8;
		this.media20 = media20;
		this.media200 = media200;
		this.precoMedia20Volume = precoMedia20Volume;
		this.grafico = grafico;
		this.abertura = abertura;
		this.fechamento = fechamento;
		this.maxima = maxima;
		this.minima = minima;
		this.volume = volume;
		this.data = data;
	}
	public Double getMedia8() {
		return media8;
	}
	public Double getMedia20() {
		return media20;
	}
	public Double getMedia200() {
		return media200;
	}
	public Double getPrecoMedia20Volume() {
		return precoMedia20Volume;
	}
	public String getNomeDoPapel() {
		return nomeDoPapel;
	}
	public Double getAbertura() {
		return abertura;
	}
	public Double getFechamento() {
		return fechamento;
	}
	public Double getMaxima() {
		return maxima;
	}
	public Double getMinima() {
		return minima;
	}
	public Double getVolume() {
		return volume;
	}
	public LocalDate getData() {
		return data;
	}*/
	

}
