package br.iesb.indicador_analise_grafica;

import javax.persistence.*;

@Entity
public class PapeisOperaveis {
	
	@Id
	@Column(name = "papel")
	private String nomeDoPapel;
	
	public PapeisOperaveis() {
	}

	public PapeisOperaveis(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
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
		PapeisOperaveis other = (PapeisOperaveis) obj;
		if (nomeDoPapel == null) {
			if (other.nomeDoPapel != null)
				return false;
		} else if (!nomeDoPapel.equals(other.nomeDoPapel))
			return false;
		return true;
	}
	
	

}
