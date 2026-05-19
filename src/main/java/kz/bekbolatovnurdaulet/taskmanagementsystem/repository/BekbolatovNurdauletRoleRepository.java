package kz.bekbolatovnurdaulet.taskmanagementsystem.repository;

import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BekbolatovNurdauletRoleRepository extends JpaRepository<BekbolatovNurdauletRole, Long> {
    Optional<BekbolatovNurdauletRole> findByName(String name);
}
