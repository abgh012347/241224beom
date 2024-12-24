package com.example.demo_db.data.dao;

import com.example.demo_db.data.entity.AdminEntity;
import com.example.demo_db.data.repository.AdminEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminDAO {
    private final AdminEntityRepository adminEntityRepository;

    public AdminEntity saveAdmin(AdminEntity adminEntity) {
        return adminEntityRepository.save(adminEntity);
    }

    public AdminEntity getAdminById(String adminname) {
        Optional<AdminEntity> adminEntity=this.adminEntityRepository.findById(adminname);
        if(adminEntity.isPresent()) {
            return adminEntity.get();
        }
        return null;
    }

    public boolean existAdmin(String adminname) {
        return this.adminEntityRepository.existsById(adminname);
    }

}
