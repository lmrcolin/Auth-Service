package com.unam.dwb.auth.model.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class InfoPaginacion implements Serializable{

	private static final long serialVersionUID = 1502769119951843826L;
	
	private Integer paginaActual;
	private Boolean paginaSiguiente;
	private Boolean paginaAnterior;
	private Integer paginasTotales;
	private Integer registrosDevueltos;
	private Long registrosTotales;

}
