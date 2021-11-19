package com.takaka.takakaProducer.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takaka.takakaProducer.kafka.IndicatorsProducer;
import com.takaka.takakaProducer.model.IndicatorsModel;
import com.takaka.takakaProducer.repository.IndicatorsRepository;
import com.takaka.takakaProducer.repository.UserIndRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/indicators")
public class IndicatorsController {
	
	@Autowired
	IndicatorsRepository indicatorsRepository;
	
	@Autowired
	UserIndRepository userRepository;
	
	@Autowired
	IndicatorsProducer indicatorsProducer;
	
	@Autowired
	ObjectMapper mapper = new ObjectMapper();
	
	@GetMapping()
	@ApiOperation(value = "Lista de indicators")
	public ResponseEntity<List<IndicatorsModel>> findAll() {

		List<IndicatorsModel> indicators = indicatorsRepository.findAll();
		return ResponseEntity.ok(indicators);
	}
	@GetMapping("/{id}")
	@ApiOperation(value = "indicator por ID")
	public ResponseEntity<IndicatorsModel> findById(@PathVariable("id") long id) {

		IndicatorsModel indicators = indicatorsRepository.findById(id).get();
		return ResponseEntity.ok(indicators);
	}

	@PostMapping()
	@ApiOperation(value = "Salvar novo indicator")
	public ResponseEntity<?> save(@RequestBody @Valid IndicatorsModel indicatorsModel, BindingResult bindingResult) throws JsonProcessingException {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		IndicatorsModel indicators = indicatorsRepository.save(indicatorsModel);
		String jsoninstring = mapper.writeValueAsString(indicators);
		
		indicatorsProducer.publish(indicators.getIndicatorsId().toString(), jsoninstring);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(indicators.getUserId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alteração de indicator")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody @Valid IndicatorsModel indicatorsModel,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		indicatorsModel.setIndicatorsId((long)id);
		indicatorsRepository.save(indicatorsModel);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir indicator")
	public ResponseEntity<?> deleteById(@PathVariable("id") long id) {

		indicatorsRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/criarind")
	@ApiOperation(value = "criar")
	public void criar() {
		try {
			IndicatorsModel indicators = new IndicatorsModel((long) 7,"goal", "target", "indicator", "series_code", "series_description", "geo_area_code", "geo_area_name", "time_period", "value", userRepository.findById((long) 1).get());
			indicatorsRepository.save(indicators);
			String jsoninstring = mapper.writeValueAsString(indicators);
			System.out.println(jsoninstring);
			IndicatorsModel indicators2 = mapper.readValue(jsoninstring, IndicatorsModel.class);
			System.out.println(indicators2.toString());
			indicatorsRepository.save(indicators2);
			//StringJsonMessageConverter.class;
			indicatorsProducer.publish(indicators.getIndicatorsId().toString(), jsoninstring);
		} catch (Exception e) {
			System.out.println("erro: "+ e);
		}
		
	}

}
