package br.iesb.indicador_analise_grafica_enum;

import java.util.Arrays;
import java.util.List;

public enum PadroesEnum {
	
	MARTELO("Martelo"),
	MARTELOINVERTIDO("Martelo Invertido"),
	MARUBOZU("Marubozu"),
	ENGOLFO("Engolfo"),
	PIERCINGLINE("Piercing Line"),
	DARKCLOUD("Dark Cloud"),
	DOJICOMPRA("Doji de Compra"),
	DOJIVENDA("Doji de Venda"),
	TRESSOLDADOSDEALTA("Tres Soldados de Alta"),
	TRESSOLDADOSDEBAIXA("Tres soldados de Baixa"),
	BEBEABANDONADODEALTA("Bebe Abandonado de Alta"),
	BEBEABANDONADODEBAIXA("Bebe Abandonado de Baixa"),
	NULL("Null");
	
	private String descricao;
	
	
	
	PadroesEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static List<PadroesEnum> getTodosPadroes(){
		return Arrays.asList(PadroesEnum.values());
	}
	
	public static PadroesEnum comparaPadrao(String padrao) {
		List<PadroesEnum> padroes = getTodosPadroes();
		
		PadroesEnum padraoRetorno = NULL;
		
		for(int i=0; i<padroes.size(); i++) {
			if(padrao.equals(padroes.get(i).getDescricao())) {
				padraoRetorno = padroes.get(i);
			}
		}
		
		return padraoRetorno;
	}

}
