package kz.bekbolatovnurdaulet.taskmanagementsystem.repository;

import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BekbolatovNurdauletCommentRepository extends JpaRepository<BekbolatovNurdauletComment, Long> {
    List<BekbolatovNurdauletComment> findByTaskIdOrderByCreatedAtDesc(Long taskId);
}
