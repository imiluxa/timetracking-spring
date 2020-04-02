package ua.iamiluxa.timetrackingspringproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import ua.iamiluxa.timetrackingspringproject.entity.Activity;
import ua.iamiluxa.timetrackingspringproject.repository.ActivityRepo;

import javax.persistence.NoResultException;

@Log4j2
public class ActivityService {
    private final ActivityRepo activityRepo;

    @Autowired
    public ActivityService(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    public Activity findActivityById(long id) {
        return activityRepo.findById(id).orElseThrow(() ->
                new NoResultException("id not finded: " + id));
    }
}
