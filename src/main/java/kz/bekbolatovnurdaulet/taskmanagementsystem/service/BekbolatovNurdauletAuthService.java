package kz.bekbolatovnurdaulet.taskmanagementsystem.service;

import java.time.LocalDateTime;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAuthDtos.BekbolatovNurdauletAuthResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAuthDtos.BekbolatovNurdauletLoginRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletAuthDtos.BekbolatovNurdauletRegisterRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletRole;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletUser;
import kz.bekbolatovnurdaulet.taskmanagementsystem.exception.BekbolatovNurdauletBadRequestException;
import kz.bekbolatovnurdaulet.taskmanagementsystem.mapper.BekbolatovNurdauletUserMapper;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletRoleRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletUserRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.security.BekbolatovNurdauletJwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BekbolatovNurdauletAuthService {
    private final BekbolatovNurdauletUserRepository userRepository;
    private final BekbolatovNurdauletRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BekbolatovNurdauletJwtService jwtService;
    private final BekbolatovNurdauletUserMapper userMapper;

    public BekbolatovNurdauletAuthResponseDto register(BekbolatovNurdauletRegisterRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BekbolatovNurdauletBadRequestException("Email is already registered");
        }
        BekbolatovNurdauletRole role = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(BekbolatovNurdauletRole.builder().name("USER").build()));
        var user = BekbolatovNurdauletUser.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();
        user = userRepository.save(user);
        log.info("Registered user {}", user.getEmail());
        String token = jwtService.generateToken(User.withUsername(user.getEmail())
                .password(user.getPassword()).roles(user.getRole().getName()).build());
        return new BekbolatovNurdauletAuthResponseDto(token, "Bearer", userMapper.toDto(user));
    }

    public BekbolatovNurdauletAuthResponseDto login(BekbolatovNurdauletLoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BekbolatovNurdauletBadRequestException("Invalid credentials"));
        String token = jwtService.generateToken(User.withUsername(user.getEmail())
                .password(user.getPassword()).roles(user.getRole().getName()).build());
        log.info("User {} logged in", user.getEmail());
        return new BekbolatovNurdauletAuthResponseDto(token, "Bearer", userMapper.toDto(user));
    }
}
