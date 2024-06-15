package com.upc.rpamypes.business.entities;

import jakarta.validation.constraints.NotEmpty;

public class PlanillaRequest {

	@NotEmpty(message = "El campo de Fecha Inicial está incompleto.")
	private String fechaIn;
	@NotEmpty(message = "El campo de Fecha Fin está incompleto.")
	private String fechaFn;
	public String getFechaIn() {
		return fechaIn;
	}
	public void setFechaIn(String fechaIn) {
		this.fechaIn = fechaIn;
	}
	public String getFechaFn() {
		return fechaFn;
	}
	public void setFechaFn(String fechaFn) {
		this.fechaFn = fechaFn;
	}
	public PlanillaRequest(String fechaIn, String fechaFn) {
		this.fechaIn = fechaIn;
		this.fechaFn = fechaFn;
	}
	public PlanillaRequest() {
		super();
	}
	@Override
	public String toString() {
		return "PlanillaRequest [fechaIn=" + fechaIn + ", fechaFn=" + fechaFn + "]";
	}
	
	
	
}
