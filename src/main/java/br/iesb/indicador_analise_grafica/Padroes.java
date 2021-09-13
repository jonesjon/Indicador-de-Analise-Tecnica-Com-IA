package br.iesb.indicador_analise_grafica;

public enum Padroes {
	
	MARTELO("Martelo"),
	MARTELOINVERTIDO("Martelo Invertido"),
	MARUBOZU("Marubozu"),
	ENGOLFO("Engolfo"),
	DOJICOMPRA("Doji de Compra"),
	DOJIVENDA("Doji de Venda");
	
	
	private String descricao;
	
	Padroes(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
