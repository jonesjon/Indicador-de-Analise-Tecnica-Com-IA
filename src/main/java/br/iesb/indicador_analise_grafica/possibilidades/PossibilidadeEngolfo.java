package br.iesb.indicador_analise_grafica.possibilidades;

import br.iesb.indicador_analise_grafica_enum.PavioInferior;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8;
import br.iesb.indicador_analise_grafica_enum.TipoCandle;
import br.iesb.indicador_analise_grafica_enum.VariacaoPreco;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

public class PossibilidadeEngolfo {
	
	private TipoCandle tipoCandle;
	private PavioSuperior pavioSuperior;
	private PavioInferior pavioInferior;
	private VolumeAcimaMedia20 volumeAcimaMedia20;
	private PrecoAcimaMedia8 precoAcimaMedia8;
	private PrecoAcimaMedia20 precoAcimaMedia20;
	private PrecoAcimaMedia200 precoAcimaMedia200;
	private VariacaoPreco variacao;
	
	public PossibilidadeEngolfo(TipoCandle tipoCandle, PavioSuperior pavioSuperior, PavioInferior pavioInferior,
			VolumeAcimaMedia20 volumeAcimaMedia20, PrecoAcimaMedia8 precoAcimaMedia8,
			PrecoAcimaMedia20 precoAcimaMedia20, PrecoAcimaMedia200 precoAcimaMedia200, VariacaoPreco variacao) {
		this.tipoCandle = tipoCandle;
		this.pavioSuperior = pavioSuperior;
		this.pavioInferior = pavioInferior;
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
		this.precoAcimaMedia8 = precoAcimaMedia8;
		this.precoAcimaMedia20 = precoAcimaMedia20;
		this.precoAcimaMedia200 = precoAcimaMedia200;
		this.variacao = variacao;
	}

	public TipoCandle getTipoCandle() {
		return tipoCandle;
	}

	public void setTipoCandle(TipoCandle tipoCandle) {
		this.tipoCandle = tipoCandle;
	}

	public PavioSuperior getPavioSuperior() {
		return pavioSuperior;
	}

	public void setPavioSuperior(PavioSuperior pavioSuperior) {
		this.pavioSuperior = pavioSuperior;
	}

	public PavioInferior getPavioInferior() {
		return pavioInferior;
	}

	public void setPavioInferior(PavioInferior pavioInferior) {
		this.pavioInferior = pavioInferior;
	}

	public VolumeAcimaMedia20 getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public void setVolumeAcimaMedia20(VolumeAcimaMedia20 volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
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

	public VariacaoPreco getVariacao() {
		return variacao;
	}

	public void setVariacao(VariacaoPreco variacao) {
		this.variacao = variacao;
	}
	
	

}
