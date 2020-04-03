package ua.iamiluxa.timetrackingspringproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.iamiluxa.timetrackingspringproject.dto.DurationDTO;
import ua.iamiluxa.timetrackingspringproject.entity.Activity;
import ua.iamiluxa.timetrackingspringproject.entity.User;
import ua.iamiluxa.timetrackingspringproject.repository.ActivityRepo;

import javax.persistence.NoResultException;
import java.util.List;

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

    public List<Activity> findAllActivities() {
        return activityRepo.findAll(Sort.sort(Activity.class));
    }

    @Transactional(isolation =  Isolation.REPEATABLE_READ)
    public void enterDuration(long activityId,
                              DurationDTO durationDTO,
                              User user) {
        Activity activity = findActivityById(activityId);

        if (activity.getUsers().contains(user)) {
            activity.setDuration(durationDTO.getHours());

            activityRepo.save(activity);
        }
    }
}
