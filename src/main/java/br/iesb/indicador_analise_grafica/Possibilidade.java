package br.iesb.indicador_analise_grafica;

public class Possibilidade {
	
	TipoCandle tipoCandle;
	PavioSuperior pavioSuperior;
	PavioInferior pavioInferior;
	VolumeAcimaMedia20 volumeAcimaMedia20;
	PrecoAcimaMedia200 precoAcimaMedia200;
	
	
	public Possibilidade(TipoCandle tipoCandle, PavioSuperior pavioSuperior, PavioInferior pavioInferior,
			VolumeAcimaMedia20 volumeAcimaMedia20, PrecoAcimaMedia200 precoAcimaMedia200) {
		this.tipoCandle = tipoCandle;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}
	
	

}
