package com.proyectos.vn_cambio_rol.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
import com.proyectos.vn_cambio_rol.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;


	public void eliminarRolDeUsuario(long userId) {
		userRoleRepository.deleteByUserId(userId);
	}

	public Set<Role> obtenerRolesDeUsuario(Long userId) {
//		Set<UserRole> userRoles = userRoleRepository.findByUserId(userId)
//				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
//		;
//
//		return userRoles.stream().map(UserRole::getRole).collect(Collectors.toSet());
		// Obtiene el conjunto de roles del usuario
		Set<UserRole> userRoles = userRoleRepository.findByUserId(userId);

		// Verifica si el conjunto está vacío y lanza una excepción si no hay roles
		if (userRoles.isEmpty()) {
			throw new NoSuchElementException("Usuario no encontrado o sin roles asignados");
		}

		// Retorna los roles asociados al usuario
		return userRoles.stream().map(UserRole::getRole).collect(Collectors.toSet());
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<UserRole> getAllUsersAndRoles() {
		List<UserRole> userRoles = userRoleRepository.findAllWithUsersAndRoles();
		return userRoles;
	}

	@Override
	public List<UserRole> updateUserRol(long userId, long roleId, long categoryId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

		Role role = roleRepository.findById(roleId).orElseThrow(() -> new NoSuchElementException("Rol no encontrado"));

		if (categoryId < 1 || categoryId > 4) {
			throw new IllegalArgumentException("Categoría inválida. Debe ser un número entre 1 y 4.");
		}

		eliminarRolDeUsuario(userId);

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		userRole.setCategoryId(categoryId);
		userRole.setAssignedAt(LocalDateTime.now());
		userRoleRepository.save(userRole);

		asignarRolAUsuario(userRole);
		return null;
	}
	
	@Transactional
	public void asignarRolAUsuario(UserRole userRole) {
		userRoleRepository.save(userRole);
	}

}
