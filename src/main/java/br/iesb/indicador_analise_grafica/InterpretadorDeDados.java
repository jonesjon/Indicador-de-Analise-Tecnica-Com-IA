package br.iesb.indicador_analise_grafica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.repository.InfoCandleRepository;
import br.iesb.indicador_analise_grafica.service.InfoCandleService;

public class InterpretadorDeDados {

	ArrayList<String> caracteres = new ArrayList<String>();
	Grafico grafico = new Grafico();
	Candle candle;
	InfoCandle infoCandle;
	ArrayList<InfoCandle> listaInfoCandle = new ArrayList<InfoCandle>();
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	LocalDate data;
	Scanner scanner = new Scanner(System.in);
	RedeNeural redeNeural = new RedeNeural();

	public InterpretadorDeDados(String nome) throws IOException {

		try {

			FileReader arquivo = new FileReader(nome);
			BufferedReader leitura = new BufferedReader(arquivo); // Leitura do Arquivo da Série Histórica

			String linha = leitura.readLine(); // Le linha por linha

			while (linha != null) {

				caracteres.clear();

				for (int i = 0; i < linha.length(); i++) {
					caracteres.add(i, "" + linha.charAt(i));
				} 

				if (Integer.parseInt(caracteres.get(1)) == 0) { // Header

					data = LocalDate.parse(caracteres.get(29) + caracteres.get(30) + "/" + caracteres.get(27)
							+ caracteres.get(28) + "/" + caracteres.get(23) + caracteres.get(24) + caracteres.get(25)
							+ caracteres.get(26), formato); 

					/* System.out.println("Data da geração do Arquivo: " + formato.format(data)); */
				}

				if (Integer.parseInt(caracteres.get(1)) == 1) { // Informa��es referentes aos papeis
					
					String codigoBDI = "";
					
					for (int i = 10; i <= 11; i++) { // Verifica o nome do ativo no arquivo
						codigoBDI += caracteres.get(i);
					}
					
					if(codigoBDI.equals("02")) {
						
						String papelDaLinha = "";
						String abertura = "";
						String fechamento = "";
						String maxima = "";
						String minima = "";
						String volume = "";
						String dia = "";
						String mes = "";
						String ano = "";
						

						for (int i = 12; i <= 23; i++) { // Verifica o nome do ativo no arquivo
							papelDaLinha += caracteres.get(i);
						} 
	
						for (int i = 8; i <= 9; i++) { // dia de negocia��o
							dia += caracteres.get(i);
						}
						for (int i = 6; i <= 7; i++) { // mes de negocia��o
							mes += caracteres.get(i);
						}
						for (int i = 2; i <= 5; i++) { // ano de negocia��o
							ano += caracteres.get(i);
						}
						for (int i = 56; i <= 68; i++) { // Pre�o de abertura do ativo
							abertura += caracteres.get(i);
						}
						for (int i = 69; i <= 81; i++) { // Pre�o maximo do ativo
							maxima += caracteres.get(i);
						}
						for (int i = 82; i <= 94; i++) { // Pre�o minimo do ativo
							minima += caracteres.get(i);
						}
						for (int i = 108; i <= 120; i++) { // Pre�o de fechamento do ativo
							fechamento += caracteres.get(i);
						}
						for (int i = 170; i <= 187; i++) { // Volume de negocia��o
							volume += caracteres.get(i);
						}
	
						String sDate = dia + "/" + mes + "/" + ano;
						LocalDate date = LocalDate.parse(sDate, formato);
	
						candle = new Candle(date, abertura, maxima, minima, fechamento, volume, papelDaLinha.trim());
						grafico.adicionaCandle(candle);
	
						listaInfoCandle.clear();
						listaInfoCandle = InfoCandleService.getListForMediaMovel(papelDaLinha.trim());
	
						Double media8 = Indicador.mediaMovel(8, listaInfoCandle, candle);
						Double media20 = Indicador.mediaMovel(20, listaInfoCandle, candle);
						Double media200 = Indicador.mediaMovel(200, listaInfoCandle, candle);
						Double mediaVolume = Indicador.mediaMovelVolume(20, listaInfoCandle, candle);
	
						infoCandle = new InfoCandle(candle, media8, media20, media200, mediaVolume);
	
						InfoCandleService.adicionaCandle(infoCandle);
					
				}

				}

				linha = leitura.readLine(); // Le a Proxima Linha
			}

		} catch (IOException e) {
			System.err.printf("Não foi possivel abrir o arquivo: %s.\n", e.getMessage());
		}

	}
}
