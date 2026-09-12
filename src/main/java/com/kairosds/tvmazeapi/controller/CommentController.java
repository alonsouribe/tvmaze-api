package com.kairosds.tvmazeapi.controller;

import com.kairosds.tvmazeapi.dto.request.CommentRequest;
import com.kairosds.tvmazeapi.dto.response.SaveCommentResponse;
import com.kairosds.tvmazeapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shows/{showId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaveCommentResponse save(@PathVariable long showId, @Valid @RequestBody CommentRequest request) {
        // Si el body no pasa @Valid, Spring responde 400.
        return commentService.save(showId, request);
    }
}
