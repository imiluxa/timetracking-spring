package ua.iamiluxa.timetrackingspringproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.iamiluxa.timetrackingspringproject.entity.*;
import ua.iamiluxa.timetrackingspringproject.repository.ActivityRepo;
import ua.iamiluxa.timetrackingspringproject.repository.RequestRepo;
import ua.iamiluxa.timetrackingspringproject.repository.UserRepo;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class RequestService {
    private final RequestRepo requestRepo;
    private final UserRepo userRepo;
    private final ActivityRepo activityRepo;

    @Autowired
    public RequestService(RequestRepo requestRepo,
                          UserRepo userRepo,
                          ActivityRepo activityRepo) {
        this.requestRepo = requestRepo;
        this.userRepo = userRepo;
        this.activityRepo = activityRepo;
    }

    public Request findRequestById(long id) {
        return requestRepo.findById(id).orElseThrow(() ->
                new NoResultException("id not finded: " + id));
    }

    public List<Request> findAllRequests() {
        return requestRepo.findAll(Sort.sort(Request.class));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void makeAddRequest(Long id, long activityId) throws Exception {
        User user = userRepo.findById(id).orElseThrow(() ->
                new Exception("Invalid user: " + id));
        Activity activity = activityRepo.findById(activityId).orElseThrow(() ->
                new Exception("Invalid activity: " + activityId));

        long currentRequestsCount = requestRepo
                .findByActivityIdAndUserId(activityId, id)
                .stream()
                .filter(activityRequest ->
                        activityRequest.getRequestAction().equals(RequestActions.ADD) &&
                                activityRequest.getStatus().equals(RequestStatus.WAITING))
                .count();
        if (currentRequestsCount > 0) {
            return;
        }

        switch (activity.getStatus()) {
            case COMPLETED:
                break;
            case INPROGRESS: {
                if (activity.getUsers().contains(user)) {
                    break;
                }
                Request request = Request.builder()
                        .user(user)
                        .activity(activity)
                        .requestAction(RequestActions.ADD)
                        .status(RequestStatus.WAITING)
                        .build();
                requestRepo.save(request);
                break;
            }
            case WAITING: {
                Request request = Request.builder()
                        .user(user)
                        .activity(activity)
                        .requestAction(RequestActions.ADD)
                        .status(RequestStatus.WAITING)
                        .build();
                requestRepo.save(request);
                break;
            }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void makeCompleteRequest(Long id, long activityId) throws Exception {
        User user = userRepo.findById(id).orElseThrow(() ->
                new Exception("Invalid user: " + id));
        Activity activity = activityRepo.findById(activityId).orElseThrow(() ->
                new Exception("Invalid activity: " + activityId));

        long currentRequestsCount = requestRepo
                .findByActivityIdAndUserId(activityId, id)
                .stream()
                .filter(activityRequest ->
                        activityRequest.getRequestAction().equals(RequestActions.END) &&
                                activityRequest.getStatus().equals(RequestStatus.WAITING))
                .count();
        if (currentRequestsCount > 0) {
            return;
        }

        switch (activity.getStatus()) {
            case COMPLETED:
                break;
            case INPROGRESS:
                if (activity.getUsers().contains(user)) {
                    Request activityRequest = Request.builder()
                            .user(user)
                            .activity(activity)
                            .requestAction(RequestActions.END)
                            .status(RequestStatus.WAITING)
                            .build();
                    requestRepo.save(activityRequest);
                    return;
                }
                break;
            case WAITING:
                break;
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void approveAddRequest(long requestId) {
        Request request = findRequestById(requestId);

        Activity activity = request.getActivity();
        User user = request.getUser();

        switch (activity.getStatus()) {
            case WAITING: {
                activity.setStatus(ActivityStatus.INPROGRESS);
                user.getActivities().add(activity);
                request.setStatus(RequestStatus.CONFIRMED);
                activityRepo.save(activity);
                break;
            }
            case INPROGRESS: {
                user.getActivities().add(activity);
                request.setStatus(RequestStatus.CONFIRMED);
                activityRepo.save(activity);
                break;
            }
            case COMPLETED: {
                request.setStatus(RequestStatus.DECLINED);
                break;
            }
        }
        requestRepo.save(request);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void approveCompleteRequest(long requestId) {
        Request request = findRequestById(requestId);

        Activity activity = request.getActivity();

        switch (activity.getStatus()) {
            case WAITING: {
                break;
            }
            case INPROGRESS: {
                activity.setStatus(ActivityStatus.COMPLETED);
                request.setStatus(RequestStatus.CONFIRMED);
                activityRepo.save(activity);
                requestRepo.save(request);
                break;
            }
            case COMPLETED: {
                request.setStatus(RequestStatus.DECLINED);
                requestRepo.save(request);
                break;
            }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void declineRequest(long requestId) {
        Request request = findRequestById(requestId);
        request.setStatus(RequestStatus.DECLINED);
        requestRepo.save(request);
    }
}
