package br.com.fiap.restaurante.util;

import java.util.Properties;

public class PropriedadesConexao {
	
	public static Properties obterPropriedades() {
		Properties props = new Properties();
		props.put("user", "usuariooo");
		props.put("password", "senhaaaa");
		return props;
	}

}
