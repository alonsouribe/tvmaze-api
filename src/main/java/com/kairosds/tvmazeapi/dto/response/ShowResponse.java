package com.kairosds.tvmazeapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor // necesario para crear el contructor
public class ShowResponse {

    private final Long id;
    private final String name;
    private final String channel;
    private final String summary;
    private final List<String> genres;
}
