package br.iesb.indicador_analise_grafica.primary_key;

import java.io.Serializable;
import java.time.LocalDate;

public class OperacaoPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5695820878657075030L;
	
	private LocalDate dat;
	private String nomeDoPapel;
	private String padrao;
	
	public OperacaoPK() {
		
	}
	
	public OperacaoPK(LocalDate data, String nomeDoPapel, String padrao) {
		this.dat = data;
		this.nomeDoPapel = nomeDoPapel;
		this.setPadrao(padrao);
	}
	
	public LocalDate getData() {
		return dat;
	}
	
	public void setData(LocalDate data) {
		this.dat = data;
	}
	
	public String getNomeDoPapel() {
		return nomeDoPapel;
	}
	
	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}
	

	public String getPadrao() {
		return padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dat == null) ? 0 : dat.hashCode());
		result = prime * result + ((nomeDoPapel == null) ? 0 : nomeDoPapel.hashCode());
		result = prime * result + ((padrao == null) ? 0 : padrao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperacaoPK other = (OperacaoPK) obj;
		if (dat == null) {
			if (other.dat != null)
				return false;
		} else if (!dat.equals(other.dat))
			return false;
		if (nomeDoPapel == null) {
			if (other.nomeDoPapel != null)
				return false;
		} else if (!nomeDoPapel.equals(other.nomeDoPapel))
			return false;
		if (padrao == null) {
			if (other.padrao != null)
				return false;
		} else if (!padrao.equals(other.padrao))
			return false;
		return true;
	}
	
}
