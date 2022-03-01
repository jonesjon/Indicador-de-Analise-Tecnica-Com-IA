package br.iesb.indicador_analise_grafica.possibilidades;

import br.iesb.indicador_analise_grafica_enum.PadroesEnum;
import br.iesb.indicador_analise_grafica_enum.PavioInferior;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

public class PossibilidadeTresSoldados {
	
	private PadroesEnum padrao;
	
	private PavioSuperior pavioSuperiorPrimeiroCandle;

	private PavioInferior pavioInferiorPrimeiroCandle;
	
	private PavioSuperior pavioSuperiorTerceiroCandle;

	private PavioInferior pavioInferiorTerceiroCandle;
	
	private PrecoAcimaMedia200 precoAcimaMedia200;
	

	public PossibilidadeTresSoldados(PadroesEnum padrao, PavioSuperior pavioSuperiorPrimeiroCandle,
			PavioInferior pavioInferiorPrimeiroCandle, PavioSuperior pavioSuperiorTerceiroCandle,
			PavioInferior pavioInferiorTerceiroCandle, PrecoAcimaMedia200 precoAcimaMedia200) {
		
		this.padrao = padrao;
		this.pavioSuperiorPrimeiroCandle = pavioSuperiorPrimeiroCandle;
		this.pavioInferiorPrimeiroCandle = pavioInferiorPrimeiroCandle;
		this.pavioSuperiorTerceiroCandle = pavioSuperiorTerceiroCandle;
		this.pavioInferiorTerceiroCandle = pavioInferiorTerceiroCandle;
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	public PavioSuperior getPavioSuperiorPrimeiroCandle() {
		return pavioSuperiorPrimeiroCandle;
	}

	public void setPavioSuperiorPrimeiroCandle(PavioSuperior pavioSuperiorPrimeiroCandle) {
		this.pavioSuperiorPrimeiroCandle = pavioSuperiorPrimeiroCandle;
	}

	public PavioInferior getPavioInferiorPrimeiroCandle() {
		return pavioInferiorPrimeiroCandle;
	}

	public void setPavioInferiorPrimeiroCandle(PavioInferior pavioInferiorPrimeiroCandle) {
		this.pavioInferiorPrimeiroCandle = pavioInferiorPrimeiroCandle;
	}

	public PavioSuperior getPavioSuperiorTerceiroCandle() {
		return pavioSuperiorTerceiroCandle;
	}

	public void setPavioSuperiorTerceiroCandle(PavioSuperior pavioSuperiorTerceiroCandle) {
		this.pavioSuperiorTerceiroCandle = pavioSuperiorTerceiroCandle;
	}

	public PavioInferior getPavioInferiorTerceiroCandle() {
		return pavioInferiorTerceiroCandle;
	}

	public void setPavioInferiorTerceiroCandle(PavioInferior pavioInferiorTerceiroCandle) {
		this.pavioInferiorTerceiroCandle = pavioInferiorTerceiroCandle;
	}

	public PrecoAcimaMedia200 getPrecoAcimaMedia200() {
		return precoAcimaMedia200;
	}

	public void setPrecoAcimaMedia200(PrecoAcimaMedia200 precoAcimaMedia200) {
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	public PadroesEnum getPadrao() {
		return padrao;
	}

	public void setPadrao(PadroesEnum padrao) {
		this.padrao = padrao;
	}
	
	
}
