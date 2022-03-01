package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;

@Entity
@Table(name="PIERCING_LINE")
@PrimaryKeyJoinColumn(name = "ID")
public class PiercingLine extends Padrao{
	
	@Column
	private int perfuracao;
	

	public PiercingLine() {
	}

	public int getPerfuracao() {
		return perfuracao;
	}

	public void setPerfuracao(int perfuracao) {
		this.perfuracao = perfuracao;
	}
}
