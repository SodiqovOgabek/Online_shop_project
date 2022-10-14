package com.example.online_shop_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.online_shop_project.domains.AuthRole;

public interface AuthPermissionRepository extends JpaRepository<AuthRole, Long> {
}
