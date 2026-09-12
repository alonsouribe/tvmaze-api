package com.kairosds.tvmazeapi.client;

import com.kairosds.tvmazeapi.dto.external.TvMazeSearchResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class TvMazeClient {

    private final RestClient restClient;

    public TvMazeClient(@Value("${tvmaze.base-url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    // http://api.tvmaze.com/search/shows?q=query
    public List<TvMazeSearchResultDto> search(String query) {
        TvMazeSearchResultDto[] results = restClient.get()
                .uri("/search/shows?q={q}", query)
                .retrieve()
                .body(TvMazeSearchResultDto[].class);

        List<TvMazeSearchResultDto> list = new ArrayList<>();

        if(results != null) {
            for(TvMazeSearchResultDto result : results) {
                list.add(result);
            }
        }
        return list;
    }
}
