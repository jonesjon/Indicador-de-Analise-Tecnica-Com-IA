package br.iesb.indicador_analise_grafica.possibilidades;

import br.iesb.indicador_analise_grafica_enum.PadroesEnum;
import br.iesb.indicador_analise_grafica_enum.PavioInferiorEnum;
import br.iesb.indicador_analise_grafica_enum.PavioSuperiorEnum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8Enum;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20Enum;

public class PossibilidadeTresSoldados {
	
	private PadroesEnum padrao;
	
	private PavioSuperiorEnum pavioSuperiorPrimeiroCandle;

	private PavioInferiorEnum pavioInferiorPrimeiroCandle;
	
	private PavioSuperiorEnum pavioSuperiorTerceiroCandle;

	private PavioInferiorEnum pavioInferiorTerceiroCandle;
	
	private PrecoAcimaMedia200Enum precoAcimaMedia200;
	

	public PossibilidadeTresSoldados(PadroesEnum padrao, PavioSuperiorEnum pavioSuperiorPrimeiroCandle,
			PavioInferiorEnum pavioInferiorPrimeiroCandle, PavioSuperiorEnum pavioSuperiorTerceiroCandle,
			PavioInferiorEnum pavioInferiorTerceiroCandle, PrecoAcimaMedia200Enum precoAcimaMedia200) {
		
		this.padrao = padrao;
		this.pavioSuperiorPrimeiroCandle = pavioSuperiorPrimeiroCandle;
		this.pavioInferiorPrimeiroCandle = pavioInferiorPrimeiroCandle;
		this.pavioSuperiorTerceiroCandle = pavioSuperiorTerceiroCandle;
		this.pavioInferiorTerceiroCandle = pavioInferiorTerceiroCandle;
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	public PavioSuperiorEnum getPavioSuperiorPrimeiroCandle() {
		return pavioSuperiorPrimeiroCandle;
	}

	public void setPavioSuperiorPrimeiroCandle(PavioSuperiorEnum pavioSuperiorPrimeiroCandle) {
		this.pavioSuperiorPrimeiroCandle = pavioSuperiorPrimeiroCandle;
	}

	public PavioInferiorEnum getPavioInferiorPrimeiroCandle() {
		return pavioInferiorPrimeiroCandle;
	}

	public void setPavioInferiorPrimeiroCandle(PavioInferiorEnum pavioInferiorPrimeiroCandle) {
		this.pavioInferiorPrimeiroCandle = pavioInferiorPrimeiroCandle;
	}

	public PavioSuperiorEnum getPavioSuperiorTerceiroCandle() {
		return pavioSuperiorTerceiroCandle;
	}

	public void setPavioSuperiorTerceiroCandle(PavioSuperiorEnum pavioSuperiorTerceiroCandle) {
		this.pavioSuperiorTerceiroCandle = pavioSuperiorTerceiroCandle;
	}

	public PavioInferiorEnum getPavioInferiorTerceiroCandle() {
		return pavioInferiorTerceiroCandle;
	}

	public void setPavioInferiorTerceiroCandle(PavioInferiorEnum pavioInferiorTerceiroCandle) {
		this.pavioInferiorTerceiroCandle = pavioInferiorTerceiroCandle;
	}

	public PrecoAcimaMedia200Enum getPrecoAcimaMedia200() {
		return precoAcimaMedia200;
	}

	public void setPrecoAcimaMedia200(PrecoAcimaMedia200Enum precoAcimaMedia200) {
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	public PadroesEnum getPadrao() {
		return padrao;
	}

	public void setPadrao(PadroesEnum padrao) {
		this.padrao = padrao;
	}
	
	
}
