package br.iesb.indicador_analise_grafica.primary_key;

import java.io.Serializable;
import java.time.LocalDate;

public class MarteloPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate dats;
	private String nomePapel;
	
	public MarteloPK() {
		
	}
	
	public MarteloPK(LocalDate data, String nomeDoPapel) {
		this.dats = data;
		this.nomePapel = nomeDoPapel;
	}
	
	public LocalDate getData() {
		return dats;
	}
	
	public void setData(LocalDate data) {
		this.dats = data;
	}
	
	public String getNomeDoPapel() {
		return nomePapel;
	}
	
	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomePapel = nomeDoPapel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dats == null) ? 0 : dats.hashCode());
		result = prime * result + ((nomePapel == null) ? 0 : nomePapel.hashCode());
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
		if (dats == null) {
			if (other.dats != null)
				return false;
		} else if (!dats.equals(other.dats))
			return false;
		if (nomePapel == null) {
			if (other.nomePapel != null)
				return false;
		} else if (!nomePapel.equals(other.nomePapel))
			return false;
		return true;
	}

}
