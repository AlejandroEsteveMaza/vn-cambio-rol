package com.proyectos.vn_cambio_rol.service;

import com.proyectos.vn_cambio_rol.entity.User;

public interface UserService {
	public User cambiarRol(Long usuarioId, String nuevoRol);
}
