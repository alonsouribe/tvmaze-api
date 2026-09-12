package com.kairosds.tvmazeapi.service;

import com.kairosds.tvmazeapi.document.CommentDocument;
import com.kairosds.tvmazeapi.dto.request.CommentRequest;
import com.kairosds.tvmazeapi.dto.response.CommentResponse;
import com.kairosds.tvmazeapi.dto.response.SaveCommentResponse;
import com.kairosds.tvmazeapi.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public SaveCommentResponse save(long showId, CommentRequest request) {
        // null en el primer parametro porque Mongo genera el id solo al guardar.
        CommentDocument document = new CommentDocument(null, showId, request.getComment(), request.getRating());
        CommentDocument saved = commentRepository.save(document);
        return new SaveCommentResponse("OK", saved.getId());
    }

    public List<CommentResponse> getByShowId(long showId) {
        List<CommentDocument> documents = commentRepository.findByShowId(showId);

        List<CommentResponse> responses = new ArrayList<>();
        for (CommentDocument document : documents) {
            responses.add(new CommentResponse(document.getComment(), document.getRating()));
        }
        return responses;
    }
}
