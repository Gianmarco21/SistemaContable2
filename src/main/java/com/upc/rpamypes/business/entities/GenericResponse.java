package com.upc.rpamypes.business.entities;

public class GenericResponse {

	private String message;
	private String error;

	public GenericResponse(String message, String error) {
		this.message = message;
		this.error = error;
	}

	public GenericResponse() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
