package br.iesb.indicador_analise_grafica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.iesb.indicador_analise_grafica_enum.Perfil;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws IOException, SQLException {

		SpringApplication.run(Main.class, args);
		long start = System.currentTimeMillis();

		Perfil perfil = null;
		
		perfil = selecionaPerfil(perfil);
		
		menu(perfil);

		long elapsedTime = (System.currentTimeMillis() - start);
		System.out.println("Tempo gasto para rodar: (segundos) " + elapsedTime / 1000F);

	}

	private static void menu(Perfil perfil) {
		int opcaoDeMenu = -1;
		do {
			apresentarAsOpcoesDeMenu();
			
			opcaoDeMenu = leValorInteiroParaSwitch();
			
			switch (opcaoDeMenu) {
			case 1:
				RedeNeural.testaRedeNeuralMarteloNaPratica(perfil);
				break;
			
			case 2:
				RedeNeural.testaRedeNeuralEngolfoNaPratica(perfil);
				break;
				
			case 3:
				RedeNeural.testaRedeNeuralPiercingLineNaPratica(perfil);
				break;
				
			case 4:
				RedeNeural.testaRedeNeuralTresSoldadosNaPratica(perfil);
				break;
				
			case 5:
				perfil = selecionaPerfil(perfil);
				break;
				
			default:
				System.out.println("Opcao Invalida.");

			}

		} while (opcaoDeMenu != 0);
	}

	private static Perfil selecionaPerfil(Perfil perfil) {
		int valorDoPerfil = 0;
		
		do{
			opcoesParaPerfilEscritas();
			valorDoPerfil = leValorInteiroParaSwitch();
			
			perfil = switchParaSelecionarPerfil(valorDoPerfil);
			
		}while(perfil == null);
		
		return perfil;
		
	}

	private static Perfil switchParaSelecionarPerfil(int valorDoPerfil) {
		Perfil perfil;
		switch (valorDoPerfil) {
		case 1:
			perfil = Perfil.ARROJADO;
			return perfil;
			
		case 2:
			perfil = Perfil.MODERADO;
			return perfil;
			
		case 3:
			perfil = Perfil.CONSERVADOR;
			return perfil;
			
		default:
			System.out.println("Opcao Invalida.");
			return null;
		}
	}

	private static int leValorInteiroParaSwitch() {
		int valorDoPerfil = -1;
		Scanner scanner = new Scanner(System.in);
		try {
			valorDoPerfil = Integer.parseInt(scanner.nextLine());
		}catch(NumberFormatException numberException) {
			System.out.println("Não pode selecionar letra.");
		}
		return valorDoPerfil;
	}

	private static void opcoesParaPerfilEscritas() {
		System.out.println();
		System.out.println("Digite a Opcao de Perfil: ");
		System.out.println("1 - Arrojado ");
		System.out.println("2 - Moderado ");
		System.out.println("3 - Conservador ");
		System.out.println();
	}

	private static void apresentarAsOpcoesDeMenu() {
		System.out.println();
		System.out.println("1 - Realiza Treinamento Para O Padrao Martelo.");
		System.out.println("2 - Realiza Treinamento Para O Padrao Engolfo.");
		System.out.println("3 - Realiza Treinamento Para O Padrao Piercing Line.");
		System.out.println("4 - Realiza Treinamento Para O Padrao Tres Soldados.");
		System.out.println("5 - Troca O Perfil De Usuario.");
		System.out.println("0 - Sair.");
		System.out.println();
	}

}
