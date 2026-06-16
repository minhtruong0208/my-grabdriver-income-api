package com.tairitsu.driverincome.service;

import com.tairitsu.driverincome.dto.request.LoginDTORequest;
import com.tairitsu.driverincome.dto.request.RegisterDTORequest;
import com.tairitsu.driverincome.dto.response.AuthDTOResponse;

public interface AuthService {
    AuthDTOResponse register(RegisterDTORequest request);
    AuthDTOResponse login(LoginDTORequest request);
}