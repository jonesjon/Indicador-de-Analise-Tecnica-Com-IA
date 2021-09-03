package br.iesb.indicador_analise_grafica;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "MARUBOZU")
public class Marubozu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long iD;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name = "variacaoPreco")
	private String variacaoPreco;
	
	@Column(name="pavioSuperior")
	private String pavioSuperior;
	
	@Column(name="pavioInferior")
	private String pavioInferior;
	
	@Column(name="volumeAcimaMedia20")
	private Boolean volumeAcimaMedia20;
	
	@OneToOne
	@JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel")})
	private InfoCandle infoCandle;
	
	@OneToOne
	@JoinColumn(name="padrao")
	private Operacao operacao = null;
	
	public Marubozu() {
		
	}
	
	public Marubozu(String tipo, String pavioSuperior,
			String pavioInferior, Boolean volumeAcimaMedia20, String variacaoPreco, Operacao operacao) {
		
		this.tipo = tipo;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.variacaoPreco = variacaoPreco;
		this.operacao = operacao;
		
	}

	public String getTipo() {
		return tipo;
	}

	public String getPavioSuperior() {
		return pavioSuperior;
	}

	public String getPavioInferior() {
		return pavioInferior;
	}

	public Boolean getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public Operacao getOperacao() {
		return operacao;
	}
	
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public InfoCandle getInfoCandle() {
		return infoCandle;
	}

	public void setInfoCandle(InfoCandle infoCandle) {
		this.infoCandle = infoCandle;
	}
	

}
