package com.proyectos.vn_cambio_rol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectos.vn_cambio_rol.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByNombre(String nombre);
}
