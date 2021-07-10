package br.iesb.indicador_analise_grafica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {


	public static java.sql.Connection getConexaoMySQL() {
		Connection connection = null;
		try {
			
			String driverName = "com.mysql.jdbc.Driver";

			Class.forName(driverName);

			String serverName = "localhost:3306"; // caminho do servidor do BD
			String mydatabase = "indicadordeanalisetecnicacomia"; // nome do seu banco de dados
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "root"; // nome de um usuário de seu BD
			String password = ""; // sua senha de acesso

			connection = DriverManager.getConnection(url, username, password);

			return connection;

		} catch (ClassNotFoundException e) { // Driver não encontrado

			System.out.println("O driver expecificado nao foi encontrado.");
			return null;

		} catch (SQLException e) {
			// Não conseguindo se conectar ao banco
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}

	}

	// Método que fecha sua conexão//

	public static boolean FecharConexao() {

		try {
			ConexaoBancoDeDados.getConexaoMySQL().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	// Método que reinicia sua conexão//

	public static java.sql.Connection ReiniciarConexao() {
		FecharConexao();
		return ConexaoBancoDeDados.getConexaoMySQL();

	}

}
