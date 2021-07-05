package br.iesb.indicador_analise_grafica;

public enum Entrada {
	
	COMPRA("Realizada opera��o de Compra"), VENDA("Realizada opera��o de Venda");
	
	private String descricao;
	
	Entrada(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
