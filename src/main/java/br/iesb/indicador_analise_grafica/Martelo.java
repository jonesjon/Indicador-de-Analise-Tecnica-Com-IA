package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import br.iesb.indicador_analise_grafica.primary_key.MarteloPK;

@Entity
@Table(name = "MARTELO")
public class Martelo {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long iD;
	
	@Column(name="tipo")
	private String tipo = "";
	
	@Column(name="pavioSuperior")
	private String pavioSuperior = "";
	
	@Column(name="pavioInferior")
	private String pavioInferior = "";

	@Column(name="volumeAcimaMedia20")
	private Boolean volumeAcimaMedia20;
	
	@Column(name="marteloAcimaMedia200")
	private Boolean marteloAcimaMedia200;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;
	
	public Martelo() {
		
	}

	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	
	public void setPavioSuperior(String pavioSuperior) {
		this.pavioSuperior = pavioSuperior;
	}

	public void setPavioInferior(String pavioInferior) {
		this.pavioInferior = pavioInferior;
	}
}
