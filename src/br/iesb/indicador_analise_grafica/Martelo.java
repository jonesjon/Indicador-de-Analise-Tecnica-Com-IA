package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Martelo {
	
	private final LocalDate data;
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final TipoCandle tipo;
	private final PavioSuperior pavioSuperior;
	private final PavioInferior pavioInferior;
	private final Boolean volumeAcimaMedia20;
	private boolean startOperacao = false;
	private Double precoAtivarOperacao;
	private Double precoCancelarOperacao;
	
	
	public Martelo(LocalDate data, TipoCandle tipo, PavioSuperior pavioSuperior,
			PavioInferior pavioInferior, Boolean volumeAcimaMedia20) {
		super();
		this.data = data;
		this.tipo = tipo;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}
	
	public LocalDate getData() {
		return data;
	}

	public TipoCandle getTipo() {
		return tipo;
	}

	public PavioSuperior getPavioSuperior() {
		return pavioSuperior;
	}

	public PavioInferior getPavioInferior() {
		return pavioInferior;
	}

	public Boolean getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}
	
	public Boolean getStartOperacao() {
		return startOperacao;
	}

	public void setFormato(DateTimeFormatter formato) {
		this.formato = formato;
	}

	public void setStartOperacao(boolean startOperacao) {
		this.startOperacao = startOperacao;
	}

	public Double getPrecoAtivarOperacao() {
		return precoAtivarOperacao;
	}

	public void setPrecoAtivarOperacao(Double precoAtivarOperacao) {
		this.precoAtivarOperacao = precoAtivarOperacao;
	}

	public Double getPrecoCancelarOperacao() {
		return precoCancelarOperacao;
	}

	public void setPrecoCancelarOperacao(Double precoCancelarOperacao) {
		this.precoCancelarOperacao = precoCancelarOperacao;
	}

	public DateTimeFormatter getFormato() {
		return formato;
	}

}
