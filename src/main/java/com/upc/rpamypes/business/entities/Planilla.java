package com.upc.rpamypes.business.entities;

import java.util.Date;

public class Planilla {

	private int PlanillaId;
	private String Ruta;
	private String Ruta2;
	private Date FechaProcesada;
	private String EjecucionId;
	private String Documento;
	private int DuracionSegundos;
	private String Tipo;

	public Planilla() {
	}

	public Planilla(int planillaId, String ruta, Date fechaProcesada, String ejecucionId, String documento,
			int duracionSegundos, String ruta2, String tipo) {

		PlanillaId = planillaId;
		Ruta = ruta;
		FechaProcesada = fechaProcesada;
		EjecucionId = ejecucionId;
		Documento = documento;
		DuracionSegundos = duracionSegundos;
		Ruta2 = ruta2;
		Tipo = tipo;
	}

	public int getPlanillaId() {
		return PlanillaId;
	}

	public void setPlanillaId(int planillaId) {
		PlanillaId = planillaId;
	}

	public String getRuta() {
		return Ruta;
	}

	public void setRuta(String ruta) {
		Ruta = ruta;
	}

	public Date getFechaProcesada() {
		return FechaProcesada;
	}

	public void setFechaProcesada(Date fechaProcesada) {
		FechaProcesada = fechaProcesada;
	}

	public String getEjecucionId() {
		return EjecucionId;
	}

	public void setEjecucionId(String ejecucionId) {
		EjecucionId = ejecucionId;
	}

	public String getDocumento() {
		return Documento;
	}

	public void setDocumento(String documento) {
		Documento = documento;
	}

	public int getDuracionSegundos() {
		return DuracionSegundos;
	}

	public void setDuracionSegundos(int duracionSegundos) {
		DuracionSegundos = duracionSegundos;
	}

	public String getRuta2() {
		return Ruta2;
	}

	public void setRuta2(String ruta2) {
		Ruta2 = ruta2;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	
	

}
