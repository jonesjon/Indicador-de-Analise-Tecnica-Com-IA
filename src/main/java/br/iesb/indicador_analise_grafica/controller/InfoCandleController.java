package br.iesb.indicador_analise_grafica.controller;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.iesb.indicador_analise_grafica.Candle;
import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.PapeisOperaveis;
import br.iesb.indicador_analise_grafica.TreinamentoRedeNeural;
import br.iesb.indicador_analise_grafica.controle.Controle;
import br.iesb.indicador_analise_grafica.service.InfoCandleService;
import br.iesb.indicador_analise_grafica.service.PapeisOperaveisService;
import br.iesb.indicador_analise_grafica.util.Arquivo;

@RestController
@RequestMapping("/infocandle")
public class InfoCandleController {

	static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Autowired
	private InfoCandleService infocandleService;
	
	class RecuperaRegistros extends Thread {
		private String nomeDoPapel;
		private ArrayList<InfoCandle> ultimos200 = new ArrayList<>();
		
		RecuperaRegistros(String nomeDoPapel) {
			this.nomeDoPapel = nomeDoPapel;
		}

		@Override
		public void run() {
			this.ultimos200 = infocandleService.getUltimos200Candles(this.nomeDoPapel);
		}
		
		public String getNomeDoPapel() {
			return this.nomeDoPapel;
		}
		
		public ArrayList<InfoCandle> getUltimos200() {
			return this.ultimos200;
		}
		
	}

	@GetMapping("/update")
	public String adicionaNovosInfoCandles(){
		
		Controle controle = infocandleService.getControle();
		if(controle == null) {
			return "controle não pode ser null";
		}
		
		if(Boolean.TRUE.equals(controle.getRodandoAtualizacaodiaria())) {
			return "não pode rodar agora, já está rodando atualização";
		}
		
		ArrayList<PapeisOperaveis> todosPapeisOperaveis =  PapeisOperaveisService.getAllPapeis();
		List<RecuperaRegistros> threads = todosPapeisOperaveis.stream().map(papel -> {
			RecuperaRegistros a = new RecuperaRegistros(papel.getNomeDoPapel());
			a.start();
			return a;
		}).collect(Collectors.toList());
		
		
		controle.setRodandoAtualizacaodiaria(true);
		controle = infocandleService.saveControle(controle);

		LocalDate ultimaLeitura = controle.getUltimaInsercaoInfoCandle();
		
		HashMap<String, ArrayList<InfoCandle>> candlesPorPapel = new HashMap<>();
		
		File baseDirectory = new File("");
		final String pastaDiaria = "/series-diarias";
		String directory = baseDirectory.getAbsolutePath() + pastaDiaria;
		System.out.println("directory: " + directory);
		Set<String> arquivosNaPasta = null;
		
		
		
		arquivosNaPasta = Arquivo.listFilesUsingDirectoryStream(directory);
		
		recuperaNovosInfoCandlesDeArquivo(ultimaLeitura, candlesPorPapel, directory, 
				arquivosNaPasta, todosPapeisOperaveis);
		ordenaInfoCandlesPorData(candlesPorPapel);
		
		threads.stream().map( thread -> { 
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			return null; 
		});
		
		salvaNovosInfoCandles(candlesPorPapel, threads);
		System.out.println("Terminou com sucesso de salvar info candles");
		LocalDate ultimaInsercao = infocandleService.getMaxDate();
		System.out.println("Última inserção, " + ultimaInsercao);
		controle.setUltimaInsercaoInfoCandle(ultimaInsercao);
		controle.setUltimaInsercaoMedia(ultimaInsercao);
		System.out.println("Última inserção atualizada");
	
		controle.setRodandoAtualizacaodiaria(false);
		infocandleService.saveControle(controle);
		System.out.println("Atualização diária terminou com sucesso 100%");
		
		TreinamentoRedeNeural.realizaTreinamentoProcurandoPadroesEmPapeisOperaveis(ultimaInsercao);
		
		return "terminou";
		//List<Operacao> operacoes = OperacaoService.getOperacoes();
		
	}

