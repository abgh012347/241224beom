package com.example.demo_db.data.dao;

import com.example.demo_db.data.entity.UserEntity;
import com.example.demo_db.data.repository.UserEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDAO {
    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> searchUserInfo(String addr) {
        return this.userEntityRepository.searchUserInfo(addr);
    }
    public List<UserEntity> searchUserInfo(Integer birthYear) {
        return this.userEntityRepository.searchUserInfo(birthYear);
    }
    public List<UserEntity> searchUserInfo(String addr, Integer birthYear) {
        return this.userEntityRepository.searchUserInfo(addr, birthYear);
    }


    public UserEntity saveUserEntity(UserEntity userEntity) {
        boolean flag=this.userEntityRepository.existsById(userEntity.getUserId());
        if(!flag){
            return this.userEntityRepository.save(userEntity);
        }
        return null;
    }

    public UserEntity getUserEntityById(String userId) {
        Optional<UserEntity> userEntity= this.userEntityRepository.findById(userId);
        if(userEntity.isPresent()){
            return userEntity.get();
        }
        throw new EntityNotFoundException("존재하지 않는 아이디입니다.");
    }

    public boolean checkUserId(String userId) {
        return this.userEntityRepository.existsById(userId);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
