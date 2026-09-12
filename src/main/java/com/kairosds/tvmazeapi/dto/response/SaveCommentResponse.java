package com.kairosds.tvmazeapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveCommentResponse {
    private final String status;
    private final String id;
}
