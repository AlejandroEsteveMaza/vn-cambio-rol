package com.proyectos.vn_cambio_rol;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyectos.vn_cambio_rol.entity.Role;
import com.proyectos.vn_cambio_rol.entity.User;
import com.proyectos.vn_cambio_rol.entity.UserRole;
import com.proyectos.vn_cambio_rol.repository.RoleRepository;
import com.proyectos.vn_cambio_rol.repository.UserRepository;
import com.proyectos.vn_cambio_rol.repository.UserRoleRepository;

@SpringBootApplication
public class VnCambioDeRolApplication {

	public static void main(String[] args) {
		SpringApplication.run(VnCambioDeRolApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(RoleRepository roleRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
		return args -> {
			List<Role> roles = Arrays.asList(new Role(null, "ADMIN", new HashSet<>()),
					new Role(null, "USER", new HashSet<>()), new Role(null, "MODERATOR", new HashSet<>()));
			roleRepository.saveAll(roles);

			//User user = new User(null, "Alejandro", "123", true, new HashSet<>());
			User user = new User(null, "Alejandro", "123", true);
			userRepository.save(user);

			// Asociar roles con el usuario en la tabla UserRole
			Role adminRole = roles.get(0); // Asignamos ADMIN
			Role userRole = roles.get(1); // Asignamos USER

			// Crear registros de UserRole para asociar al usuario con roles
			UserRole userRole1 = new UserRole();
			userRole1.setUser(user);
			userRole1.setRole(adminRole);
			userRole1.setAssignedAt(LocalDateTime.now());

			UserRole userRole2 = new UserRole();
			userRole2.setUser(user);
			userRole2.setRole(userRole);
			userRole2.setAssignedAt(LocalDateTime.now());

			// Guardar las asociaciones en la tabla user_roles
			userRoleRepository.saveAll(Arrays.asList(userRole1, userRole2));
		};
	}

}
