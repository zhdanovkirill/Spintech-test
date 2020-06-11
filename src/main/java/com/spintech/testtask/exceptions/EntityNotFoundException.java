package com.spintech.testtask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private String entityName;
	private String identifier;

	public EntityNotFoundException(String entityName, String identifier) {
		this.entityName = entityName;
		this.identifier = identifier;
	}

	@Override
	public String getMessage() {
		return format("%s with ID = %s not found", entityName, identifier);
	}
}
