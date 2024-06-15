package com.upc.rpamypes.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upc.rpamypes.business.entities.Planilla;
import com.upc.rpamypes.business.entities.service.RpamypesService;

@RestController
@RequestMapping("planillas-rest")
class RpamypesRestController {

	private RpamypesService service;

	public RpamypesRestController(RpamypesService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public List<Planilla> getPlanillas() {
		return service.GetPlanillas("A");
	}
	
	@PostMapping(produces = "application/json")
	public int postPlanillas(@RequestBody Planilla planilla) {
		return service.PostPlanilla(planilla);
	}


}
