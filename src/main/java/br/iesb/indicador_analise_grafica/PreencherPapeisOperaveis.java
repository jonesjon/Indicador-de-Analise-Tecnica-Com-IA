package br.iesb.indicador_analise_grafica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import br.iesb.indicador_analise_grafica.service.PapeisOperaveisService;

public class PreencherPapeisOperaveis {
	
	private static ArrayList<PapeisOperaveis> papeisOperaveis = new ArrayList<PapeisOperaveis>();
	
	public static Boolean preencher() {
		
		try {
			String caminhoArquivo = "C:\\Users\\João\\Desktop\\papeisMario.txt";
			FileReader arquivo = new FileReader(caminhoArquivo);
			BufferedReader leitura = new BufferedReader(arquivo);
			
			String linha = leitura.readLine();
			
			while (linha != null) {
				PapeisOperaveis po = new PapeisOperaveis();
				po.setNomeDoPapel(linha);
				papeisOperaveis.add(po);
				linha = leitura.readLine();
			}
			
		} catch (IOException e) {
			System.err.printf("Não foi possivel abrir o arquivo: %s.\n", e.getMessage());
		}
		
		PapeisOperaveisService.setAllPapeis(papeisOperaveis);
		
		return null;
	}

}
