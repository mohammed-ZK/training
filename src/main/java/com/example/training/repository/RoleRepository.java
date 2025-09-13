package com.example.training.repository;

import com.example.training.entity.Role;
import com.example.training.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional< Role > findByName(RoleEnum name);
}
