package br.iesb.indicador_analise_grafica.primary_key;
import java.io.Serializable;
import java.time.LocalDate;


public class InfoCandlePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDate data;
	private String nomeDoPapel;
	

	public InfoCandlePK() {
		
	}
	
	public InfoCandlePK(LocalDate data, String nome) {
		this.data = data;
		this.nomeDoPapel = nome;
	}

	public LocalDate getData() {
		return data;
	}

	public String getNomeDoPapel() {
		return nomeDoPapel;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}

}
