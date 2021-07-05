package br.iesb.indicador_analise_grafica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;



public class InterpretadorDeDados {
	
	ArrayList<String> caracteres = new ArrayList<String>();
	Grafico grafico = new Grafico();
	Candle candle;
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	LocalDate data;
	Scanner scanner = new Scanner(System.in);
	RedeNeural redeNeural = new RedeNeural();
	
	
	public InterpretadorDeDados(String nome, String papel) throws IOException {
		
		
		
		try {
			
			Grafico.grafico.clear();
			Grafico.listaDeMedias.clear();
			FileReader arquivo = new FileReader(nome);
			BufferedReader leitura = new BufferedReader(arquivo);  																	//Leitura do Arquivo da Série Histórica
			
			String linha = leitura.readLine();																						//Le linha por linha 
			
			while (linha != null) {
				
				caracteres.clear();
				
				for(int i=0; i<linha.length(); i++) {
					caracteres.add(i, ""+linha.charAt(i));																			//Adiciona todos os caracteres da linha em um ArrayList, 
				}																													//para fazer a interpretação do arquivo.
				
				if(Integer.parseInt(caracteres.get(1)) == 0) {																		//Header
					
					data = LocalDate.parse(caracteres.get(29)+caracteres.get(30)+ "/" +caracteres.get(27)+caracteres.get(28) +"/"+
							caracteres.get(23)+caracteres.get(24)+caracteres.get(25)+caracteres.get(26), formato);		//Data de geração do Arquivo no formato 
																																	// de datas
						
					//System.out.println("Data da geração do Arquivo: " + formato.format(data));
				}
				
				if(Integer.parseInt(caracteres.get(1)) == 1) {																		//Informações referentes aos papeis
					
					String papelDaLinha = "";
					String abertura = "";
					String fechamento = ""; 
					String maxima = ""; 
					String minima = ""; 
					String volume = "";
					String dia = "";
					String mes = "";
					String ano = "";
					
					
					
					for(int i=12; i<=17; i++) {																						//Verifica o nome do ativo no arquivo 
						papelDaLinha += caracteres.get(i);
					}
					
					if(papelDaLinha.equals(papel)) {																				//Compara o nome do papel e se for o que está observando, entra no if
						
						for(int i=8; i<=9; i++) {																					//dia de negociação 
							dia += caracteres.get(i);
						}
						for(int i=6; i<=7; i++) {																					//mes de negociação
							mes += caracteres.get(i);
						}
						for(int i=2; i<=5; i++) {																					//ano de negociação
							ano += caracteres.get(i);
						}
						for(int i=56; i<=68; i++) {																					//Preço de abertura do ativo
							abertura += caracteres.get(i);
						}
						for(int i=69; i<=81; i++) {																					//Preço maximo do ativo
							maxima += caracteres.get(i);
						}
						for(int i=82; i<=94; i++) {																					//Preço minimo do ativo
							minima += caracteres.get(i);
						}
						for(int i=108; i<=120; i++) {																				//Preço de fechamento do ativo
							fechamento += caracteres.get(i);
						}
						for(int i=170; i<=187; i++) {																				//Volume de negociação 
							volume += caracteres.get(i);
						} 
						
						candle = new Candle(dia, mes, ano, abertura, maxima, minima, fechamento, volume, papel);
						grafico.adicionaCandle(candle);
						grafico.adicionaMediaMovelNaLista(candle, 8);
						grafico.adicionaMediaMovelNaLista(candle, 20);
						grafico.adicionaMediaMovelNaLista(candle, 200);
						
					}
					
					
				}

		        linha = leitura.readLine();																							//Le a Proxima Linha
		      }
			
		} catch (IOException e) {
	        System.err.printf("Não foi possivel abrir o arquivo: %s.\n",
	                e.getMessage());
	    }
		
	}
	
	

}
