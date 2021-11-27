package br.iesb.indicador_analise_grafica;

import java.text.DecimalFormat;
import java.io.Serializable;
import java.math.RoundingMode;
import java.time.LocalDate;
import javax.persistence.*;

import br.iesb.indicador_analise_grafica.padroes.Padrao;
import br.iesb.indicador_analise_grafica.primary_key.OperacaoPK;
import br.iesb.indicador_analise_grafica_enum.Entrada;
import br.iesb.indicador_analise_grafica_enum.PadroesEnum;

@Entity
@Table(name = "OPERACAO")
@IdClass(OperacaoPK.class)
public class Operacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="dat")
	private LocalDate dat;
	
	@Id
	@Column(name="nomeDoPapel")
	private String nomeDoPapel;
	
	@Id
	@Column(name="padrao")
	@Enumerated(EnumType.STRING)
	private PadroesEnum padraoEnum;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private Padrao padrao = null;
	
	@Column
	private LocalDate dataFinal;

	@Column(name="start")
	private boolean start = false;
	
	@Column(name="tipoEntrada")
	@Enumerated(EnumType.STRING)
	private Entrada tipoEntradaEnum = null;
	
	@Column(name="precoEntrada")
	private Double precoEntrada;

	@Column(name="precoStop")
	private Double precoStop;
	
	@Column(name="precoPrimeiroAlvoFibonacci")
	private Double precoPrimeiroAlvoFibonacci;
	
	@Column(name="precoSegundoAlvoFibonacci")
	private Double precoSegundoAlvoFibonacci;
	
	@Column(name="precoTerceiroAlvoFibonacci")
	private Double precoTerceiroAlvoFibonacci;
	
	@Column(name="primeiroAlvoAtingido")
	private Boolean primeiroAlvoAtingido = false;
	
	@Column(name="segundoAlvoAtingido")
	private Boolean segundoAlvoAtingido = false;
	
	@Column(name="terceiroAlvoAtingido")
	private Boolean terceiroAlvoAtingido = false;
	
	@Column
	private Boolean operacaoFinalizada = false;
	
	@Column(name="porcentagemOperacaoFinal")
	private Double porcentagemOperacaoFinal = 0.0;
	
	
	public Operacao() {
		
	}
	
	public Operacao(LocalDate dat, String nomeDoPapel, PadroesEnum padrao) {
		this.dat = dat;
		this.nomeDoPapel = nomeDoPapel;
		this.padraoEnum = padrao;
	}
	
	public Double formataPreco(Double num) {
		
		String fmt = "#.##";
		DecimalFormat df = new DecimalFormat(fmt);
		df.setRoundingMode(RoundingMode.DOWN);
		if(!df.format(num).replace(',', '.').equals("-∞") && !df.format(num).replace(',', '.').equals("∞")) {
			return Double.parseDouble(df.format(num).replace(',', '.'));
		}
		
		return 1000000.0;
	}

	public LocalDate getDat() {
		return dat;
	}

	public void setDat(LocalDate dat) {
		this.dat = dat;
	}

	public String getNomeDoPapel() {
		return nomeDoPapel;
	}

	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}

	public PadroesEnum getPadraoEnum() {
		return padraoEnum;
	}

	public void setPadraoEnum(PadroesEnum padraoEnum) {
		this.padraoEnum = padraoEnum;
	}

	public Padrao getPadrao() {
		return padrao;
	}

	public void setPadrao(Padrao padrao) {
		this.padrao = padrao;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public Entrada getTipoEntrada() {
		return tipoEntradaEnum;
	}

	public void setTipoEntrada(Entrada tipoEntrada) {
		this.tipoEntradaEnum = tipoEntrada;
	}

	public Double getPrecoEntrada() {
		return precoEntrada;
	}

	public void setPrecoEntrada(Double precoEntrada) {
		this.precoEntrada = precoEntrada;
	}

	public Double getPrecoStop() {
		return precoStop;
	}

	public void setPrecoStop(Double precoStop) {
		this.precoStop = precoStop;
	}

	public Double getPrecoPrimeiroAlvoFibonacci() {
		return precoPrimeiroAlvoFibonacci;
	}

	public void setPrecoPrimeiroAlvoFibonacci(Double precoPrimeiroAlvoFibonacci) {
		this.precoPrimeiroAlvoFibonacci = precoPrimeiroAlvoFibonacci;
	}

	public Double getPrecoSegundoAlvoFibonacci() {
		return precoSegundoAlvoFibonacci;
	}

	public void setPrecoSegundoAlvoFibonacci(Double precoSegundoAlvoFibonacci) {
		this.precoSegundoAlvoFibonacci = precoSegundoAlvoFibonacci;
	}

	public Double getPrecoTerceiroAlvoFibonacci() {
		return precoTerceiroAlvoFibonacci;
	}

	public void setPrecoTerceiroAlvoFibonacci(Double precoTerceiroAlvoFibonacci) {
		this.precoTerceiroAlvoFibonacci = precoTerceiroAlvoFibonacci;
	}

	public Boolean getPrimeiroAlvoAtingido() {
		return primeiroAlvoAtingido;
	}

	public void setPrimeiroAlvoAtingido(Boolean primeiroAlvoAtingido) {
		this.primeiroAlvoAtingido = primeiroAlvoAtingido;
	}

	public Boolean getSegundoAlvoAtingido() {
		return segundoAlvoAtingido;
	}

	public void setSegundoAlvoAtingido(Boolean segundoAlvoAtingido) {
		this.segundoAlvoAtingido = segundoAlvoAtingido;
	}

	public Boolean getTerceiroAlvoAtingido() {
		return terceiroAlvoAtingido;
	}

	public void setTerceiroAlvoAtingido(Boolean terceiroAlvoAtingido) {
		this.terceiroAlvoAtingido = terceiroAlvoAtingido;
	}

	public Boolean getOperacaoFinalizada() {
		return operacaoFinalizada;
	}

	public void setOperacaoFinalizada(Boolean operacaoFinalizada) {
		this.operacaoFinalizada = operacaoFinalizada;
	}

	public Double getPorcentagemOperacaoFinal() {
		return porcentagemOperacaoFinal;
	}

	public void setPorcentagemOperacaoFinal(Double porcentagemOperacaoFinal) {
		this.porcentagemOperacaoFinal = porcentagemOperacaoFinal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
	