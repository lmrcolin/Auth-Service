package com.unam.dwb.auth.service;

import com.unam.dwb.auth.model.request.UsuarioRequest;
import com.unam.dwb.auth.model.response.AuthAPIResponse;
import jakarta.validation.Valid;

public interface UsuarioService {
	
	public AuthAPIResponse registraUsuario(@Valid UsuarioRequest request);
	public AuthAPIResponse consultaUsuarios(Integer pagina, Integer tam);

}
