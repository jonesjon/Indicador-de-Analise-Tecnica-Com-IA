package br.iesb.indicador_analise_grafica;

public enum TipoCandle {
	
	POSITIVO("Positivo", 1), NEGATIVO("Negativo", 2), NEUTRO("Neutro", 3), NULL("Null", 4);
	
	private String tipo;
	private int ID;
	
	TipoCandle(String tipo, int ID){
		this.tipo = tipo;
		this.ID = ID;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public int getID() {
		return ID;
	}

}
