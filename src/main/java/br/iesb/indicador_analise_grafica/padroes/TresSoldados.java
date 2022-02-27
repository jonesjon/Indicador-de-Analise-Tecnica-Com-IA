package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica_enum.PavioInferior;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;;

@Entity
@Table(name="TRES_SOLDADOS")
@PrimaryKeyJoinColumn(name = "ID")
public class TresSoldados extends Padrao {
	
	@Column
	private String pavioSuperiorPrimeiroCandle;
	
	@Column
	private String pavioInferiorPrimeiroCandle;
	
	@Column
	private String pavioSuperiorSegundoCandle;
	
	@Column
	private String pavioInferiorSegundoCandle;
	
	@Column
	private String pavioSuperiorTerceiroCandle;
	
	@Column
	private String pavioInferiorTerceiroCandle;
	
	public TresSoldados() {
		
	}

	public String getPavioSuperiorPrimeiroCandle() {
		return pavioSuperiorPrimeiroCandle;
	}

	public void setPavioSuperiorPrimeiroCandle(String pavioSuperiorPrimeiroCandle) {
		this.pavioSuperiorPrimeiroCandle = pavioSuperiorPrimeiroCandle;
	}

	public String getPavioInferiorPrimeiroCandle() {
		return pavioInferiorPrimeiroCandle;
	}

	public void setPavioInferiorPrimeiroCandle(String pavioInferiorPrimeiroCandle) {
		this.pavioInferiorPrimeiroCandle = pavioInferiorPrimeiroCandle;
	}

	public String getPavioSuperiorSegundoCandle() {
		return pavioSuperiorSegundoCandle;
	}

	public void setPavioSuperiorSegundoCandle(String pavioSuperiorSegundoCandle) {
		this.pavioSuperiorSegundoCandle = pavioSuperiorSegundoCandle;
	}

	public String getPavioInferiorSegundoCandle() {
		return pavioInferiorSegundoCandle;
	}

	public void setPavioInferiorSegundoCandle(String pavioInferiorSegundoCandle) {
		this.pavioInferiorSegundoCandle = pavioInferiorSegundoCandle;
	}

	public String getPavioSuperiorTerceiroCandle() {
		return pavioSuperiorTerceiroCandle;
	}

	public void setPavioSuperiorTerceiroCandle(String pavioSuperiorTerceiroCandle) {
		this.pavioSuperiorTerceiroCandle = pavioSuperiorTerceiroCandle;
	}

	public String getPavioInferiorTerceiroCandle() {
		return pavioInferiorTerceiroCandle;
	}

	public void setPavioInferiorTerceiroCandle(String pavioInferiorTerceiroCandle) {
		this.pavioInferiorTerceiroCandle = pavioInferiorTerceiroCandle;
	}
	
	
}
