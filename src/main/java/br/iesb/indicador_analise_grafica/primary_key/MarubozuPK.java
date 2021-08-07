package br.iesb.indicador_analise_grafica.primary_key;

import java.io.Serializable;

public class MarubozuPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long iD;

	public MarubozuPK() {
		
	}

	public MarubozuPK(long iD) {
		super();
		this.iD = iD;
	}

	public long getiD() {
		return iD;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (iD ^ (iD >>> 32));
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
		MarubozuPK other = (MarubozuPK) obj;
		if (iD != other.iD)
			return false;
		return true;
	}

	

}
