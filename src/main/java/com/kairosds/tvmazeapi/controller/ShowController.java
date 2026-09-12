package com.kairosds.tvmazeapi.controller;

import com.kairosds.tvmazeapi.dto.response.ShowResponse;
import com.kairosds.tvmazeapi.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    // api/shows/search?q=girls
    @GetMapping("/search")
    public List<ShowResponse> search(@RequestParam("q") @NotBlank String q) {
        return showService.search(q);

    }
}
