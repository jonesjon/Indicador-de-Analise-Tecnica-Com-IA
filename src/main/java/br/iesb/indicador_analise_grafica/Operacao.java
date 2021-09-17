package br.iesb.indicador_analise_grafica;

import java.text.DecimalFormat;
import java.io.Serializable;
import java.math.RoundingMode;
import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.GeneratorType;

import br.iesb.indicador_analise_grafica.primary_key.MarteloPK;
import br.iesb.indicador_analise_grafica.primary_key.OperacaoPK;

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
	private String padrao;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private Martelo martelo = null;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private MarteloInvertido marteloInvertido = null;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private Marubozu marubozu = null;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private Engolfo engolfo = null;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private Doji doji = null;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private PiercingLine piercingLine = null;
	
	@OneToOne(mappedBy = "operacao", cascade = CascadeType.PERSIST)
	private TresSoldados tresSoldados = null;

	@Column(name="start")
	private boolean start = false;
	
	@Column(name="tipoEntrada")
	private String tipoEntrada = null;
	
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
	
	@Column(name="porcentagemOperacaoFinal")
	private Double porcentagemOperacaoFinal = 0.0;
	
	public PiercingLine getPiercingLine() {
		return piercingLine;
	}

	public void setPiercingLine(PiercingLine piercingLine) {
		this.piercingLine = piercingLine;
	}

	public Operacao() {
		
	}
	
	public Operacao(LocalDate dat, String nomeDoPapel, String padrao) {
		this.dat = dat;
		this.nomeDoPapel = nomeDoPapel;
		this.padrao = padrao;
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
	
	public Engolfo getEngolfo() {
		return engolfo;
	}

	public void setEngolfo(Engolfo engolfo) {
		this.engolfo = engolfo;
	}
	
	public String getNomeDoPapel() {
		return nomeDoPapel;
	}

	public LocalDate getData() {
		return dat;
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

	public Double getPrecoStop() {
		return precoStop;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setEntrada(String entrada) {
		this.tipoEntrada = entrada;
	}

	public void setPrecoEntrada(Double precoEntrada) {
		this.precoEntrada = formataPreco(precoEntrada);
	}

	public void setPrecoStop(Double precoLoss) {
		this.precoStop = formataPreco(precoLoss);
	}

	public String getPadrao() {
		return padrao;
	}

	public Double getPorcentagemOperacaoFinal() {
		return porcentagemOperacaoFinal;
	}

	public void setPorcentagemOperacaoFinal(Double porcentagemOperacaoFinal) {
		this.porcentagemOperacaoFinal = formataPreco(porcentagemOperacaoFinal);
	}

	public Martelo getMartelo() {
		return martelo;
	}

	public void setMartelo(Martelo martelo) {
		this.martelo = martelo;
	}

	public Marubozu getMarubozu() {
		return marubozu;
	}

	public void setMarubozu(Marubozu marubozu) {
		this.marubozu = marubozu;
	}

	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}

	public void setTipoEntrada(String tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public Double getPrecoPrimeiroAlvoFibonacci() {
		return precoPrimeiroAlvoFibonacci;
	}

	public void setPrecoPrimeiroAlvoFibonacci(Double precoPrimeiroAlvoFibonacci) {
		this.precoPrimeiroAlvoFibonacci = formataPreco(precoPrimeiroAlvoFibonacci);
	}

	public Double getPrecoSegundoAlvoFibonacci() {
		return precoSegundoAlvoFibonacci;
	}

	public void setPrecoSegundoAlvoFibonacci(Double precoSegundoAlvoFibonacci) {
		this.precoSegundoAlvoFibonacci = formataPreco(precoSegundoAlvoFibonacci);
	}

	public Double getPrecoTerceiroAlvoFibonacci() {
		return precoTerceiroAlvoFibonacci;
	}

	public void setPrecoTerceiroAlvoFibonacci(Double precoTerceiroAlvoFibonacci) {
		this.precoTerceiroAlvoFibonacci = formataPreco(precoTerceiroAlvoFibonacci);
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTipoEntrada() {
		return tipoEntrada;
	}

	public Doji getDoji() {
		return doji;
	}

	public void setDoji(Doji doji) {
		this.doji = doji;
	}

	public LocalDate getDat() {
		return dat;
	}

	public void setDat(LocalDate dat) {
		this.dat = dat;
	}

	public MarteloInvertido getMarteloInvertido() {
		return marteloInvertido;
	}

	public void setMarteloInvertido(MarteloInvertido marteloInvertido) {
		this.marteloInvertido = marteloInvertido;
	}

	public TresSoldados getTresSoldados() {
		return tresSoldados;
	}

	public void setTresSoldados(TresSoldados tresSoldados) {
		this.tresSoldados = tresSoldados;
	}


}
