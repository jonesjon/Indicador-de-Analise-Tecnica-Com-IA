package br.iesb.indicador_analise_grafica;

import java.io.IOException;

public class PopularBanco {
	
	private static String fileNameFirst = "C:\\Users\\João\\Documents\\TCC\\Programa\\Serie Historica\\COTAHIST_A";
	private static String fileNameLast = ".TXT";
	
	static InterpretadorDeDados dados;
	
	public static void inserirDados() throws IOException{
		for(int i=2000; i<=2020; i++) {
			String fileName = fileNameFirst + Integer.toString(i) + fileNameLast;
			dados = new InterpretadorDeDados(fileName);
		}
	}
}
