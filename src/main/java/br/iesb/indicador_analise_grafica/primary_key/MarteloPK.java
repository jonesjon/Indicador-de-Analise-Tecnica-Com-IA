package br.iesb.indicador_analise_grafica.primary_key;

import java.io.Serializable;
import java.time.LocalDate;

public class MarteloPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate dat;
	private String nomeDoPapel;
	
	public MarteloPK() {
		
	}
	
	public MarteloPK(LocalDate data, String nomeDoPapel) {
		this.dat = data;
		this.nomeDoPapel = nomeDoPapel;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dat == null) ? 0 : dat.hashCode());
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
		MarteloPK other = (MarteloPK) obj;
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
		return true;
	}

}
