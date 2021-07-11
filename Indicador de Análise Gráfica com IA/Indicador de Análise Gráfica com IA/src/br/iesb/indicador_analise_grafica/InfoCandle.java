package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name="INFO_CANDLE")
public class InfoCandle {
	
	
	@OneToOne
	private Candle candle;
	
	@EmbeddedId
	private InfoCandlePK infoCandlePK;
	
	@Column(name="precoMedia8")
	private Double precoMedia8;
	
	@Column(name="precoMedia20")
	private Double precoMedia20;
	
	@Column(name="precoMedia200")
	private Double precoMedia200;
	
	@Column(name="volumeMedia20")
	private Double volumeMedia20;

	
	public InfoCandle(Candle candle, Double precoMedia8, Double precoMedia20, Double precoMedia200,
			Double volumeMedia20, InfoCandlePK pk) {
		
		this.candle = candle;
		this.precoMedia8 = precoMedia8;
		this.precoMedia20 = precoMedia20;
		this.precoMedia200 = precoMedia200;
		this.volumeMedia20 = volumeMedia20;
		this.infoCandlePK = pk;
	}

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

	public Candle getCandle() {
		return candle;
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
	
	

}
