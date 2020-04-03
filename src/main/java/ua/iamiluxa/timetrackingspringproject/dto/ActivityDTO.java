package ua.iamiluxa.timetrackingspringproject.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDTO {
    @NotBlank
    private String name;
    private String goal;
}
