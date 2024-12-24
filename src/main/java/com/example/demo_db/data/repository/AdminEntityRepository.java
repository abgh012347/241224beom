package com.example.demo_db.data.repository;

import com.example.demo_db.data.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminEntityRepository extends JpaRepository<AdminEntity, String> {
}
