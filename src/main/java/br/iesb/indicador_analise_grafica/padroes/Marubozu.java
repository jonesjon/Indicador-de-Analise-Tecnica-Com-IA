package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica_enum.PavioSuperiorEnum;
import lombok.Data;

@Entity
@Table(name = "MARUBOZU")
@PrimaryKeyJoinColumn(name = "padrao_pai_id")
@Data
public class Marubozu extends Padrao{

	
	@Column(name = "variacaoPreco")
	private String variacaoPreco;
	
	@Column(name="pavioSuperior")
	private String pavioSuperior;
	
	@Column(name="pavioInferior")
	private String pavioInferior;
	
	public Marubozu() {
		
	}
	
	public Marubozu(String tipo, String pavioSuperior,
			String pavioInferior, Boolean volumeAcimaMedia20, String variacaoPreco) {
		
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.variacaoPreco = variacaoPreco;
		this.setVolumeAcimaMedia20(volumeAcimaMedia20);
		
	}


	public String getPavioSuperior() {
		return pavioSuperior;
	}

	public String getPavioInferior() {
		return pavioInferior;
	}	

}
