package br.iesb.indicador_analise_grafica;

import java.io.IOException;

public class PopularBanco {
	
	private static String fileNameFirst = "C:\\Users\\joaov\\git\\Indicador-de-Analise-Tecnica-Com-IA\\src\\main\\resources\\COTAHIST_A";
	private static String fileNameLast = ".TXT";
	
	static InterpretadorDeDados dados;
	
	public static void inserirDados() throws IOException{
		for(int i=2000; i<=2020; i++) {
			String fileName = fileNameFirst + Integer.toString(i) + fileNameLast;
			
			dados = new InterpretadorDeDados(fileName);
			
		}
	}
	

}
