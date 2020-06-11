package com.spintech.testtask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class EntityAlreadyExistException extends RuntimeException {

	private String entityName;
	private String identifier;

	public EntityAlreadyExistException(String entityName, String identifier) {
		this.entityName = entityName;
		this.identifier = identifier;
	}

	@Override
	public String getMessage() {
		return format("%s with ID %s for current user already exist ", entityName, identifier);
	}
}
