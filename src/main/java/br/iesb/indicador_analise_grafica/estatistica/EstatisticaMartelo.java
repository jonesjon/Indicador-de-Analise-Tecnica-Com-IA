package br.iesb.indicador_analise_grafica.estatistica;

import br.iesb.indicador_analise_grafica.PossibilidadeMartelo;

public class EstatisticaMartelo {
	
	private PossibilidadeMartelo configuracaoMartelo;
	
	private int frequenciaGeral;
	
	private int frequenciaUltimoAno;
	
	private int frequenciaUltimosCincoAnos;
	
	private int assertividadeGeral;
	
	private int assertividadeUltimoAno;
	
	private int assertividadeUltimosCincoAnos;

	public EstatisticaMartelo() {
		
	}

	public PossibilidadeMartelo getConfiguracaoMartelo() {
		return configuracaoMartelo;
	}

	public void setConfiguracaoMartelo(PossibilidadeMartelo configuracaoMartelo) {
		this.configuracaoMartelo = configuracaoMartelo;
	}

	public int getFrequenciaGeral() {
		return frequenciaGeral;
	}

	public void setFrequenciaGeral(int frequenciaGeral) {
		this.frequenciaGeral = frequenciaGeral;
	}

	public int getFrequenciaUltimoAno() {
		return frequenciaUltimoAno;
	}

	public void setFrequenciaUltimoAno(int frequenciaUltimoAno) {
		this.frequenciaUltimoAno = frequenciaUltimoAno;
	}

	public int getFrequenciaUltimosCincoAnos() {
		return frequenciaUltimosCincoAnos;
	}

	public void setFrequenciaUltimosCincoAnos(int frequenciaUltimosCincoAnos) {
		this.frequenciaUltimosCincoAnos = frequenciaUltimosCincoAnos;
	}

	public int getAssertividadeGeral() {
		return assertividadeGeral;
	}

	public void setAssertividadeGeral(int assertividadeGeral) {
		this.assertividadeGeral = assertividadeGeral;
	}

	public int getAssertividadeUltimoAno() {
		return assertividadeUltimoAno;
	}

	public void setAssertividadeUltimoAno(int assertividadeUltimoAno) {
		this.assertividadeUltimoAno = assertividadeUltimoAno;
	}

	public int getAssertividadeUltimosCincoAnos() {
		return assertividadeUltimosCincoAnos;
	}

	public void setAssertividadeUltimosCincoAnos(int assertividadeUltimosCincoAnos) {
		this.assertividadeUltimosCincoAnos = assertividadeUltimosCincoAnos;
	}
	

}
