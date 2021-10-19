package br.iesb.indicador_analise_grafica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {

	public static void main(String[] args) throws IOException, SQLException {

		SpringApplication.run(Main.class, args);
		long start = System.currentTimeMillis();

		//TreinamentoRedeNeural.realizaTreinamentoProcurandoPadroesEmPapeisOperaveis();
		//TreinamentoRedeNeural.confereAlvosDasOperacoesPossiveis();
		//RedeNeural.preenchendoEstatisticaMartelo();
		//RedeNeural.preencherTxtValidacaoRedeNeuralMartelo();
		
		RedeNeural.realizaTreinamentoRedeNeural();

		long elapsedTime = (System.currentTimeMillis() - start);
		System.out.println("Tempo gasto parar rodar: (segundos) " + elapsedTime / 1000F);

	}

}
