package kz.bekbolatovnurdaulet.taskmanagementsystem.repository;

import java.util.List;
import kz.bekbolatovnurdaulet.taskmanagementsystem.entity.BekbolatovNurdauletComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BekbolatovNurdauletCommentRepository extends JpaRepository<BekbolatovNurdauletComment, Long> {
    List<BekbolatovNurdauletComment> findByTaskIdOrderByCreatedAtDesc(Long taskId);
}
