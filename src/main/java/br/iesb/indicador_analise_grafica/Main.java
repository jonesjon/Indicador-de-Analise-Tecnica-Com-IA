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

import br.iesb.indicador_analise_grafica_enum.Perfil;


@SpringBootApplication
public class Main {

	public static void main(String[] args) throws IOException, SQLException {

		SpringApplication.run(Main.class, args);
		long start = System.currentTimeMillis();

		Perfil perfil1 = Perfil.ARROJADO;
		Perfil perfil2 = Perfil.MODERADO;
		Perfil perfil3 = Perfil.CONSERVADOR;
		
		//TreinamentoRedeNeural.realizaTreinamentoProcurandoPadroesEmPapeisOperaveis();
		//TreinamentoRedeNeural.confereAlvosDasOperacoesPossiveis();
		//RedeNeural.preenchendoEstatisticaPiercingLine(perfil1);
		//RedeNeural.preencherTxtValidacaoRedeNeuralPiercingLine();
		//RedeNeural.realizaTreinamentoRedeNeuralPiercingLine(perfil1);
		System.out.println();
		System.out.println("Testando Martelo Ano 2021: ");
		RedeNeural.testaRedeNeuralMarteloNaPratica(perfil1);
		RedeNeural.testaRedeNeuralMarteloNaPratica(perfil2);
		RedeNeural.testaRedeNeuralMarteloNaPratica(perfil3);
		System.out.println();
		System.out.println();
		System.out.println("Testando Piercing Line Ano 2021: ");
		RedeNeural.testaRedeNeuralPiercingLineNaPratica(perfil1);
		RedeNeural.testaRedeNeuralPiercingLineNaPratica(perfil2);
		RedeNeural.testaRedeNeuralPiercingLineNaPratica(perfil3);
		//RedeNeural.preenchendoEstatisticaMartelo(perfil);
		//RedeNeural.preencherTxtValidacaoRedeNeuralMartelo();
		
		//RedeNeural.realizaTreinamentoRedeNeuralMartelo(perfil);
		//RedeNeural.testaRedeNeuralMarteloNaPratica(perfil);

		long elapsedTime = (System.currentTimeMillis() - start);
		System.out.println("Tempo gasto parar rodar: (segundos) " + elapsedTime / 1000F);

	}

}
