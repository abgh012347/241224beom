package com.example.demo_db.service;

import com.example.demo_db.data.dao.UserDAO;
import com.example.demo_db.data.dto.UserDTO;
import com.example.demo_db.data.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;


    public List<UserDTO> searchUserInfo(String addr){
        List<UserEntity> userEntityList=this.userDAO.searchUserInfo(addr);
        List<UserDTO> userDTOList=new ArrayList<UserDTO>();
        for(UserEntity userEntity:userEntityList){
            UserDTO userDTO=UserDTO.builder()
                    .userId(userEntity.getUserId())
                    .userName(userEntity.getUserName())
                    .birthYear(userEntity.getBirthYear())
                    .mobile(userEntity.getMobile1()+userEntity.getMobile2())
                    .height(userEntity.getHeight())
                    .joinDate(userEntity.getJoinDate())
                    .build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
    public List<UserDTO> searchUserInfo(Integer birthYear){
        List<UserEntity> userEntityList=this.userDAO.searchUserInfo(birthYear);
        List<UserDTO> userDTOList=new ArrayList<UserDTO>();
        for(UserEntity userEntity:userEntityList){
            UserDTO userDTO=UserDTO.builder()
                    .userId(userEntity.getUserId())
                    .userName(userEntity.getUserName())
                    .birthYear(userEntity.getBirthYear())
                    .mobile(userEntity.getMobile1()+userEntity.getMobile2())
                    .height(userEntity.getHeight())
                    .joinDate(userEntity.getJoinDate())
                    .build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
    public List<UserDTO> searchUserInfo(String addr, Integer birthYear){
        List<UserEntity> userEntityList=this.userDAO.searchUserInfo(addr, birthYear);
        List<UserDTO> userDTOList=new ArrayList<UserDTO>();
        for(UserEntity userEntity:userEntityList){
            UserDTO userDTO=UserDTO.builder()
                    .userId(userEntity.getUserId())
                    .userName(userEntity.getUserName())
                    .birthYear(userEntity.getBirthYear())
                    .mobile(userEntity.getMobile1()+userEntity.getMobile2())
                    .height(userEntity.getHeight())
                    .joinDate(userEntity.getJoinDate())
                    .build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public UserDTO joinUserInfo(UserDTO userDTO){

        UserEntity userEntity=UserEntity.builder()
                .userId(userDTO.getUserId())
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .mobile1(userDTO.getMobile()!=null? userDTO.getMobile().substring(0,3):null)
                .mobile2(userDTO.getMobile()!=null? userDTO.getMobile().substring(3):null)
                .birthYear(userDTO.getBirthYear())
                .addr(userDTO.getAddr())
                .height(userDTO.getHeight())
                .joinDate(userDTO.getJoinDate())
                .build();

        UserEntity saveUserEntity=this.userDAO.saveUserEntity(userEntity);
        if(saveUserEntity!=null){
            UserDTO saveUserDTO=UserDTO.builder()
                    .userId(saveUserEntity.getUserId())
                    .userName(saveUserEntity.getUserName())
                    .mobile(saveUserEntity.getMobile1()!=null?saveUserEntity.getMobile1()+saveUserEntity.getMobile2():null)
                    .birthYear(saveUserEntity.getBirthYear())
                    .addr(saveUserEntity.getAddr())
                    .height(saveUserEntity.getHeight())
                    .joinDate(saveUserEntity.getJoinDate())
                    .build();
            return saveUserDTO;
        }
        return null;
    }

    public boolean checkUserId(String userId) {
        return this.userDAO.checkUserId(userId);
    }

}
