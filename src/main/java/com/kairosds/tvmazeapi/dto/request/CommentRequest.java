package com.kairosds.tvmazeapi.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    @NotBlank(message = "Comment required")
    private String comment;

    @NotNull(message = "Rating required")
    @Min(value = 0, message = "rating only 0 and 5")
    @Max(value = 5, message = "rating only 0 and 5")
    private Integer rating;
}
