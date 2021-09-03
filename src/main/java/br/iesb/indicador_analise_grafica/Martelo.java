package br.iesb.indicador_analise_grafica;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import br.iesb.indicador_analise_grafica.primary_key.MarteloPK;

@Entity
@Table(name = "MARTELO")
public class Martelo {
	 
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long iD;
	
	@Column(name="tipo")
	private String tipo = "";
	
	@Column(name="pavioSuperior")
	private int pavioSuperior;
	
	@Column(name="pavioInferior")
	private int pavioInferior;

	@Column(name="volumeAcimaMedia20")
	private Boolean volumeAcimaMedia20;
	
	@Column(name="marteloAcimaMedia200")
	private Boolean marteloAcimaMedia200;
	
	@OneToOne
	@JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel")})
	private InfoCandle infoCandle;
	
	@OneToOne
	@JoinColumn(name="padrao")
	private Operacao operacao = null;
	
	public Martelo() {
		
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

	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPavioSuperior() {
		return pavioSuperior;
	}

	public int getPavioInferior() {
		return pavioInferior;
	}

	public Boolean getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public Boolean getMarteloAcimaMedia200() {
		return marteloAcimaMedia200;
	}

	public void setMarteloAcimaMedia200(Boolean marteloAcimaMedia200) {
		this.marteloAcimaMedia200 = marteloAcimaMedia200;
	}

	public void setVolumeAcimaMedia20(Boolean volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	
	public void setPavioSuperior(Double pavioSuperior) {
		this.pavioSuperior = pavioSuperior.intValue();
	}

	public void setPavioInferior(Double pavioInferior) {
		this.pavioInferior = pavioInferior.intValue();
	}

	public InfoCandle getInfoCandle() {
		return infoCandle;
	}

	public void setInfoCandle(InfoCandle infoCandle) {
		this.infoCandle = infoCandle;
	}
}
