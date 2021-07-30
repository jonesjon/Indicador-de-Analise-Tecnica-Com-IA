package br.iesb.indicador_analise_grafica;

public enum Entrada {
	
	COMPRA("Compra"), VENDA("Venda");
	
	private String descricao;
	
	Entrada(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
