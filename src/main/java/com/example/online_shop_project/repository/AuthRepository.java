package com.example.online_shop_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.online_shop_project.domains.AuthUser;

import java.util.Optional;
@Repository
public interface AuthRepository extends JpaRepository<AuthUser, Long> {
    @Query("select p from AuthUser p where p.username = ?1")
    Optional<AuthUser> findByUsername(String username);


}
