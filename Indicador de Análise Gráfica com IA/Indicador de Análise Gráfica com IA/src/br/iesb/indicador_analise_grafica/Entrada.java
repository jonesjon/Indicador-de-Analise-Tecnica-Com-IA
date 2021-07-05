package br.iesb.indicador_analise_grafica;

public enum Entrada {
	
	COMPRA("Realizada operação de Compra"), VENDA("Realizada operação de Venda");
	
	private String descricao;
	
	Entrada(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
