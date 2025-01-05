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
			List<Role> roles = Arrays.asList(new Role(null, "Hostelero"),
					new Role(null, "Empleado"), new Role(null, "HosteleroNOCE"), new Role(null, "EmpleadoNOCE"));
			roleRepository.saveAll(roles);

			User user = new User(null, "Alejandro", "123", true);
			userRepository.save(user);

			Role empleadoRole = roles.get(1);

			UserRole userRole1 = new UserRole();
			userRole1.setUser(user);
			userRole1.setRole(empleadoRole);
			userRole1.setCategoryID(2);
			userRole1.setAssignedAt(LocalDateTime.now());


			userRoleRepository.saveAll(Arrays.asList(userRole1));
		};
	}

}
