package com.example.demo_db.controller;

import com.example.demo_db.data.dto.UserDTO;
import com.example.demo_db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/search-userinfo/addr")
    public ResponseEntity<List<UserDTO>> searchUserInfo(@RequestParam String addr) {
        List<UserDTO> userDTOList=this.userService.searchUserInfo(addr);
        return ResponseEntity.ok(userDTOList);
    };

    @GetMapping(value="/search-userinfo/birthyear")
    public ResponseEntity<List<UserDTO>> searchUserInfo(@RequestParam Integer birthyear) {
        List<UserDTO> userDTOList=this.userService.searchUserInfo(birthyear);
        return ResponseEntity.ok(userDTOList);

    };

    @GetMapping(value="/search-userinfo/addrbirthyear")
    public ResponseEntity<List<UserDTO>> searchUserInfo(@RequestParam String addr, @RequestParam Integer birthyear) {
        List<UserDTO> userDTOList=this.userService.searchUserInfo(addr, birthyear);
        return ResponseEntity.ok(userDTOList);
    };

    @GetMapping(value="/check-id/{userid}")
    public ResponseEntity<String> checkUser(@PathVariable("userid") String userid) {
        if(this.userService.checkUserId(userid)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 아이디 입니다.");
        }else {
            return ResponseEntity.ok("사용할수 있는 아이디입니다.");
        }
    }

    @PostMapping(value = "/join-userinfo")
    public ResponseEntity<Object> joinUserInfo(@RequestBody UserDTO userDTO) {
        UserDTO joinUserInfo = this.userService.joinUserInfo(userDTO);
        if (joinUserInfo != null) {
            return ResponseEntity.ok(joinUserInfo);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디가 중복되었습니다.");
        }
    }

}
