package br.iesb.indicador_analise_grafica_enum;

import java.util.Arrays;
import java.util.List;

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
	DOJIVENDA("Doji de Venda"),
	BEBEABANDONADODEALTA("Bebe Abandonado de Alta"),
	BEBEABANDONADODEBAIXA("Bebe Abandonado de Baixa"),
	NULL("Null");
	
	
	private String descricao;
	
	Padroes(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static List<Padroes> getTodosPadroes(){
		return Arrays.asList(Padroes.values());
	}
	
	public static Padroes comparaPadrao(String padrao) {
		List<Padroes> padroes = getTodosPadroes();
		
		Padroes padraoRetorno = NULL;
		
		for(int i=0; i<padroes.size(); i++) {
			if(padrao.equals(padroes.get(i).getDescricao())) {
				padraoRetorno = padroes.get(i);
			}
		}
		
		return padraoRetorno;
	}

}
