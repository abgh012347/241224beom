package com.example.demo_db.service;


import com.example.demo_db.data.dao.AdminDAO;
import com.example.demo_db.data.entity.AdminEntity;
import com.example.demo_db.data.repository.UserEntityRepository;
import com.example.demo_db.data.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminLoginService implements UserDetailsService {
    private final AdminDAO adminDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity adminEntity =this.adminDAO.getAdminById(username);
        if(adminEntity==null){
            throw new UsernameNotFoundException("admin not found");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(adminEntity.getUsername(), adminEntity.getPassword(), grantedAuthorities);
    }

}

