package br.iesb.indicador_analise_grafica;

public enum Padroes {
	
	MARTELO("Martelo"),
	MARUBOZU("Marubozu"),
	ENGOLFO("Engolfo"),
	DOJI("Doji");
	
	
	
	private String descricao;
	
	Padroes(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
