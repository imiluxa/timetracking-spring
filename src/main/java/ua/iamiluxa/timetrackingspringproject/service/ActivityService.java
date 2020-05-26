package ua.iamiluxa.timetrackingspringproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.iamiluxa.timetrackingspringproject.dto.ActivityDTO;
import ua.iamiluxa.timetrackingspringproject.dto.DurationDTO;
import ua.iamiluxa.timetrackingspringproject.entity.Activity;
import ua.iamiluxa.timetrackingspringproject.entity.ActivityStatus;
import ua.iamiluxa.timetrackingspringproject.entity.User;
import ua.iamiluxa.timetrackingspringproject.repository.ActivityRepo;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

@Service
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

    public void addActivity(ActivityDTO activityDTO) {
        Activity activity = Activity.builder()
            .name(activityDTO.getName())
            .goal(activityDTO.getGoal())
            .duration(0L)
            .status(ActivityStatus.WAITING)
            .build();

        activityRepo.save(activity);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteActivity(long id) {
        Activity activity = findActivityById(id);
        Set<User> userSet = activity.getUsers();
        for(User user : userSet) {
            user.getActivities().remove(activity);
        }
        activityRepo.delete(activity);
    }
}
