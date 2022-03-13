package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica_enum.EntradaEnum;
import br.iesb.indicador_analise_grafica_enum.PadroesEnum;
import lombok.Data;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "PADRAO")
@Data
public abstract class Padrao {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name="tipo")
	private String tipo;
	
	@Column
	private Boolean precoAcimaMedia8;
	
	@Column
	private Boolean precoAcimaMedia20;
	
	@Column
	private Boolean precoAcimaMedia200;
	
	@Column
	private Boolean volumeAcimaMedia20;
	

	@JsonIgnore
	@OneToOne(mappedBy = "padrao")
	private Operacao operacao;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getPrecoAcimaMedia8() {
		return precoAcimaMedia8;
	}

	public void setPrecoAcimaMedia8(Boolean precoAcimaMedia8) {
		this.precoAcimaMedia8 = precoAcimaMedia8;
	}

	public Boolean getPrecoAcimaMedia20() {
		return precoAcimaMedia20;
	}

	public void setPrecoAcimaMedia20(Boolean precoAcimaMedia20) {
		this.precoAcimaMedia20 = precoAcimaMedia20;
	}

	public Boolean getPrecoAcimaMedia200() {
		return precoAcimaMedia200;
	}

	public void setPrecoAcimaMedia200(Boolean precoAcimaMedia200) {
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	public Boolean getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public void setVolumeAcimaMedia20(Boolean volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Operacao getOperacao() {
		return operacao;
	}
	
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

}
