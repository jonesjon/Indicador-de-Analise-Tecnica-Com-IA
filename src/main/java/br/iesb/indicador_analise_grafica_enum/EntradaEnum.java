package br.iesb.indicador_analise_grafica_enum;

public enum EntradaEnum {
	
	COMPRA("Compra"), VENDA("Venda");
	
	private String descricao;
	
	EntradaEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
