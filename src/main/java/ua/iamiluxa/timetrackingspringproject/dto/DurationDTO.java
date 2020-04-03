package ua.iamiluxa.timetrackingspringproject.dto;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class DurationDTO {
    @PositiveOrZero
    private long hours;
}
