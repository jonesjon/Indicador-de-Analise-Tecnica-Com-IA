package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.List;

public enum VariacaoPrecoEnum {

	ATE2VEZES("Variacao de ate duas vezes"),
	DE2A3VEZES("Variacao de duas a tres vezes"),
	DE3A4VEZES("Variacao de tres a quatro vezes"),
	DE4A5VEZES("Variacao de quatro a cinco vezes"),
	MAIORQUE5VEZES("Variacao maior de cinco vezes"),
	NULL("null");
	
	private String descricao;
	
	VariacaoPrecoEnum(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static List<VariacaoPrecoEnum> getListVariacaoEngolfo(){
		
		List<VariacaoPrecoEnum> variacao = new ArrayList<VariacaoPrecoEnum>();
		
		variacao.add(ATE2VEZES);
		variacao.add(DE2A3VEZES);
		variacao.add(DE3A4VEZES);
		variacao.add(DE4A5VEZES);
		variacao.add(MAIORQUE5VEZES);
		
		return variacao;
		
	}
	
	public static VariacaoPrecoEnum comparaVariacaoPreco(String variacao) {
		List<VariacaoPrecoEnum> listVariacao = getListVariacaoEngolfo();
		
		VariacaoPrecoEnum varRetorno = VariacaoPrecoEnum.NULL;
		
		for(int i=0; i<listVariacao.size(); i++) {
			if(listVariacao.get(i).getDescricao().equals(variacao)) {
				varRetorno = listVariacao.get(i);
			}
		}
		
		return varRetorno;
	}
}
