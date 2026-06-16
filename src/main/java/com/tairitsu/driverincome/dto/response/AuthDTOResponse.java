package com.tairitsu.driverincome.dto.response;

import com.tairitsu.driverincome.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTOResponse {
    private String token;
    private String username;
    private UserType userType;
}
