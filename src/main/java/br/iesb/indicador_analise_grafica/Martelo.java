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
	private long iD;
	
	@Column(name="tipo")
	private final String tipo;
	
	@Column(name="pavioSuperior")
	private final String pavioSuperior;
	
	@Column(name="pavioInferior")
	private final String pavioInferior;
	
	@Column(name="volumeAcimaMedia20")
	private Boolean volumeAcimaMedia20;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;
	
	public Martelo() {
		this.tipo = "";
		this.pavioSuperior = "";
		this.pavioInferior = "";
	}

	public Martelo(int iD, String tipo, String pavioSuperior,
			String pavioInferior, Boolean volumeAcimaMedia20, Operacao operacao) {
		this.iD = iD;
		this.tipo = tipo;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
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
}
