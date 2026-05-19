package kz.bekbolatovnurdaulet.taskmanagementsystem.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletProject;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletRole;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTask;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTaskPriority;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTaskStatus;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletUser;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletProjectRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletRoleRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletTaskRepository;
import kz.bekbolatovnurdaulet.taskmanagementsystem.repository.BekbolatovNurdauletUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BekbolatovNurdauletDataInitializer {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner bekbolatovNurdauletDemoData(
            BekbolatovNurdauletRoleRepository roleRepository,
            BekbolatovNurdauletUserRepository userRepository,
            BekbolatovNurdauletProjectRepository projectRepository,
            BekbolatovNurdauletTaskRepository taskRepository) {
        return args -> {
            var adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(BekbolatovNurdauletRole.builder().name("ADMIN").build()));
            var userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(BekbolatovNurdauletRole.builder().name("USER").build()));
            var admin = userRepository.findByEmail("admin@example.com")
                    .orElseGet(() -> userRepository.save(BekbolatovNurdauletUser.builder()
                            .name("Bekbolatov Nurdaulet Admin")
                            .email("admin@example.com")
                            .password(passwordEncoder.encode("admin123"))
                            .role(adminRole)
                            .createdAt(LocalDateTime.now())
                            .build()));
            var user = userRepository.findByEmail("user@example.com")
                    .orElseGet(() -> userRepository.save(BekbolatovNurdauletUser.builder()
                            .name("Demo User")
                            .email("user@example.com")
                            .password(passwordEncoder.encode("user123"))
                            .role(userRole)
                            .createdAt(LocalDateTime.now())
                            .build()));
            if (projectRepository.count() == 0) {
                var project = projectRepository.save(BekbolatovNurdauletProject.builder()
                        .name("Final Backend Project")
                        .description("Demo task management project")
                        .createdAt(LocalDateTime.now())
                        .build());
                taskRepository.save(BekbolatovNurdauletTask.builder()
                        .title("Build Spring Boot API")
                        .description("Implement REST API, JWT, Swagger, Docker")
                        .status(BekbolatovNurdauletTaskStatus.IN_PROGRESS)
                        .priority(BekbolatovNurdauletTaskPriority.HIGH)
                        .deadline(LocalDate.now().plusDays(7))
                        .createdAt(LocalDateTime.now())
                        .project(project)
                        .assignedUser(user)
                        .build());
                taskRepository.save(BekbolatovNurdauletTask.builder()
                        .title("Review admin features")
                        .description("Check protected endpoints")
                        .status(BekbolatovNurdauletTaskStatus.TODO)
                        .priority(BekbolatovNurdauletTaskPriority.MEDIUM)
                        .deadline(LocalDate.now().plusDays(10))
                        .createdAt(LocalDateTime.now())
                        .project(project)
                        .assignedUser(admin)
                        .build());
            }
        };
    }
}
