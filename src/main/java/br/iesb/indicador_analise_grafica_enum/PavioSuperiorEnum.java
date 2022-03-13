package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PavioSuperiorEnum {
	
	SEMPAVIO("Sem Pavio Superior", 1), 
	PAVIO5PORCENTO("Até 5%", 2), 
	PAVIO10PORCENTO("De 5% até 10%", 3), 
	PAVIO33PORCENTO("Maior que 10% e menor que 33%", 4),
	PAVIO40PORCENTO("Maior que 33% e menor que 40%", 5),
	PAVIO67PORCENTO("Maior que 40% e menor que 67%", 6),
	PAVIO80PORCENTO("Maior que 67% e menor que 80%", 7),
	PAVIO90PORCENTO("Maior que 80% e menor que 90%", 8),
	PAVIO95PORCENTO("Maior que 90% e menor que 95%", 9),
	PAVIO100PORCENTO("Maior que 90% e menor que 100%", 10),
	NULL("null", -1);
	
	
	private String descricao;
	private int ID;
	
	PavioSuperiorEnum(String descricao, int ID){
		this.descricao = descricao;
		this.ID = ID;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public int getID() {
		return ID;
	}
	
	public static List<PavioSuperiorEnum> getPavioSuperiorTresSoldados(){
		List<PavioSuperiorEnum> list = new ArrayList<PavioSuperiorEnum>();
		
		list.add(SEMPAVIO);
		list.add(PAVIO5PORCENTO);
		list.add(PAVIO10PORCENTO);
		list.add(PAVIO33PORCENTO);
		
		return list;
		
	}
	
	public static List<PavioSuperiorEnum> getPavioSuperiorMartelo() {
		List<PavioSuperiorEnum> listAcimaDe = new ArrayList<PavioSuperiorEnum>();
		
		listAcimaDe.add(SEMPAVIO);
		listAcimaDe.add(PAVIO5PORCENTO);
		listAcimaDe.add(PAVIO10PORCENTO);
		listAcimaDe.add(NULL);
		
		return listAcimaDe;
	}
	
	public static List<PavioSuperiorEnum> getPavioSuperiorEngolfo() {
		List<PavioSuperiorEnum> listAcimaDe = new ArrayList<PavioSuperiorEnum>();
		
		listAcimaDe.add(SEMPAVIO);
		listAcimaDe.add(PAVIO5PORCENTO);
		listAcimaDe.add(PAVIO10PORCENTO);
		listAcimaDe.add(PAVIO33PORCENTO);
		listAcimaDe.add(PAVIO40PORCENTO);
		
		return listAcimaDe;
	}
	
	public static List<PavioSuperiorEnum> retornaPavios() {
		return Arrays.asList(PavioSuperiorEnum.values());
	}
	
	public static PavioSuperiorEnum comparaPavioSuperior(String pavio) {
		List<PavioSuperiorEnum> pavios = retornaPavios();
		
		PavioSuperiorEnum pavioRetorno = NULL;
		
		for(int i=0; i<pavios.size(); i++) {
			if(pavios.get(i).descricao.equals(pavio)) {
				pavioRetorno = pavios.get(i);
			}
		}
		
		return pavioRetorno;
	}

}
