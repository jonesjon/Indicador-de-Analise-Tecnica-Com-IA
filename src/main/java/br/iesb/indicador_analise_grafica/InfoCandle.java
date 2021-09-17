package br.iesb.indicador_analise_grafica;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

import br.iesb.indicador_analise_grafica.primary_key.InfoCandlePK;


@Entity
@Table(name = "INFO_CANDLE")
@IdClass(InfoCandlePK.class)
public class InfoCandle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "nomeDoPapel")
	private String nomeDoPapel;

	@Id
	@Column(name = "dat")
	private LocalDate data;

	@Column(name = "abertura")
	private Double abertura;

	@Column(name = "fechamento")
	private Double fechamento;

	@Column(name = "maxima")
	private Double maxima;

	@Column(name = "minima")
	private Double minima;

	@Column(name = "volume")
	private Double volume;

	@Column(name = "precoMedia8")
	private Double precoMedia8;

	@Column(name = "precoMedia20")
	private Double precoMedia20;

	@Column(name = "precoMedia200")
	private Double precoMedia200;

	@Column(name = "volumeMedia20")
	private Double volumeMedia20;

	public InfoCandle() {

	}
	public InfoCandle(Candle candle, Double precoMedia8, Double precoMedia20, Double precoMedia200,
			Double volumeMedia20) {

		this.precoMedia8 = precoMedia8;
		this.precoMedia20 = precoMedia20;
		this.precoMedia200 = precoMedia200;
		this.volumeMedia20 = volumeMedia20;
		this.nomeDoPapel = candle.getPapel();
		this.abertura = candle.abertura;
		this.fechamento = candle.fechamento;
		this.maxima = candle.maxima;
		this.minima = candle.minima;
		this.volume = candle.volume;
		this.data = candle.data;
	}

	/*
	 * public void setInfoCandlePK(InfoCandlePK infoCandlePK) { this.infoCandlePK =
	 * infoCandlePK; }
	 * 
	 * public InfoCandlePK getInfoCandlePK() { return infoCandlePK; }
	 */

	public void setPrecoMedia8(Double precoMedia8) {
		this.precoMedia8 = precoMedia8;
	}

	public void setPrecoMedia20(Double precoMedia20) {
		this.precoMedia20 = precoMedia20;
	}

	public void setPrecoMedia200(Double precoMedia200) {
		this.precoMedia200 = precoMedia200;
	}

	public void setVolumeMedia20(Double volumeMedia20) {
		this.volumeMedia20 = volumeMedia20;
	}

	public Double getPrecoMedia8() {
		return precoMedia8;
	}

	public Double getPrecoMedia20() {
		return precoMedia20;
	}

	public Double getPrecoMedia200() {
		return precoMedia200;
	}

	public Double getVolumeMedia20() {
		return volumeMedia20;
	}

	public String getNomeDoPapel() {
		return nomeDoPapel;
	}

	public LocalDate getData() {
		return data;
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
	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public void setAbertura(Double abertura) {
		this.abertura = abertura;
	}
	public void setFechamento(Double fechamento) {
		this.fechamento = fechamento;
	}
	public void setMaxima(Double maxima) {
		this.maxima = maxima;
	}
	public void setMinima(Double minima) {
		this.minima = minima;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}

}
