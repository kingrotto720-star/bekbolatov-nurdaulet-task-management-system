package kz.bekbolatovnurdaulet.taskmanagementsystem.repository;

import java.util.Optional;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BekbolatovNurdauletRoleRepository extends JpaRepository<BekbolatovNurdauletRole, Long> {
    Optional<BekbolatovNurdauletRole> findByName(String name);
}
