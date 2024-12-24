package com.example.demo_db.data.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String userId;
    private String userName;
    private String password;
    private int birthYear;
    private String addr;
    private String mobile;
    private short height;
    private LocalDate joinDate;
}
