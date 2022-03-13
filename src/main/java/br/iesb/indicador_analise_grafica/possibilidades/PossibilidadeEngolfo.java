package br.iesb.indicador_analise_grafica.possibilidades;

import br.iesb.indicador_analise_grafica_enum.PavioInferiorEnum;
import br.iesb.indicador_analise_grafica_enum.PavioSuperiorEnum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8Enum;
import br.iesb.indicador_analise_grafica_enum.TipoCandleEnum;
import br.iesb.indicador_analise_grafica_enum.VariacaoPrecoEnum;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20Enum;

public class PossibilidadeEngolfo {
	
	private TipoCandleEnum tipoCandle;
	private PavioSuperiorEnum pavioSuperior;
	private PavioInferiorEnum pavioInferior;
	private VolumeAcimaMedia20Enum volumeAcimaMedia20;
	private PrecoAcimaMedia8Enum precoAcimaMedia8;
	private PrecoAcimaMedia20Enum precoAcimaMedia20;
	private PrecoAcimaMedia200Enum precoAcimaMedia200;
	private VariacaoPrecoEnum variacao;
	
	public PossibilidadeEngolfo(TipoCandleEnum tipoCandle, PavioSuperiorEnum pavioSuperior, PavioInferiorEnum pavioInferior,
			VolumeAcimaMedia20Enum volumeAcimaMedia20, PrecoAcimaMedia8Enum precoAcimaMedia8,
			PrecoAcimaMedia20Enum precoAcimaMedia20, PrecoAcimaMedia200Enum precoAcimaMedia200, VariacaoPrecoEnum variacao) {
		this.tipoCandle = tipoCandle;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.precoAcimaMedia8 = precoAcimaMedia8;
		this.precoAcimaMedia20 = precoAcimaMedia20;
		this.precoAcimaMedia200 = precoAcimaMedia200;
		this.variacao = variacao;
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

	public VariacaoPrecoEnum getVariacao() {
		return variacao;
	}

	public void setVariacao(VariacaoPrecoEnum variacao) {
		this.variacao = variacao;
	}
	
	

}
