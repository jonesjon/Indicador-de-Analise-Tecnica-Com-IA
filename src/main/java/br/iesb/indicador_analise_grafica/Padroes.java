package br.iesb.indicador_analise_grafica;

public enum Padroes {
	
	MARTELO("Martelo");
	
	private String descricao;
	
	Padroes(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
