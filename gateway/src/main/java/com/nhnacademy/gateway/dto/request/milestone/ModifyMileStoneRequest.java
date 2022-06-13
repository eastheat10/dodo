package com.nhnacademy.gateway.dto.request.milestone;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyMileStoneRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
