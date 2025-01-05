package com.proyectos.vn_cambio_rol.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.proyectos.vn_cambio_rol.entity.Role;
import com.proyectos.vn_cambio_rol.entity.User;
import com.proyectos.vn_cambio_rol.entity.UserRole;

public interface UserService {
	public Set<Role> obtenerRolesDeUsuario(Long usuarioId);
	public List<User> getAllUsers();
	
	public List<UserRole> getAllUsersAndRoles();
	
	public List<UserRole> updateUserRol();
	
}
