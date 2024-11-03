package br.com.fiap.restaurante.util;

import java.util.Properties;

public class PropriedadesConexao {
	
	public static Properties obterPropriedades() {
		Properties props = new Properties();
		props.put("user", "pf2012");
		props.put("password", "fiap24");
		return props;
	}

}
