package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;

@Entity
@Table(name = "MARUBOZU")
@PrimaryKeyJoinColumn(name = "ID")
public class Marubozu extends Padrao{

	
	@Column(name = "variacaoPreco")
	private String variacaoPreco;
	
	@Column(name="pavioSuperior")
	private String pavioSuperior;
	
	@Column(name="pavioInferior")
	private String pavioInferior;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;
	
	public Marubozu() {
		
	}
	
	public Marubozu(String tipo, String pavioSuperior,
			String pavioInferior, Boolean volumeAcimaMedia20, String variacaoPreco, Operacao operacao) {
		
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.variacaoPreco = variacaoPreco;
		this.operacao = operacao;
		
	}


	public String getPavioSuperior() {
		return pavioSuperior;
	}

	public String getPavioInferior() {
		return pavioInferior;
	}

	public Operacao getOperacao() {
		return operacao;
	}
	
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	

}
