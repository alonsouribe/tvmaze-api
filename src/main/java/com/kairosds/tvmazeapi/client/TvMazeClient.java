package com.kairosds.tvmazeapi.client;

import com.kairosds.tvmazeapi.dto.external.TvMazeSearchResultDto;
import com.kairosds.tvmazeapi.dto.external.TvMazeShowDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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

    // https://api.tvmaze.com/shows/{show_id}
    public TvMazeShowDto getShowById(Long showId) {
        try {
            return restClient.get()
                    .uri("/shows/{id}", showId)
                    .retrieve()
                    .body(TvMazeShowDto.class);
            // solo se cacha error 404, sin el marca error 500
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}
