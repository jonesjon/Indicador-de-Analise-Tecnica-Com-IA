package br.iesb.indicador_analise_grafica;

import javax.persistence.*;

@Entity
@Table(name = "ENGOLFO")
public class Engolfo {
	
	
	@Id
	@Column
	@GeneratedValue
	private long iD;
	
	@Column
	private String tipo;
	
	@Column
	private String pavioSuperior;
	
	@Column
	private String pavioInferior;
	
	@Column
	private Boolean volumeAcimaMedia20;
	
	@Column
	private Boolean acimaMedia8;
	
	@Column
	private Boolean acimaMedia20;
	
	@Column
	private Boolean acimaMedia200;
	
	@Column
	private String variacao;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;
	
	public Engolfo(){
		
	}


	public long getiD() {
		return iD;
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

	public void setPavioSuperior(String pavioSuperior) {
		this.pavioSuperior = pavioSuperior;
	}

	public String getPavioInferior() {
		return pavioInferior;
	}

	public void setPavioInferior(String pavioInferior) {
		this.pavioInferior = pavioInferior;
	}

	public Boolean getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public void setVolumeAcimaMedia20(Boolean volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}

	public Boolean getAcimaMedia8() {
		return acimaMedia8;
	}

	public void setAcimaMedia8(Boolean acimaMedia8) {
		this.acimaMedia8 = acimaMedia8;
	}

	public Boolean getAcimaMedia20() {
		return acimaMedia20;
	}

	public void setAcimaMedia20(Boolean acimaMedia20) {
		this.acimaMedia20 = acimaMedia20;
	}

	public Boolean getAcimaMedia200() {
		return acimaMedia200;
	}

	public void setAcimaMedia200(Boolean acimaMedia200) {
		this.acimaMedia200 = acimaMedia200;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}


	public String getVariacao() {
		return variacao;
	}


	public void setVariacao(String variacao) {
		this.variacao = variacao;
	}
	

}
