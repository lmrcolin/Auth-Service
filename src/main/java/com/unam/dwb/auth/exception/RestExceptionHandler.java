package com.unam.dwb.auth.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.unam.dwb.auth.model.response.AuthAPIResponse;
import com.unam.dwb.auth.util.Globales;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
	
	private static final String INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado en el manejo de la petición";
	private static final String INTERNAL_SERVER_DETAIL = "Excepción atrapada: {}";
	
	private static final String MALFORMED_REQUEST = "La solicitud se encuentra malformada";
	private static final String MALFORMED_DETAIL = "La solicitud no cumplió con la especificación requerida: {}";

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthAPIResponse.class)))
	protected ResponseEntity<AuthAPIResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

		AuthAPIResponse response = new AuthAPIResponse();

		log.error(MALFORMED_REQUEST);
		generaElementosComunesRespuesta(ex, response); 
		log.error(MALFORMED_DETAIL, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthAPIResponse.class)))
	protected ResponseEntity<AuthAPIResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {

		AuthAPIResponse response = new AuthAPIResponse();

		log.error(MALFORMED_REQUEST);
		generaElementosComunesRespuesta(ex, response);
		log.error(MALFORMED_DETAIL, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthAPIResponse.class)))
	protected ResponseEntity<AuthAPIResponse> handleMissingParameterException(HttpMessageNotReadableException ex, WebRequest request) {

		AuthAPIResponse response = new AuthAPIResponse();

		log.error(MALFORMED_REQUEST);
		generaElementosComunesRespuesta(ex, response);
		log.error(MALFORMED_DETAIL, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthAPIResponse.class)))
	protected ResponseEntity<AuthAPIResponse> handleMissingParameterException(MissingServletRequestParameterException ex, WebRequest request) {

		AuthAPIResponse response = new AuthAPIResponse();

		log.error(MALFORMED_REQUEST);
		generaElementosComunesRespuesta(ex, response);
		log.error(MALFORMED_DETAIL, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthAPIResponse.class)))
	protected ResponseEntity<AuthAPIResponse> handleGlobalException(Exception ex, WebRequest request) {

		AuthAPIResponse response = new AuthAPIResponse();

		log.error(INTERNAL_SERVER_ERROR);
		generaElementosComunesRespuesta(ex, response);
		log.error(INTERNAL_SERVER_DETAIL, ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void generaElementosComunesRespuesta(HttpMessageNotReadableException ex, AuthAPIResponse response) {		
		response.setFechaHora(Globales.formatDate(new Date()));
		response.setDetalles(Arrays.asList(Globales.MSG_BAD_REQUEST, ex.getMessage()));		
	}
	
	private void generaElementosComunesRespuesta(Exception ex, AuthAPIResponse response) {		
		response.setFechaHora(Globales.formatDate(new Date()));
		response.setDetalles(Arrays.asList(Globales.MSG_INTERNAL_SERVER_ERROR, ex.getMessage()));		
	}
	
	private void generaElementosComunesRespuesta(MissingServletRequestParameterException ex, AuthAPIResponse response) {		
		response.setFechaHora(Globales.formatDate(new Date()));
		response.setDetalles(Arrays.asList(Globales.MSG_BAD_REQUEST, ex.getMessage()));		
	}
	
	private void generaElementosComunesRespuesta(ConstraintViolationException ex, AuthAPIResponse response) {		
		response.setFechaHora(Globales.formatDate(new Date()));
		response.setDetalles(Arrays.asList(Globales.MSG_BAD_REQUEST, ex.getMessage()));		
	}

	private void generaElementosComunesRespuesta(MethodArgumentNotValidException ex, AuthAPIResponse response) {

		List<FieldError> fieldErrors = new ArrayList<>(ex.getBindingResult().getFieldErrors());

		fieldErrors.sort( (f1, f2) -> {
		    if (f1.getField().equals(f2.getField())) {
		        return 0;
		    }
		    return f1.getField().compareToIgnoreCase(f2.getField());
		});
		
		ArrayList<String> mensajesError = new ArrayList<>();
		mensajesError.add(Globales.MSG_BAD_REQUEST);
		StringBuilder sb;
		for(FieldError field: fieldErrors) {
			sb = new StringBuilder();
			sb.append(field.getField());
			sb.append(": ");
			sb.append(field.getDefaultMessage());
			sb.append(". ");		
			mensajesError.add(sb.toString());
		}
		
		response.setFechaHora(Globales.formatDate(new Date()));
		response.setDetalles(mensajesError);
	}

}
