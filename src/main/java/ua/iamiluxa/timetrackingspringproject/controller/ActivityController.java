package ua.iamiluxa.timetrackingspringproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.iamiluxa.timetrackingspringproject.dto.DurationDTO;
import ua.iamiluxa.timetrackingspringproject.entity.User;
import ua.iamiluxa.timetrackingspringproject.service.ActivityService;

import javax.validation.Valid;

@Log4j2
@Controller
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public String getActivities(Model model) {
        model.addAttribute("activity", activityService.findAllActivities());
        return "activities";
    }

    @ModelAttribute("duration")
    public DurationDTO durationDTO() { return new DurationDTO();}

    @PostMapping("/activities/duration/{id}")
    public String InputDuration(@AuthenticationPrincipal User user,
                                @PathVariable("id") long activityId,
                                @ModelAttribute("duration") @Valid DurationDTO durationDTO,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activity", activityService.findAllActivities());
            return "activities";
        }

        activityService.enterDuration(activityId, durationDTO, user);

        return "activities";
    }
}
