package br.iesb.indicador_analise_grafica.primary_key;

import java.io.Serializable;
import java.time.LocalDate;

public class OperacaoPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5695820878657075030L;
	
	private LocalDate data;
	private String nomeDoPapel;
	
	public OperacaoPK() {
		
	}
	
	public OperacaoPK(LocalDate data, String nomeDoPapel) {
		this.data = data;
		this.nomeDoPapel = nomeDoPapel;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getNomeDoPapel() {
		return nomeDoPapel;
	}
	
	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((nomeDoPapel == null) ? 0 : nomeDoPapel.hashCode());
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
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (nomeDoPapel == null) {
			if (other.nomeDoPapel != null)
				return false;
		} else if (!nomeDoPapel.equals(other.nomeDoPapel))
			return false;
		return true;
	}

}
