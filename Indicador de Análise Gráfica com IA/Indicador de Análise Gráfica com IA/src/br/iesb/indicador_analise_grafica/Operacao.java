package br.iesb.indicador_analise_grafica;

import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Operacao {
		
	private LocalDate data;
	private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	private Padroes padrao;
	private Martelo martelo = null;
	private boolean start = false;
	private Entrada entrada = null;
	private Double precoEntrada;
	private Double precoCancelarEntrada;
	private Double precoGain;
	private Double precoLoss;
	private Double precoGainMax;
	private Double percentualGain;
	private Double percentualLoss;
	private Double percentualGainMax;
	private Boolean lucro = false;
	private Boolean lucroMax = false;
	private Double porcentagemOperacaoFinal;
	
	public Operacao(LocalDate data,  Padroes padrao, Double precoEntrada, Double precoCancelarEntrada, Double precoGain, Double precoLoss) {
		this.data = data;
		this.padrao = padrao;
		this.precoEntrada = formataPreco(precoEntrada);
		this.precoCancelarEntrada = formataPreco(precoCancelarEntrada);
		this.precoGain = formataPreco(precoGain);
		this.precoLoss = formataPreco(precoLoss);
		this.precoGainMax = formataPreco(precoEntrada + ((precoGain-precoEntrada)*2));
		this.percentualGain = formataPreco((100*precoGain/precoEntrada)-100);
		this.percentualLoss = formataPreco((100*precoLoss/precoEntrada)-100);
		this.percentualGainMax = formataPreco((100*precoGainMax/precoEntrada)-100);
	}
	
	public Double formataPreco(Double num) {
		String fmt = "#.##";
		DecimalFormat df = new DecimalFormat(fmt);
		df.setRoundingMode(RoundingMode.DOWN);
		return Double.parseDouble(df.format(num).replace(',', '.'));
	}

	public LocalDate getData() {
		return data;
	}

	public DateTimeFormatter getFormato() {
		return formato;
	}

	public boolean isStart() {
		return start;
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public Double getPrecoEntrada() {
		return precoEntrada;
	}

	public Double getPrecoGain() {
		return precoGain;
	}

	public Double getPrecoLoss() {
		return precoLoss;
	}

	public Double getPercentualGain() {
		return percentualGain;
	}

	public void setPercentualGain(Double percentualGain) {
		this.percentualGain = percentualGain;
	}

	public Double getPercentualLoss() {
		return percentualLoss;
	}

	public void setPercentualLoss(Double percentualLoss) {
		this.percentualLoss = percentualLoss;
	}

	public Double getPercentualGainMax() {
		return percentualGainMax;
	}

	public void setPercentualGainMax(Double percentualGainMax) {
		this.percentualGainMax = percentualGainMax;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public void setFormato(DateTimeFormatter formato) {
		this.formato = formato;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public void setPrecoEntrada(Double precoEntrada) {
		this.precoEntrada = precoEntrada;
	}

	public void setPrecoGain(Double precoGain) {
		this.precoGain = precoGain;
	}

	public void setPrecoLoss(Double precoLoss) {
		this.precoLoss = precoLoss;
	}

	public Double getPrecoCancelarEntrada() {
		return precoCancelarEntrada;
	}

	public Padroes getPadrao() {
		return padrao;
	}

	public Boolean getLucro() {
		return lucro;
	}

	public void setLucro(Boolean lucro) {
		this.lucro = lucro;
	}

	public Double getPrecoGainMax() {
		return precoGainMax;
	}

	public Boolean getLucroMax() {
		return lucroMax;
	}

	public void setLucroMax(Boolean lucroMax) {
		this.lucroMax = lucroMax;
	}

	public Double getPorcentagemOperacaoFinal() {
		return porcentagemOperacaoFinal;
	}

	public void setPorcentagemOperacaoFinal(Double porcentagemOperacaoFinal) {
		this.porcentagemOperacaoFinal = porcentagemOperacaoFinal;
	}

	public Martelo getMartelo() {
		return martelo;
	}

	public void setMartelo(Martelo martelo) {
		this.martelo = martelo;
	}

	
	
	

}
