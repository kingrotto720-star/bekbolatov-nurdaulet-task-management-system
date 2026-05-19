package kz.bekbolatovnurdaulet.taskmanagementsystem.repository;

import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTask;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletTaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BekbolatovNurdauletTaskRepository extends JpaRepository<BekbolatovNurdauletTask, Long>,
        JpaSpecificationExecutor<BekbolatovNurdauletTask> {
    Page<BekbolatovNurdauletTask> findByStatus(BekbolatovNurdauletTaskStatus status, Pageable pageable);
}
