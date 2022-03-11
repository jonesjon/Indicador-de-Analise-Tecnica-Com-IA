package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;

@Entity
@Table(name = "DOJI")
@PrimaryKeyJoinColumn(name = "ID")
public class Doji extends Padrao{
	
	@Column
	private int tamanhoPavioCorpo;
	
	@Column
	private int pavioSuperior;
	
	@Column
	private int pavioInferior;
	

	public Doji() {
		
	}
	
	/*public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}*/

	public int getTamanhoPavioCorpo() {
		return tamanhoPavioCorpo;
	}

	public void setTamanhoPavioCorpo(int tamanhoPavioCorpo) {
		this.tamanhoPavioCorpo = tamanhoPavioCorpo;
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
}

