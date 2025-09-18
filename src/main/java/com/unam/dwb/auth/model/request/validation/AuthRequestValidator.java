package com.unam.dwb.auth.model.request.validation;

import org.springframework.util.StringUtils;

import com.unam.dwb.auth.model.request.AuthRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthRequestValidator implements ConstraintValidator<AuthRequestConstraint, AuthRequest>{

	@Override
	public boolean isValid(AuthRequest request, ConstraintValidatorContext context) {
		return StringUtils.hasLength(request.getCorreo()) || StringUtils.hasLength(request.getNombreUsuario());
	}

}
