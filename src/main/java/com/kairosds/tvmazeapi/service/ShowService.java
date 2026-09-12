package com.kairosds.tvmazeapi.service;

import com.kairosds.tvmazeapi.client.TvMazeClient;
import com.kairosds.tvmazeapi.document.ShowDocument;
import com.kairosds.tvmazeapi.dto.external.TvMazeSearchResultDto;
import com.kairosds.tvmazeapi.dto.external.TvMazeShowDto;
import com.kairosds.tvmazeapi.dto.response.CommentResponse;
import com.kairosds.tvmazeapi.dto.response.ShowResponse;
import com.kairosds.tvmazeapi.repository.CommentRepository;
import com.kairosds.tvmazeapi.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private final TvMazeClient tvMazeClient;
    private final ShowRepository showRepository;
    private final CommentService commentService;

    public ShowService(TvMazeClient tvMazeClient, ShowRepository showRepository, CommentService commentService) {
        this.tvMazeClient = tvMazeClient;
        this.showRepository = showRepository;
        this.commentService = commentService;
    }

    public List<ShowResponse> search(String query) {
        List<TvMazeSearchResultDto> results = tvMazeClient.search(query);

        List<ShowResponse> responses = new ArrayList<>();
        for (TvMazeSearchResultDto result : results) {
            TvMazeShowDto show = result.getShow();

            // obtenemos los comentarios por show id
            List<CommentResponse> comments = commentService.getByShowId(show.getId());

            responses.add(new ShowResponse(show.getId(), show.getName(), show.getChannelName(),
                    show.getSummary(), show.getGenres(), comments));
        }
        return responses;
    }


    public ShowResponse getShowById(Long showId) {

        // buscamos primero en mongo
        Optional<ShowDocument> cached = showRepository.findById(showId);

        ShowDocument show;

        if(cached.isPresent()) {
            // revisamos si esta en cache
            show = cached.get();
        } else {
            // de lo contrario lo solicitamos a la api
            TvMazeShowDto fromApi = tvMazeClient.getShowById(showId);

            // devuelve null si no tiene ningun show con ese id
            if(fromApi == null) {
                return null;
            }
            // creamos el documento
            show = new ShowDocument(fromApi.getId(), fromApi.getName(), fromApi.getChannelName(),
                    fromApi.getSummary(), fromApi.getGenres());
            // guardamos show en mongo
            showRepository.save(show);
        }

        List<CommentResponse> comments = commentService.getByShowId(showId);

        // retornamos la respuesta
        return new ShowResponse(show.getId(), show.getName(), show.getName(), show.getSummary(), show.getGenres(), comments);
    }
}
