package br.iesb.indicador_analise_grafica.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Arquivo {

	public static Set<String> listFilesUsingDirectoryStream(String dir) {
		return Stream.of(new File(dir).listFiles())
			      .filter(file -> !file.isDirectory())
			      .map(File::getName)
			      .collect(Collectors.toSet());
	}
	
	public static ArrayList<String> leLinhasDeArquivo(String fullName) {
		FileReader arquivo = null;
		try {
			arquivo = new FileReader(fullName);
			
			BufferedReader leitura = new BufferedReader(arquivo);
			
			ArrayList<String> linhas = leTodasLinhas(leitura);
			System.out.println(linhas.size());
			return linhas;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(arquivo != null)
					arquivo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static ArrayList<String> leTodasLinhas(BufferedReader leitura) {
		String linha;
		ArrayList<String> linhas = new ArrayList<>();
		
		try {
			linha = leitura.readLine();
			while (linha != null) {
				linhas.add(linha);
				linha = leitura.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return linhas;
	}
}
