package com.proyectos.vn_cambio_rol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.vn_cambio_rol.dto.CambiarRolRequest;
import com.proyectos.vn_cambio_rol.entity.User;
import com.proyectos.vn_cambio_rol.service.UserService;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
	@Autowired
	private UserService usuarioService;

	@PutMapping("/{id}/rol")
	public ResponseEntity<User> cambiarRol(@PathVariable Long id, @RequestBody CambiarRolRequest request) {
		User usuarioActualizado = usuarioService.cambiarRol(id, request.getNuevoRol());
		return ResponseEntity.ok(usuarioActualizado);
	}
}
