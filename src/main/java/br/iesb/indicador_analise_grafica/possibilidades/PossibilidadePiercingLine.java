package br.iesb.indicador_analise_grafica.possibilidades;

import br.iesb.indicador_analise_grafica_enum.PerfuracaoEnum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8Enum;
import br.iesb.indicador_analise_grafica_enum.TipoCandleEnum;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20Enum;

public class PossibilidadePiercingLine {

	private TipoCandleEnum tipoCandle;
	private VolumeAcimaMedia20Enum volumeAcimaMedia20;
	private PerfuracaoEnum perfuracao;
	private PrecoAcimaMedia8Enum precoAcimaMedia8;
	private PrecoAcimaMedia20Enum precoAcimaMedia20;
	private PrecoAcimaMedia200Enum precoAcimaMedia200;
	
	public PossibilidadePiercingLine(TipoCandleEnum tipoCandle, VolumeAcimaMedia20Enum volumeAcimaMedia20,
			PerfuracaoEnum perfuracao, PrecoAcimaMedia8Enum precoAcimaMedia8, PrecoAcimaMedia20Enum precoAcimaMedia20,
			PrecoAcimaMedia200Enum precoAcimaMedia200) {
		this.tipoCandle = tipoCandle;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.perfuracao = perfuracao;
		this.precoAcimaMedia8 = precoAcimaMedia8;
		this.precoAcimaMedia20 = precoAcimaMedia20;
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	public TipoCandleEnum getTipoCandle() {
		return tipoCandle;
	}

	public void setTipoCandle(TipoCandleEnum tipoCandle) {
		this.tipoCandle = tipoCandle;
	}

	public VolumeAcimaMedia20Enum getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public void setVolumeAcimaMedia20(VolumeAcimaMedia20Enum volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}

	public PerfuracaoEnum getPerfuracao() {
		return perfuracao;
	}

	public void setPerfuracao(PerfuracaoEnum perfuracao) {
		this.perfuracao = perfuracao;
	}

	public PrecoAcimaMedia8Enum getPrecoAcimaMedia8() {
		return precoAcimaMedia8;
	}

	public void setPrecoAcimaMedia8(PrecoAcimaMedia8Enum precoAcimaMedia8) {
		this.precoAcimaMedia8 = precoAcimaMedia8;
	}

	public PrecoAcimaMedia20Enum getPrecoAcimaMedia20() {
		return precoAcimaMedia20;
	}

	public void setPrecoAcimaMedia20(PrecoAcimaMedia20Enum precoAcimaMedia20) {
		this.precoAcimaMedia20 = precoAcimaMedia20;
	}

	public PrecoAcimaMedia200Enum getPrecoAcimaMedia200() {
		return precoAcimaMedia200;
	}

	public void setPrecoAcimaMedia200(PrecoAcimaMedia200Enum precoAcimaMedia200) {
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	

}
