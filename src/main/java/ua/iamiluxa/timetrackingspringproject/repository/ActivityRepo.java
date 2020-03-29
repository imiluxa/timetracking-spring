package ua.iamiluxa.timetrackingspringproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.iamiluxa.timetrackingspringproject.entity.Activity;

public interface ActivityRepo extends JpaRepository<Activity, Long> {

}
