package br.iesb.indicador_analise_grafica;

import br.iesb.indicador_analise_grafica_enum.PavioInferior;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.TipoCandle;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

public class PossibilidadeMartelo {
	
	TipoCandle tipoCandle;
	PavioSuperior pavioSuperior;
	PavioInferior pavioInferior;
	VolumeAcimaMedia20 volumeAcimaMedia20;
	PrecoAcimaMedia200 precoAcimaMedia200;
	
	
	public PossibilidadeMartelo(TipoCandle tipoCandle, PavioSuperior pavioSuperior, PavioInferior pavioInferior,
			VolumeAcimaMedia20 volumeAcimaMedia20, PrecoAcimaMedia200 precoAcimaMedia200) {
		this.tipoCandle = tipoCandle;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}
	
	

}
