package com.example.demo_db.service;

import com.example.demo_db.data.dao.AdminDAO;
import com.example.demo_db.data.dto.AdminDTO;
import com.example.demo_db.data.entity.AdminEntity;
import com.example.demo_db.exception.CookdbAuthenticationExceptionHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminDAO adminDAO;
    private final PasswordEncoder passwordEncoder;

    public AdminDTO saveAdmin(AdminDTO adminDTO) {
        if(!adminDTO.getAuthenNumber().equals("1234")){
            throw new CookdbAuthenticationExceptionHandler("관리자 인증변호가 틀립니다.");
        }

        AdminEntity adminEntity = AdminEntity.builder()
                .username(adminDTO.getAdminName())
                .password(this.passwordEncoder.encode(adminDTO.getAdminPassword()))
                .build();
        AdminEntity saveAdminEntity=this.adminDAO.saveAdmin(adminEntity);

        AdminDTO saveAdminDTO=AdminDTO.builder()
                .adminName(saveAdminEntity.getUsername())
                .adminPassword(saveAdminEntity.getPassword())
                .build();
        return saveAdminDTO;
    }
}
