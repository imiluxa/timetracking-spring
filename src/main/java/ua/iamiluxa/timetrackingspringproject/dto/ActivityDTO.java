package ua.iamiluxa.timetrackingspringproject.dto;

import lombok.*;
import ua.iamiluxa.timetrackingspringproject.entity.ActivityStatus;
import ua.iamiluxa.timetrackingspringproject.entity.Request;
import ua.iamiluxa.timetrackingspringproject.entity.User;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDTO {
    private long id;
    @NotBlank
    private String name;
    private String goal;
    private Set<User> users;
    private ActivityStatus status;
    private Set<Request> Requests;
}
