package br.iesb.indicador_analise_grafica.possibilidades;

import br.iesb.indicador_analise_grafica_enum.PavioInferiorEnum;
import br.iesb.indicador_analise_grafica_enum.PavioSuperiorEnum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200Enum;
import br.iesb.indicador_analise_grafica_enum.TipoCandleEnum;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20Enum;

public class PossibilidadeMartelo {
	
	private TipoCandleEnum tipoCandle;
	private PavioSuperiorEnum pavioSuperior;
	private PavioInferiorEnum pavioInferior;
	private VolumeAcimaMedia20Enum volumeAcimaMedia20;
	private PrecoAcimaMedia200Enum precoAcimaMedia200;
	
	
	public PossibilidadeMartelo(TipoCandleEnum tipoCandle, PavioSuperiorEnum pavioSuperior, PavioInferiorEnum pavioInferior,
			VolumeAcimaMedia20Enum volumeAcimaMedia20, PrecoAcimaMedia200Enum precoAcimaMedia200) {
		this.tipoCandle = tipoCandle;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}


	public TipoCandleEnum getTipoCandle() {
		return tipoCandle;
	}


	public void setTipoCandle(TipoCandleEnum tipoCandle) {
		this.tipoCandle = tipoCandle;
	}


	public PavioSuperiorEnum getPavioSuperior() {
		return pavioSuperior;
	}


	public void setPavioSuperior(PavioSuperiorEnum pavioSuperior) {
		this.pavioSuperior = pavioSuperior;
	}


	public PavioInferiorEnum getPavioInferior() {
		return pavioInferior;
	}


	public void setPavioInferior(PavioInferiorEnum pavioInferior) {
		this.pavioInferior = pavioInferior;
	}


	public VolumeAcimaMedia20Enum getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}


	public void setVolumeAcimaMedia20(VolumeAcimaMedia20Enum volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}


	public PrecoAcimaMedia200Enum getPrecoAcimaMedia200() {
		return precoAcimaMedia200;
	}


	public void setPrecoAcimaMedia200(PrecoAcimaMedia200Enum precoAcimaMedia200) {
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}
	
	

}
