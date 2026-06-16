package com.tairitsu.driverincome.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
