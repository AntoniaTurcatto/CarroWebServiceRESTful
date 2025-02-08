package br.com.livro.domain;

import java.sql.*;

public class BaseDAO {

	public BaseDAO() {
		try {
			//necessário para utilizar o jdbc
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected Connection getConnection() {
		//URL de conexão com o banco de dados
		String url="jdbc:mysql://localhost/livro";
		String usuario = "livro";
		String senha = "2275";
		try {
			return DriverManager.getConnection(url, usuario, senha);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main (String[] args) throws SQLException{
		BaseDAO db = new BaseDAO();
		//testa a conexão
		Connection conn = db.getConnection();
		System.out.println(conn);
	}
}
