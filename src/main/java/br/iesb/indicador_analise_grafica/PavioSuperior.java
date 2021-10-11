package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;
import java.util.List;

public enum PavioSuperior {
	
	SEMPAVIO("Sem Pavio Superior"), 
	PAVIO5PORCENTO("Até 5%"), 
	PAVIO10PORCENTO("De 5% até 10%"), 
	PAVIO33PORCENTO("Maior que 10% e menor que 33%"),
	PAVIO40PORCENTO("Maior que 33% e menor que 40%"),
	PAVIO67PORCENTO("Maior que 40% e menor que 67%"),
	PAVIO80PORCENTO("Maior que 67% e menor que 80%"),
	PAVIO90PORCENTO("Maior que 80% e menor que 90%"),
	PAVIO95PORCENTO("Maior que 90% e menor que 95%"),
	PAVIO100PORCENTO("Maior que 90% e menor que 100%"),
	NULL("null");
	
	
	private String descricao;
	
	PavioSuperior(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static List<PavioSuperior> getPavioSuperiorMartelo() {
		List<PavioSuperior> listAcimaDe = new ArrayList<PavioSuperior>();
		
		listAcimaDe.add(SEMPAVIO);
		listAcimaDe.add(PAVIO5PORCENTO);
		listAcimaDe.add(PAVIO10PORCENTO);
		listAcimaDe.add(NULL);
		
		return listAcimaDe;
	}

}
