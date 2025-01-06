package com.proyectos.vn_cambio_rol.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.vn_cambio_rol.dto.CambiarRolRequest;
import com.proyectos.vn_cambio_rol.entity.Role;
import com.proyectos.vn_cambio_rol.entity.User;
import com.proyectos.vn_cambio_rol.entity.UserRole;
import com.proyectos.vn_cambio_rol.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	 @PutMapping("/{id}/rol")
	 public ResponseEntity<String> cambiarRol(
	     @PathVariable Long id,
	     @RequestBody CambiarRolRequest request) {

		 userService.updateUserRol(id, request.getRoleId(), request.getCategoryId());
	     return ResponseEntity.ok("Rol actualizado correctamente");
	 }


	@GetMapping
	public List<UserRole> getAllUsersWithRoles() {
		return userService.getAllUsersAndRoles();
	}
}
