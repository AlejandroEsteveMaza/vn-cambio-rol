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

	@Transactional
	public void asignarRolAUsuario(Long userId, Long roleId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

		Role role = roleRepository.findById(roleId).orElseThrow(() -> new NoSuchElementException("Rol no encontrado"));

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

//	public Set<User> obtenerUsuariosDeRol(Long roleId) {
//		Role role = roleRepository.findById(roleId).orElseThrow(() -> new NoSuchElementException("Rol no encontrado"));
//
//		return role.getUserRoles().stream().map(UserRole::getUser).collect(Collectors.toSet());
//	}

	public void updateUserRol(Long userId, Long oldRoleId, Long newRoleId) {
		eliminarRolDeUsuario(userId, oldRoleId);

		asignarRolAUsuario(userId, newRoleId);
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

}