	private void salvaNovosInfoCandles(HashMap<String, ArrayList<InfoCandle>> candlesPorPapel, 
				List<RecuperaRegistros> threads) {
		System.out.println("salva novos info candles + " + candlesPorPapel.size());
		ArrayList<Integer> contador = new ArrayList<>(); 
		contador.add(0);
		candlesPorPapel.entrySet().stream().forEach(set -> {
			int num = contador.get(0);
			System.out.print(num + ", ");
			num++;
			contador.add(0, num);
			String nomePapel = set.getKey();
			
			List<RecuperaRegistros> encontrados =  threads.stream()
				.filter( thread -> thread.getNomeDoPapel().trim().compareTo(nomePapel.trim()) == 0)
				.collect(Collectors.toList());
			if(encontrados.isEmpty()) {
				return;
			}
		
			ArrayList<InfoCandle> pre200 = encontrados.get(0).getUltimos200(); // pegar de hashmap
			int tamanhoPulo = pre200.size();
			
			pre200.addAll(set.getValue());
			ArrayList<InfoCandle> mediasCalculadas = calculaMediasMoveis(pre200);
			
			List<InfoCandle> novosInfoCandles = mediasCalculadas.subList(tamanhoPulo, mediasCalculadas.size());
			
			novosInfoCandles.stream().forEach( InfoCandleService::adicionaCandle);
		});
	}

	private void recuperaNovosInfoCandlesDeArquivo(LocalDate ultimaLeitura,
			HashMap<String, ArrayList<InfoCandle>> candlesPorPapel, String directory, 
			Set<String> arquivosNaPasta, ArrayList<PapeisOperaveis> todosPapeisOperaveis) {
		//recupera todos info candles de arquivo que não estejam listados no banco ainda
		
		arquivosNaPasta.forEach(arquivo -> {
			ArrayList<String> linhas = Arquivo.leLinhasDeArquivo( directory + "/" + arquivo);
			System.out.println("Lendo arquivo: " + arquivo);
			linhas.forEach(l -> {
				ArrayList<String> caracteres = this.pega1Linha(l);
				InfoCandle info = preparaInfoCandle(caracteres, ultimaLeitura, todosPapeisOperaveis);
				if(info != null) {
					if(candlesPorPapel.get(info.getNomeDoPapel()) == null) {
						candlesPorPapel.put(info.getNomeDoPapel(), new ArrayList<>());
					}
					candlesPorPapel.get(info.getNomeDoPapel()).add(info);
				}
			});
			System.out.println("Terminou arquivo: " + arquivo);
		});
	}

	private void ordenaInfoCandlesPorData(HashMap<String, ArrayList<InfoCandle>> candlesPorPapel) {
		// ordena todos os novos info candles por data em todos os papeis.
		for (Map.Entry<String, ArrayList<InfoCandle>> set :
		     candlesPorPapel.entrySet()) {
 
		    Collections.sort(set.getValue(), new Comparator<InfoCandle>() {
		        @Override
		        public int compare(InfoCandle o1, InfoCandle o2) {
		            return o1.getData().compareTo(o2.getData());
		        }
		    });
		}
	}
	
	private InfoCandle preparaInfoCandle(ArrayList<String> caracteres, LocalDate ultimaLeitura, 
			ArrayList<PapeisOperaveis> todosPapeisOperaveis) {
		if(Integer.parseInt(caracteres.get(1)) != 1) {
			return null;
		}
		
		// Informaï¿½ï¿½es referentes aos papeis
					
		String papelDaLinha = "", abertura = "", fechamento = "", maxima = "";
		String minima = "", volume = "", dia = "", mes = "", ano = "";
			
		for (int i = 8; i <= 9; i++) { // dia de negociaï¿½ï¿½o
			dia += caracteres.get(i);
		}
		for (int i = 6; i <= 7; i++) { // mes de negociaï¿½ï¿½o
			mes += caracteres.get(i);
		}
		for (int i = 2; i <= 5; i++) { // ano de negociaï¿½ï¿½o
			ano += caracteres.get(i);
		}
			
		String sDate = dia + "/" + mes + "/" + ano;
		LocalDate date = LocalDate.parse(sDate, formato);
			
		if(!date.isAfter(ultimaLeitura)) {
			return null;
		}
				

		for (int i = 12; i <= 23; i++) { // Verifica o nome do ativo no arquivo
			papelDaLinha += caracteres.get(i);
		} 
		
		final String nomeDoPapel = papelDaLinha.trim();
		List<PapeisOperaveis> encontrados =  todosPapeisOperaveis.stream()
			.filter( papel -> papel.getNomeDoPapel().compareTo(nomeDoPapel) == 0)
			.collect(Collectors.toList());
		if(encontrados.isEmpty()) {
			return null;
		}
			
		if(nomeDoPapel.charAt(nomeDoPapel.length() -1) == 'T')
			return null;

			
		for (int i = 56; i <= 68; i++) { // Preï¿½o de abertura do ativo
			abertura += caracteres.get(i);
		}
		for (int i = 69; i <= 81; i++) { // Preï¿½o maximo do ativo
			maxima += caracteres.get(i);
		}
		for (int i = 82; i <= 94; i++) { // Preï¿½o minimo do ativo
			minima += caracteres.get(i);
		}
		for (int i = 108; i <= 120; i++) { // Preï¿½o de fechamento do ativo
			fechamento += caracteres.get(i);
		}
		for (int i = 170; i <= 187; i++) { // Volume de negociaï¿½ï¿½o
			volume += caracteres.get(i);
		}
			

		Candle candle = new Candle(date, abertura, maxima,
					minima, fechamento, volume, papelDaLinha.trim());
					
		return new InfoCandle(candle, null, null, null, null);
	}
	
