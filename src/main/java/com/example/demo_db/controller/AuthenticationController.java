package com.example.demo_db.controller;

import com.example.demo_db.data.dto.AdminDTO;
import com.example.demo_db.data.entity.AdminEntity;
import com.example.demo_db.exception.CookdbAuthenticationExceptionHandler;
import com.example.demo_db.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthenticationController {
    private final AdminService adminService;

    @GetMapping("/csrf-token")
    public ResponseEntity<Map<String, String>> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", csrfToken.getToken());
        return ResponseEntity.ok(tokenMap);
    }

    @PostMapping(value="/admin-join")
    public ResponseEntity<String> join(@RequestBody AdminDTO adminDTO) {
        AdminDTO saveAdminDTO = this.adminService.saveAdmin(adminDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("가입성공");
    }

}
