package com.kairosds.tvmazeapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {

    private String comment;
    private Integer rating;
}
