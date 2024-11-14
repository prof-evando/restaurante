package br.com.fiap.restaurante.util;

import java.util.Properties;

public class PropriedadesConexao {
	
	public static Properties obterPropriedades() {
		Properties props = new Properties();
		props.put("user", "rm98480");
		props.put("password", "280602");
		return props;
	}

}
