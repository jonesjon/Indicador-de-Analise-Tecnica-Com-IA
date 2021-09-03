package br.iesb.indicador_analise_grafica;

public enum Entrada {
	
	COMPRA("Compra"), VENDA("Venda"), INDEFINIDO("Indefinido");
	
	private String descricao;
	
	Entrada(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
