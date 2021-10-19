package br.iesb.indicador_analise_grafica_enum;

public enum VariacaoPreco {

	ATE2VEZES("Variacao de ate duas vezes"),
	DE2A3VEZES("Variacao de duas a tres vezes"),
	DE3A4VEZES("Variacao de tres a quatro vezes"),
	DE4A5VEZES("Variacao de quatro a cinco vezes"),
	MAIORQUE5VEZES("Variacao maior de cinco vezes"),
	NULL("null");
	
	private String descricao;
	
	VariacaoPreco(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
