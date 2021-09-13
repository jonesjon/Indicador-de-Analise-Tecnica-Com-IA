package br.iesb.indicador_analise_grafica;

import javax.persistence.*;

@Entity
@Table(name = "DOJI")
public class Doji {
	
	@Id
	@Column
	@GeneratedValue
	private long iD;
	
	@Column
	private String tipo;
	
	@Column
	private int tamanhoPavioCorpo;
	
	@Column
	private Boolean volumeAcimaMedia20;
	
	@Column
	private int pavioSuperior;
	
	@Column
	private int pavioInferior;
	
	@Column
	private Boolean precoAcimaMedia8;
	
	@Column
	private Boolean precoAcimaMedia20;
	
	@Column
	private Boolean precoAcimaMedia200;
	
	@OneToOne
	@JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;

	public Doji() {
		
	}
	
	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTamanhoPavioCorpo() {
		return tamanhoPavioCorpo;
	}

	public void setTamanhoPavioCorpo(int tamanhoPavioCorpo) {
		this.tamanhoPavioCorpo = tamanhoPavioCorpo;
	}

	public Boolean getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public void setVolumeAcimaMedia20(Boolean volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}

	public int getPavioSuperior() {
		return pavioSuperior;
	}

	public void setPavioSuperior(int pavioSuperior) {
		this.pavioSuperior = pavioSuperior;
	}

	public int getPavioInferior() {
		return pavioInferior;
	}

	public void setPavioInferior(int pavioInferior) {
		this.pavioInferior = pavioInferior;
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

}

