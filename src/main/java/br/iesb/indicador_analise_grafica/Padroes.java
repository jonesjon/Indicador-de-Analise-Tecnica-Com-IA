package br.iesb.indicador_analise_grafica;

public enum Padroes {
	
	MARTELO("Martelo"),
	MARTELOINVERTIDO("Martelo Invertido"),
	MARUBOZU("Marubozu"),
	ENGOLFO("Engolfo"),
	PIERCINGLINE("Piercing Line"),
	DARKCLOUD("Dark Cloud"),
	DOJICOMPRA("Doji de Compra"),
	TRESSOLDADOSDEALTA("Tres Soldados de Alta"),
	TRESSOLDADOSDEBAIXA("Tres soldados de Baixa"),
	DOJIVENDA("Doji de Venda");
	
	
	private String descricao;
	
	Padroes(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
