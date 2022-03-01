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

	public MarteloInvertido() {
		
	}

	public String getPavioSuperior() {
		return pavioSuperior;
	}

	public String getPavioInferior() {
		return pavioInferior;
	}
	
	public void setPavioSuperior(String pavioSuperior) {
		this.pavioSuperior = pavioSuperior;
	}

	public void setPavioInferior(String pavioInferior) {
		this.pavioInferior = pavioInferior;
	}
}
