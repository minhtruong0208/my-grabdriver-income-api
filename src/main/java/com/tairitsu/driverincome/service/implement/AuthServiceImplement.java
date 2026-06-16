package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.request.LoginDTORequest;
import com.tairitsu.driverincome.dto.request.RegisterDTORequest;
import com.tairitsu.driverincome.dto.response.AuthDTOResponse;
import com.tairitsu.driverincome.entity.User;
import com.tairitsu.driverincome.entity.UserType;
import com.tairitsu.driverincome.repository.UserRepository;
import com.tairitsu.driverincome.service.AuthService;
import com.tairitsu.driverincome.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles authentication use cases such as register and login.
 *
 * <p>Public registration always creates a normal USER account.
 * ADMIN accounts must not be created from the public register endpoint.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthDTOResponse register(RegisterDTORequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setUserType(UserType.USER);
        User savedUser = userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthDTOResponse(token, savedUser.getUsername(), savedUser.getUserType());
    }
    @Override
    public AuthDTOResponse login(LoginDTORequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthDTOResponse(token, user.getUsername(), user.getUserType());
    }
}