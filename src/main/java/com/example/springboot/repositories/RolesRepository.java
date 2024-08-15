package com.example.springboot.repositories;

import com.example.springboot.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RolesRepository extends JpaRepository<RoleModel, UUID> {
    @Query("SELECT r FROM RoleModel r WHERE r.roleName = 'ROLE_ADMIN'")
    RoleModel findByRoleAdmin();

    @Query("SELECT r FROM RoleModel r WHERE r.roleName = 'ROLE_USER'")
    RoleModel findByRoleUser();
}
