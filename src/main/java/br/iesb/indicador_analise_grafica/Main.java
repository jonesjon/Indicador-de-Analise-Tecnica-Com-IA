package br.iesb.indicador_analise_grafica;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.iesb.indicador_analise_grafica.model.Teste;
import br.iesb.indicador_analise_grafica.repository.ITesteRepository;
import br.iesb.indicador_analise_grafica.service.TesteServiceImpl;



public class Main {
	

	public static void main(String[] args) throws IOException {
		long start =  System.currentTimeMillis();   
		
		TesteServiceImpl t = new TesteServiceImpl();
		Teste teste = t.getTesteById(1l);
		long id = 1l;
		
		//encontraUmTeste();	
		
		//PrintDemo.getRepository();
		System.out.println(t);
		
		//long count = t.getTesteById(null);
		/*
		if(count > 1l) {
			Teste t = testeRepository.findOne(id);
		
			//t.setId(23l);
			t.setDescricao("aqui vai uma descrição");
			testeRepository.save(t); 
		}*/
		
		//gitbash
		String fileName = "COTAHIST_A2010.txt";
		
		TreinamentoRedeNeural treinamento = new TreinamentoRedeNeural();
		
		String papeis[] = {"ABCB4 ","ABNB3 ", "AELP3 ", "AGRO3 ","AMBV3 ", "AMBV4 ", "AMIL3 ","BALM4 ", "BBAS3 ","BBDC3 ", "BBDC4 ", "BGIP4 ", "BICB4 ",
								"BNCA3 ", "BPNM4 ", "BRAP3 ", "BRAP4 ", "BRFS3 ", "BRKM3 ", "BRKM5 ", "BRML3 ","BRSR3 ","BRSR6 ","BRTO3 ", "BRTO4 ", "BTOW3 ",
									"BVMF3 ","BVMF3 ","CCRO3 ", "CEBR6 ", "CIEL3 ", "CLAN4 ", "CMIG3 ", "CMIG4 ", "COCE3 ","CPFE3 "};
		
		String papel = "ABCB4 ";
		/*
		for(int i=0; i<36; i++) {
			
			String abc = "/home/leonardo/eclipse-workspace/Indicador-de-Analise-Tecnica-Com-IA/";
			InterpretadorDeDados dados = new InterpretadorDeDados( abc + "COTAHIST_A2010.txt", papeis[i]);
			grafico.ordenaGrafico();
			treinamento.realizaTreinamento();
		}*/
		
		//System.out.println(conexao.getConexaoMySQL());
		
		InterpretadorDeDados dados = new InterpretadorDeDados(fileName, papel);
		treinamento.imprimeOperacoes();
		treinamento.percentualFinal();
		treinamento.percentualFinalMarteloSemPavioSuperior();
		treinamento.percentualGainLossMarteloSemPavioSuperior();
		treinamento.percentualGainLossMarteloSemPavioSuperiorPavioInferiorMax();
		
		long elapsedTime = (System.currentTimeMillis()-start);
		System.out.println("Tempo gasto parar rodar: (segundos) " + elapsedTime / 1000F );

		
	}

}
