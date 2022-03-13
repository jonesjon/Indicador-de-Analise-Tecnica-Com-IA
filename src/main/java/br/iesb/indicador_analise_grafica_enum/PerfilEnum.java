package br.iesb.indicador_analise_grafica_enum;

public enum PerfilEnum {
	
	ARROJADO("Arrojado", 60.0),
	MODERADO("Moderado", 70.0),
	CONSERVADOR("Conservador", 80.0);
	
	String definicao;
	Double id;
	
	PerfilEnum(String definicao, Double id){
		this.definicao = definicao;
		this.id = id;
	}
	
	public Double getValor() {
		return id;
	}
	
	public String getNome() {
		return definicao;
	}

}
