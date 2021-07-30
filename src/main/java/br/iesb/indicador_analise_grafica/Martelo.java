package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import br.iesb.indicador_analise_grafica.primary_key.MarteloPK;

@Entity
@Table(name = "MARTELO")
@IdClass(MarteloPK.class)
public class Martelo {

	@Id
	@Column(name="dats")
	private LocalDate dats;
	
	@Id
	@Column(name="nomePapel")
	private String nomePapel;
	
	@Column(name="tipo")
	private final String tipo;
	
	@Column(name="pavioSuperior")
	private final String pavioSuperior;
	
	@Column(name="pavioInferior")
	private final String pavioInferior;
	
	@Column(name="volumeAcimaMedia20")
	private Boolean volumeAcimaMedia20;
	
	@OneToOne(mappedBy = "martelo")
	private Operacao operacao;
	
	public Martelo() {
		this.dats = null;
		this.tipo = "";
		this.pavioSuperior = "";
		this.pavioInferior = "";
	}

	public Martelo(LocalDate data, String tipo, String pavioSuperior,
			String pavioInferior, Boolean volumeAcimaMedia20, Operacao operacao) {
		this.dats = data;
		this.tipo = tipo;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.operacao = operacao;
		this.nomePapel = operacao.getNomeDoPapel();
	}

	public LocalDate getData() {
		return dats;
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

	public String getNomeDoPapel() {
		return nomePapel;
	}

}
