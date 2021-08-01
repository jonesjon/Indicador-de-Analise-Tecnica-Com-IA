package br.iesb.indicador_analise_grafica;

import java.text.DecimalFormat;
import java.io.Serializable;
import java.math.RoundingMode;
import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.GeneratorType;

import br.iesb.indicador_analise_grafica.primary_key.OperacaoPK;

@Entity
@Table(name = "OPERACAO")
public class Operacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private long iD;
	
	@Column(name="padrao")
	private String padrao;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel")})
	private Martelo martelo = null;
	
	@Column(name="start")
	private boolean start = false;
	
	@Column(name="tipoEntrada")
	private String tipoEntrada = null;
	
	@Column(name="precoEntrada")
	private Double precoEntrada;
	
	@Column(name="precoCancelarEntrada")
	private Double precoCancelarEntrada;
	
	@Column(name="precoGain")
	private Double precoGain;
	
	@Column(name="precoLoss")
	private Double precoLoss;
	
	@Column(name="precoGainMax")
	private Double precoGainMax;
	
	@Column(name="percentualGain")
	private Double percentualGain;
	
	@Column(name="percentualLoss")
	private Double percentualLoss;
	
	@Column(name="percentualGainMax")
	private Double percentualGainMax;
	
	@Column(name="lucro")
	private Boolean lucro = false;
	
	@Column(name="lucroMax")
	private Boolean lucroMax = false;
	
	@Column(name="porcentagemOperacaoFinal")
	private Double porcentagemOperacaoFinal;
	
	public Operacao() {
		
	}
	
	public Operacao(int iD, String padrao, Double precoEntrada, Double precoCancelarEntrada, Double precoGain, Double precoLoss) {
		this.iD = iD;
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

	public boolean isStart() {
		return start;
	}

	public String getEntrada() {
		return tipoEntrada;
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

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setEntrada(String entrada) {
		this.tipoEntrada = entrada;
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

	public String getPadrao() {
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

	public long getID() {
		return iD;
	}

}
