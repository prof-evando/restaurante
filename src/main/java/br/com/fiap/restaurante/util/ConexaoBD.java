package br.com.fiap.restaurante.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
		
		private static ConexaoBD instancia;
		
		private Connection conn;
		
		private ConexaoBD () {
			
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
				conn = DriverManager.getConnection(url, PropriedadesConexao.obterPropriedades());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public static ConexaoBD getInstance() {
			if(instancia == null) {
				instancia = new ConexaoBD();
			}
			return instancia;
		}

		public Connection getConn() {
			return conn;
		}


}
