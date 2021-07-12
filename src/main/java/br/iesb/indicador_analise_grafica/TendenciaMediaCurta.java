package br.iesb.indicador_analise_grafica;

public enum TendenciaMediaCurta {
	
	ALTA("Alta"), BAIXA("Baixa"), NEUTRA("Neutra");
	
	private String tendencia;
	
	TendenciaMediaCurta(String tendencia){
		this.tendencia = tendencia;
	}
	
	public String getTendenciaCurta() {
		return tendencia;
	}

}
