package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;


@PrimaryKeyJoinColumn(name = "ID")
@Entity
@Table(name = "ENGOLFO")
public class Engolfo extends Padrao{
	
	@Column
	private String pavioSuperior;
	
	@Column
	private String pavioInferior;
	
	@Column
	private String variacao;
	
	public Engolfo(){
		
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

	/*public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}*/


	public String getVariacao() {
		return variacao;
	}


	public void setVariacao(String variacao) {
		this.variacao = variacao;
	}
	

}