	private ArrayList<String> pega1Linha(String linha) {
		ArrayList<String> caracteres = new ArrayList<String>();
		if(linha != null) {
			for (int i1 = 0; i1 < linha.length(); i1++) {
				caracteres.add(i1, "" + linha.charAt(i1));
			}
		}
		return caracteres;
	}
	
	public static ArrayList<InfoCandle> calculaMediasMoveis(ArrayList<InfoCandle> candles) {
		final int MEDIACURTA = 8;
		final int MEDIA = 20;
		final int MEDIALONGA = 200;
		
		String fmt = "#.##";
		DecimalFormat df = new DecimalFormat(fmt);
		df.setRoundingMode(RoundingMode.DOWN);

		ArrayList<InfoCandle> candlesUpdate = new ArrayList<InfoCandle>();
		
		for(int i=0; i<candles.size(); i++) {
			
			InfoCandle infoCandle = candles.get(i);
			
			if(i>=MEDIACURTA-1) {
				ArrayList<InfoCandle> candlesAux = new ArrayList<InfoCandle>();
				candlesAux.addAll(candles.subList(i-(MEDIACURTA-1), i));
				InfoCandle candle = candles.get(i);
				Double mediaCurta = mediaMovel(MEDIACURTA, candlesAux, candle);
				infoCandle.setPrecoMedia8(mediaCurta);
				
			}
			if(i>=MEDIA-1) {
				
				ArrayList<InfoCandle> candlesAux = new ArrayList<InfoCandle>();
				candlesAux.addAll(candles.subList(i-(MEDIA-1), i));
				InfoCandle candle = candles.get(i);
				Double media = mediaMovel(MEDIA, candlesAux, candle);
				Double mediaVolume = mediaMovelVolume(MEDIA, candlesAux, candle);
				infoCandle.setPrecoMedia20(media);
				infoCandle.setVolumeMedia20(mediaVolume);
				
			}
			if(i>=MEDIALONGA-1) {
				ArrayList<InfoCandle> candlesAux = new ArrayList<InfoCandle>();
				candlesAux.addAll(candles.subList(i-(MEDIALONGA-1), i));
				InfoCandle candle = candles.get(i);
				Double mediaLonga = mediaMovel(MEDIALONGA, candlesAux, candle);
				infoCandle.setPrecoMedia200(mediaLonga);
			}
			
			candlesUpdate.add(i, infoCandle);
			
		}
		
		return candlesUpdate;
	}
	
	private static double mediaMovel;
	private static double soma;

	public static Double mediaMovel(int parametro, ArrayList<InfoCandle> listaInfoCandle, InfoCandle candle) {
	
		if (listaInfoCandle.size() >= parametro - 1) {
	
			mediaMovel = 0;
			soma = 0;
	
			for (int i = 0; i < parametro - 1; i++) {
				soma += listaInfoCandle.get(i).getFechamento();
			}
	
			soma += candle.getFechamento();
			mediaMovel = soma / parametro;
	
			return mediaMovel;
		}
	
		return null;
	}

	public static Double mediaMovelVolume(int parametro, ArrayList<InfoCandle> grafico, InfoCandle candle) {
	
		if (grafico.size() >= parametro - 1) {
	
			mediaMovel = 0;
			soma = 0;
	
			for (int i = 0; i < parametro - 1; i++) {
				soma += grafico.get(i).getVolume();
			}
	
			soma += candle.getVolume();
			mediaMovel = soma / parametro;
	
			return mediaMovel;
		}
		return null;
	}

}
