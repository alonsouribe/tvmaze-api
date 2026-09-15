package com.kairosds.tvmazeapi.service;

import com.kairosds.tvmazeapi.document.CommentDocument;
import com.kairosds.tvmazeapi.dto.request.CommentRequest;
import com.kairosds.tvmazeapi.dto.response.CommentResponse;
import com.kairosds.tvmazeapi.dto.response.SaveCommentResponse;
import com.kairosds.tvmazeapi.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        commentService = new CommentService(commentRepository);
    }

    @Test
    void saveComment() {
        CommentRequest request = new CommentRequest();
        request.setComment("Excelente");
        request.setRating(5);

        //simulamos lo que haria mongo al guardar, asignar un id
        // poner L para que sea de tipo Long
        when(commentRepository.save(any(CommentDocument.class)))
                .thenReturn(new CommentDocument("generated-id", 139L, "Excelente", 5));

        SaveCommentResponse response = commentService.save(139L, request);

        assertEquals("OK", response.getStatus());
        assertEquals("generated-id", response.getId());
    }

    @Test
    void getCommentsByShowId() {
        // simulamos que mongo ya tiene un comentario guardado para este show
        List<CommentDocument> comments = new ArrayList<>();
        comments.add(new CommentDocument("generated-id", 139L, "Excelente", 5));

        when(commentRepository.findByShowId(139L)).thenReturn(comments);

        List<CommentResponse> response = commentService.getByShowId(139L);

        assertEquals(1, response.size());
        assertEquals("Excelente", response.get(0).getComment());
        assertEquals(5, response.get(0).getRating());
    }
}
