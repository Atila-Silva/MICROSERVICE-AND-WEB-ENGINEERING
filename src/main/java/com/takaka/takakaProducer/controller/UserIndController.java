package com.takaka.takakaProducer.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.takaka.takakaProducer.model.UserIndModel;
import com.takaka.takakaProducer.repository.UserIndRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserIndController {
	
	@Autowired
	UserIndRepository userRepository;
	
	@GetMapping()
	@ApiOperation(value = "Lista de usuario")
	public ResponseEntity<List<UserIndModel>> findAll() {

		List<UserIndModel> Avaliacoes = userRepository.findAll();
		return ResponseEntity.ok(Avaliacoes);
	}
	@GetMapping("/{id}")
	@ApiOperation(value = "usuario por ID")
	public ResponseEntity<UserIndModel> findById(@PathVariable("id") long id) {

		UserIndModel userInd = userRepository.findById(id).get();
		return ResponseEntity.ok(userInd);
	}

	@PostMapping()
	@ApiOperation(value = "Salvar novo usuario")
	public ResponseEntity<?> save(@RequestBody @Valid UserIndModel userIndModel, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		UserIndModel userInd = userRepository.save(userIndModel);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userInd.getUserId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Alteração de usuario")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody @Valid UserIndModel userIndModel,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		userIndModel.setUserId(id);
		userRepository.save(userIndModel);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir usuario")
	public ResponseEntity<?> deleteById(@PathVariable("id") long id) {

		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/criaravaliacao")
	@ApiOperation(value = "criar")
	public void criar() {
		userRepository.save(new UserIndModel((long) 1, "Ian", "Bosonista", "Ian.Bosonista@gmail.com", "Bonso123", 2));
		
	}

}
