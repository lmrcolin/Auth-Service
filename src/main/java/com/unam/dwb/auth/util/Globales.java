package com.unam.dwb.auth.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Globales {
	
	private Globales() {
		throw new IllegalStateException("Esta clase no debe se instanciada");
	}
	
	public static final int COD_SUCCESS = 0;
	public static final int COD_CREATED = 201;
	public static final int COD_DELETED = 202;
	public static final int NO_CONTENT = 204;
	public static final int COD_BAD_REQUEST = 400;
	public static final int COD_PRECONDITION_FAILED = 412;
	public static final int COD_TOO_MANY_REQUESTS = 429;
	public static final int COD_INTERNAL_SERVER_ERROR = 500;
	
	public static final String MSG_BAD_REQUEST = "Petición inválida o malformada";
	public static final String MSG_SUCCESS = "Ejecución exitosa";
	public static final String MSG_DELETED = "Borrado de información exitoso";
	public static final String MSG_CREATED = "Registro de información exitoso";
	public static final String MSG_NO_CONTENT = "Consulta no devuelve información";
	public static final String MSG_INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado en el procesamiento de la petición";
	
	public static final DateTimeFormatter formaterFechaActual = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	public static String formatDate(Date date) {
		LocalDateTime localDateTime = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		return formaterFechaActual.format(localDateTime);
	}

}
