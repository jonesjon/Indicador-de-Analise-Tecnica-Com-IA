package br.iesb.indicador_analise_grafica_enum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PavioInferiorEnum {

	SEMPAVIO("Sem Pavio Inferior", 1), 
	PAVIO5PORCENTO("Até 5%", 2), 
	PAVIO10PORCENTO("De 5% até 10%", 3), 
	PAVIO33PORCENTO("Maior que 10% e menor que 33%", 4),
	PAVIO40PORCENTO("Maior que 33% e menor que 40%", 5),
	PAVIO67PORCENTO("Maior que 40% e menor que 67%", 6),
	PAVIO80PORCENTO("Maior que 67% e menor que 80%", 7),
	PAVIO90PORCENTO("Maior que 80% e menor que 90%", 8),
	PAVIO95PORCENTO("Maior que 90% e menor que 95%", 9),
	PAVIO100PORCENTO("Maior que 95% e menor que 100%", 10),
	NULL("null", -1);
	
	private String descricao;
	private int ID;
	
	PavioInferiorEnum(String descricao, int id){
		this.descricao = descricao;
		this.ID = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public int getID() {
		return ID;
	}
	
	public static List<PavioInferiorEnum> getPavioInferiorTresSoldados(){
		List<PavioInferiorEnum> list = new ArrayList<PavioInferiorEnum>();
		
		list.add(SEMPAVIO);
		list.add(PAVIO5PORCENTO);
		list.add(PAVIO10PORCENTO);
		list.add(PAVIO33PORCENTO);
		
		return list;
		
	}
	
	public static List<PavioInferiorEnum> getPavioInferiorMartelo(){
		List<PavioInferiorEnum> list = new ArrayList<PavioInferiorEnum>();
		
		list.add(PAVIO80PORCENTO);
		list.add(PAVIO90PORCENTO);
		list.add(PAVIO95PORCENTO);
		list.add(PAVIO100PORCENTO);
		list.add(NULL);
		
		return list;
	}
	
	public static List<PavioInferiorEnum> getPavioInferiorEngolfo(){
		List<PavioInferiorEnum> list = new ArrayList<PavioInferiorEnum>();
		
		list.add(SEMPAVIO);
		list.add(PAVIO5PORCENTO);
		list.add(PAVIO10PORCENTO);
		list.add(PAVIO33PORCENTO);
		list.add(PAVIO40PORCENTO);
		
		return list;
	}
	
	public static List<PavioInferiorEnum> retornaPavios(){
		return Arrays.asList(PavioInferiorEnum.values());
	}
	
	public static PavioInferiorEnum comparaPavioInferior(String pavio) {
		List<PavioInferiorEnum> pavios = retornaPavios();
		
		PavioInferiorEnum pavioRetorno = NULL;
		
		for(int i=0; i<pavios.size(); i++) {
			if(pavios.get(i).getDescricao().equals(pavio)) {
				pavioRetorno = pavios.get(i);
			}
		}
		
		return pavioRetorno;
				
	}
	
}
