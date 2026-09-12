package com.kairosds.tvmazeapi.service;

import com.kairosds.tvmazeapi.client.TvMazeClient;
import com.kairosds.tvmazeapi.dto.external.TvMazeSearchResultDto;
import com.kairosds.tvmazeapi.dto.external.TvMazeShowDto;
import com.kairosds.tvmazeapi.dto.response.ShowResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    private final TvMazeClient tvMazeClient;

    public ShowService(TvMazeClient tvMazeClient) {
        this.tvMazeClient = tvMazeClient;
    }

    public List<ShowResponse> search(String query) {
        List<TvMazeSearchResultDto> results = tvMazeClient.search(query);

        List<ShowResponse> responses = new ArrayList<>();
        for (TvMazeSearchResultDto result : results) {
            TvMazeShowDto show = result.getShow();
            responses.add(new ShowResponse(show.getId(), show.getName(), show.getChannelName(),
                    show.getSummary(), show.getGenres()));
        }
        return responses;
    }
}
