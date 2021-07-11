package br.iesb.indicador_analise_grafica;
import java.time.LocalDate;

import javax.persistence.*;

@Embeddable
public class InfoCandlePK{
	
	private LocalDate data;
	private String nome;
	
	public InfoCandlePK(LocalDate data, String nome) {
		this.data = data;
		this.nome = nome;
	}

	public LocalDate getData() {
		return data;
	}

	public String getNome() {
		return nome;
	}

}
