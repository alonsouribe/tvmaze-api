package com.kairosds.tvmazeapi.service;

import com.kairosds.tvmazeapi.client.TvMazeClient;

import com.kairosds.tvmazeapi.document.ShowDocument;
import com.kairosds.tvmazeapi.dto.external.TvMazeChannelDto;
import com.kairosds.tvmazeapi.dto.external.TvMazeShowDto;
import com.kairosds.tvmazeapi.dto.response.ShowResponse;
import com.kairosds.tvmazeapi.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {

    @Mock
    private TvMazeClient tvMazeClient;

    @Mock
    private ShowRepository showRepository ;

    @Mock
    private CommentService commentService;

    private ShowService showService;

    @BeforeEach
    public void setUp() {
        showService = new ShowService(tvMazeClient, showRepository, commentService);
    }

    @Test
    public void getShowByIdCache() {
        // simulamos que el show ya esta guardado en mongo
        ShowDocument cached = new ShowDocument(139L, "Girls", "HBO", "summary", new ArrayList<>());

        when(showRepository.findById(139L)).thenReturn(Optional.of(cached));
        when(commentService.getByShowId(139L)).thenReturn(new ArrayList<>());

        ShowResponse response = showService.getShowById(139L);

        assertEquals("HBO", response.getChannel());

        // si vino de cache, no hacia falta revisar a TVMaze
        verify(tvMazeClient, never()).getShowById(anyLong());
    }

    @Test
    public void getShowByIdFromApi() {
        // creamos un show falso, como si viniera de la respuesta de TVMaze
        TvMazeShowDto showFromApi = new TvMazeShowDto();
        showFromApi.setId(139L);
        showFromApi.setName("Girls");
        showFromApi.setNetwork(new TvMazeChannelDto());
        showFromApi.getNetwork().setName("HBO");

        // simulamos que NO esta en cache
        when(showRepository.findById(139L)).thenReturn(Optional.empty());

        // simulamos que TVMaze si lo encuentra
        when(tvMazeClient.getShowById(139L)).thenReturn(showFromApi);

        // simulamos el guardado Mongo
        when(showRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(commentService.getByShowId(139L)).thenReturn(new ArrayList<>());

        ShowResponse response = showService.getShowById(139L);

        assertEquals("HBO", response.getChannel());

        // el show debio guardarse porque vino de la API, no de cache
        verify(showRepository).save(any(ShowDocument.class));
    }

    @Test
    void getShowByIdNotFound() {
        // no esta en cache
        when(showRepository.findById(999L)).thenReturn(Optional.empty());

        // y tampoco en TVMaze
        when(tvMazeClient.getShowById(999L)).thenReturn(null);

        ShowResponse response = showService.getShowById(999L);

        // el service debe devolver null y en el controller lo retorna error 404
        assertNull(response);
    }

}
