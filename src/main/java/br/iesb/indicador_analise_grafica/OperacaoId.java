package br.iesb.indicador_analise_grafica;

import java.io.Serializable;
import java.time.LocalDate;

import br.iesb.indicador_analise_grafica_enum.PadroesEnum;

public class OperacaoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 626734219115263250L;

	private LocalDate dat;
	
	private String nomeDoPapel;
	
	private PadroesEnum padraoEnum;
	
	public LocalDate getDat() {
		return dat;
	}

	public void setDat(LocalDate dat) {
		this.dat = dat;
	}

	public String getNomeDoPapel() {
		return nomeDoPapel;
	}

	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}

	public PadroesEnum getPadraoEnum() {
		return padraoEnum;
	}

	public void setPadraoEnum(PadroesEnum padraoEnum) {
		this.padraoEnum = padraoEnum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}