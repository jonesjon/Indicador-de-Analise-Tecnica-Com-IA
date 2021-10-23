package br.iesb.indicador_analise_grafica_enum;

public enum Perfil {
	
	ARROJADO("Arrojado", 60),
	MODERADO("Moderado", 70),
	CONSERVADOR("Conservador", 80);
	
	String definicao;
	int id;
	
	Perfil(String definicao, int id){
		this.definicao = definicao;
		this.id = id;
	}
	
	public int getValor() {
		return id;
	}
	
	public String getNome() {
		return definicao;
	}

}
