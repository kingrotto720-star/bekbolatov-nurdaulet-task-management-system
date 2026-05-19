package kz.bekbolatovnurdaulet.taskmanagementsystem.service;

import java.util.List;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletUpdateUserRequestDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.dto.BekbolatovNurdauletUserResponseDto;
import kz.bekbolatovnurdaulet.taskmanagementsystem.exception.BekbolatovNurdauletResourceNotFoundException;
import kz.bekbolatovnurdaulet.taskmanagementsystem.mapper.BekbolatovNurdauletUserMapper;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletRoleRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BekbolatovNurdauletUserService {
    private final BekbolatovNurdauletUserRepository userRepository;
    private final BekbolatovNurdauletRoleRepository roleRepository;
    private final BekbolatovNurdauletUserMapper userMapper;

    public List<BekbolatovNurdauletUserResponseDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public BekbolatovNurdauletUserResponseDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> notFound(id)));
    }

    public BekbolatovNurdauletUserResponseDto update(Long id, BekbolatovNurdauletUpdateUserRequestDto request) {
        var user = userRepository.findById(id).orElseThrow(() -> notFound(id));
        var role = roleRepository.findByName(request.role())
                .orElseThrow(() -> new BekbolatovNurdauletResourceNotFoundException("Role not found: " + request.role()));
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(role);
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw notFound(id);
        }
        userRepository.deleteById(id);
    }

    private BekbolatovNurdauletResourceNotFoundException notFound(Long id) {
        return new BekbolatovNurdauletResourceNotFoundException("User not found with id " + id);
    }
}
