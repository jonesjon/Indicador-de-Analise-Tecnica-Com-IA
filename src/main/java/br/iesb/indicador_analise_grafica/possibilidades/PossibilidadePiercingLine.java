package br.iesb.indicador_analise_grafica.possibilidades;

import br.iesb.indicador_analise_grafica_enum.Perfuracao;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8;
import br.iesb.indicador_analise_grafica_enum.TipoCandle;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

public class PossibilidadePiercingLine {

	private TipoCandle tipoCandle;
	private VolumeAcimaMedia20 volumeAcimaMedia20;
	private Perfuracao perfuracao;
	private PrecoAcimaMedia8 precoAcimaMedia8;
	private PrecoAcimaMedia20 precoAcimaMedia20;
	private PrecoAcimaMedia200 precoAcimaMedia200;
	
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

	public TipoCandle getTipoCandle() {
		return tipoCandle;
	}

	public void setTipoCandle(TipoCandle tipoCandle) {
		this.tipoCandle = tipoCandle;
	}

	public VolumeAcimaMedia20 getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public void setVolumeAcimaMedia20(VolumeAcimaMedia20 volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}

	public Perfuracao getPerfuracao() {
		return perfuracao;
	}

	public void setPerfuracao(Perfuracao perfuracao) {
		this.perfuracao = perfuracao;
	}

	public PrecoAcimaMedia8 getPrecoAcimaMedia8() {
		return precoAcimaMedia8;
	}

	public void setPrecoAcimaMedia8(PrecoAcimaMedia8 precoAcimaMedia8) {
		this.precoAcimaMedia8 = precoAcimaMedia8;
	}

	public PrecoAcimaMedia20 getPrecoAcimaMedia20() {
		return precoAcimaMedia20;
	}

	public void setPrecoAcimaMedia20(PrecoAcimaMedia20 precoAcimaMedia20) {
		this.precoAcimaMedia20 = precoAcimaMedia20;
	}

	public PrecoAcimaMedia200 getPrecoAcimaMedia200() {
		return precoAcimaMedia200;
	}

	public void setPrecoAcimaMedia200(PrecoAcimaMedia200 precoAcimaMedia200) {
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	

}
