package com.proyectos.vn_cambio_rol.repository;

import com.proyectos.vn_cambio_rol.entity.User;
import com.proyectos.vn_cambio_rol.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // Buscar una relación de rol de usuario por el userId y roleId
    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    // Buscar todos los roles de un usuario específico
    Set<UserRole> findByUserId(Long userId);

    // Buscar todos los usuarios con un rol específico
    Set<UserRole> findByRoleId(Long roleId);
    
    Set<UserRole> findByUser(User user); 
    
    
    @Query("SELECT ur FROM UserRole ur JOIN FETCH ur.user u JOIN FETCH ur.role r")
    List<UserRole> findAllWithUsersAndRoles();
    
    @Transactional
    void deleteByUserId(Long userId);
}
