package br.iesb.indicador_analise_grafica.padroes;
import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;

@Entity
@Table(name = "MARTELO_INVERTIDO")
@PrimaryKeyJoinColumn(name = "ID")
public class MarteloInvertido extends Padrao {
	
	@Column(name="pavioSuperior")
	private String pavioSuperior = "";
	
	@Column(name="pavioInferior")
	private String pavioInferior = "";
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;
	
	public MarteloInvertido() {
		
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
	
	public void setPavioSuperior(String pavioSuperior) {
		this.pavioSuperior = pavioSuperior;
	}

	public void setPavioInferior(String pavioInferior) {
		this.pavioInferior = pavioInferior;
	}
}
