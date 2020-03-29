package ua.iamiluxa.timetrackingspringproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.iamiluxa.timetrackingspringproject.entity.Request;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request, Long> {
    List<Request> findByActivityIdAndUserId(Long activityId, Long userId);
}
