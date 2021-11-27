package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;

@Entity
@Table(name="PIERCING_LINE")
@PrimaryKeyJoinColumn(name = "ID")
public class PiercingLine extends Padrao{
	
	@Column
	private int perfuracao;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;

	public PiercingLine() {
	}

	public int getPerfuracao() {
		return perfuracao;
	}

	public void setPerfuracao(int perfuracao) {
		this.perfuracao = perfuracao;
	}
	
	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

}
