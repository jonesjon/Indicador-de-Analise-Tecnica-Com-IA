package br.iesb.indicador_analise_grafica;

public enum PavioSuperior {
	
	SEMPAVIO("Sem Pavio Superior"), 
	PAVIO5PORCENTO("Até 5% do tamanho do Candle"), 
	PAVIO10PORCENTO("de 5% até 10% do tamanho do Candle"), 
	PAVIO33PORCENTO("Pavio maior que 10% e menor que 33% do tamanho do Candle"),
	PAVIO40PORCENTO("Pavio maior que 33% e menor que 40% do tamanho do Candle"),
	PAVIO67PORCENTO("Pavio maior que 40% e menor que 67% do tamanho do Candle"),
	PAVIO80PORCENTO("Pavio maior que 67% e menor que 80% do tamanho do Candle"),
	PAVIO90PORCENTO("Pavio maior que 80% e menor que 90% do tamanho do Candle"),
	PAVIO95PORCENTO("Pavio maior que 90% e menor que 95% do tamanho do Candle"),
	PAVIO100PORCENTO("Pavio maior que 90% e menor que 100% do tamanho do Candle");
	
	
	private String descricao;
	
	PavioSuperior(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
