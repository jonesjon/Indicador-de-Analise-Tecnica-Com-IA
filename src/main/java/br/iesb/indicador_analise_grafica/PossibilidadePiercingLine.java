package br.iesb.indicador_analise_grafica;

import br.iesb.indicador_analise_grafica_enum.Perfuracao;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8;
import br.iesb.indicador_analise_grafica_enum.TipoCandle;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

public class PossibilidadePiercingLine {

	TipoCandle tipoCandle;
	VolumeAcimaMedia20 volumeAcimaMedia20;
	Perfuracao perfuracao;
	PrecoAcimaMedia8 precoAcimaMedia8;
	PrecoAcimaMedia20 precoAcimaMedia20;
	PrecoAcimaMedia200 precoAcimaMedia200;
	
	public PossibilidadePiercingLine(TipoCandle tipoCandle, VolumeAcimaMedia20 volumeAcimaMedia20,
			Perfuracao perfuracao, PrecoAcimaMedia8 precoAcimaMedia8, PrecoAcimaMedia20 precoAcimaMedia20,
			PrecoAcimaMedia200 precoAcimaMedia200) {
		this.tipoCandle = tipoCandle;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.perfuracao = perfuracao;
		this.precoAcimaMedia8 = precoAcimaMedia8;
		this.precoAcimaMedia20 = precoAcimaMedia20;
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	

}
