package com.tairitsu.driverincome.controller;

import com.tairitsu.driverincome.dto.request.LoginDTORequest;
import com.tairitsu.driverincome.dto.request.RegisterDTORequest;
import com.tairitsu.driverincome.dto.response.AuthDTOResponse;
import com.tairitsu.driverincome.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthDTOResponse> register(@Valid @RequestBody RegisterDTORequest request) {
        AuthDTOResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthDTOResponse> login(@Valid @RequestBody LoginDTORequest request) {
        AuthDTOResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}