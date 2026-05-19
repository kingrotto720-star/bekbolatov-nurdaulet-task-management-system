package kz.bekbolatovnurdaulet.taskmanagementsystem.repository;

import java.util.Optional;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BekbolatovNurdauletUserRepository extends JpaRepository<BekbolatovNurdauletUser, Long> {
    Optional<BekbolatovNurdauletUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
