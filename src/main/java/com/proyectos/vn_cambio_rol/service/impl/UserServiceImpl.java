package com.proyectos.vn_cambio_rol.service.impl;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectos.vn_cambio_rol.entity.Role;
import com.proyectos.vn_cambio_rol.entity.User;
import com.proyectos.vn_cambio_rol.entity.UserRole;
import com.proyectos.vn_cambio_rol.repository.RoleRepository;
import com.proyectos.vn_cambio_rol.repository.UserRepository;
import com.proyectos.vn_cambio_rol.repository.UserRoleRepository;

@Service
public class UserServiceImpl {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Transactional
	public void asignarRolAUsuario(Long userId, Long roleId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

		Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new NoSuchElementException("Rol no encontrado"));

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		userRole.setAssignedAt(LocalDateTime.now());

		userRoleRepository.save(userRole);
	}

	public void eliminarRolDeUsuario(Long userId, Long roleId) {
		UserRole userRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId)
				.orElseThrow(() -> new NoSuchElementException("Relación de rol de usuario no encontrada"));

		userRoleRepository.delete(userRole);
	}

	public Set<Role> obtenerRolesDeUsuario(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

		return user.getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toSet());
	}

	public Set<User> obtenerUsuariosDeRol(Long roleId) {
		Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new NoSuchElementException("Rol no encontrado"));

		return role.getUserRoles().stream().map(UserRole::getUser).collect(Collectors.toSet());
	}

	public void cambiarRolDeUsuario(Long userId, Long oldRoleId, Long newRoleId) {
		eliminarRolDeUsuario(userId, oldRoleId);

		asignarRolAUsuario(userId, newRoleId);
	}

}
