package br.iesb.indicador_analise_grafica_enum;

public enum TendenciaMediaCurtaEnum {
	
	ALTA("Alta"), BAIXA("Baixa"), NEUTRA("Neutra");
	
	private String tendencia;
	
	TendenciaMediaCurtaEnum(String tendencia){
		this.tendencia = tendencia;
	}
	
	public String getTendenciaCurta() {
		return tendencia;
	}

}
