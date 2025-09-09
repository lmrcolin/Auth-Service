package com.unam.dwb.auth.model.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class AuthAPIResponse {
	
	private String token;
	private String fechaHora;
	private List<String> detalles;
	private List<UsuarioResponse> usuarios;
	
	private InfoPaginacion infoPaginacion;
	
	@Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
        	log.error("Excepción atrapada al serializar objeto: " + e.getMessage());
        	return super.toString(); 
        }
    }

	public void agregaUsuario(UsuarioResponse usuario) {
		if(this.usuarios == null)
			this.usuarios = new ArrayList<>();
		
		this.usuarios.add(usuario);
		
	}

}
