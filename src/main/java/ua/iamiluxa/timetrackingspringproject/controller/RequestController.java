package ua.iamiluxa.timetrackingspringproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.iamiluxa.timetrackingspringproject.entity.Request;
import ua.iamiluxa.timetrackingspringproject.entity.RequestStatus;
import ua.iamiluxa.timetrackingspringproject.entity.User;
import ua.iamiluxa.timetrackingspringproject.service.RequestService;

@Controller
@Log4j2
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/activities/request")
    public String getRequests(Model model) {
        model.addAttribute("Requests", requestService.findAllRequests());

        return "request";
    }

    @GetMapping("/activities/request/add/{id}")
    public String makeAddActivityRequest(@AuthenticationPrincipal User user,
                                         @PathVariable("id") long activityId) throws Exception {
        requestService.makeAddRequest(user.getId(), activityId);
        return "activities";
    }

    @GetMapping("/activities/request/complete/{id}")
    public String makeCompleteActivityRequest(@AuthenticationPrincipal User user,
                                              @PathVariable("id") long activityId) throws Exception {
        requestService.makeCompleteRequest(user.getId(), activityId);
        return "activities";
    }

    @GetMapping("/activities/request/approve/{id}")
    public String approveActivityRequest(@PathVariable("id") long activityRequestId) {
        Request request = requestService
                .findRequestById(activityRequestId);

        if (!request.getStatus().equals(RequestStatus.WAITING)) {
            return "request";
        }

        switch (request.getRequestAction()) {
            case ADD:
                requestService.approveAddRequest(activityRequestId);
                break;
            case END:
                requestService.approveCompleteRequest(activityRequestId);
                break;
        }

        return "redirect:/activities/request";
    }

    @GetMapping("/activities/request/decline/{id}")
    public String declineRequest(@PathVariable("id") long requestId) {
        Request request = requestService.findRequestById(requestId);

        if (!request.getStatus().equals(RequestStatus.WAITING)) {
            return "redirect:/activities/request";
        }

        requestService.declineRequest(requestId);

        return "redirect:/activities/request";
    }
}
