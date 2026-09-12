package com.kairosds.tvmazeapi.controller;

import com.kairosds.tvmazeapi.dto.response.ShowResponse;
import com.kairosds.tvmazeapi.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    // api/shows/search?q=girls
    // se agrega ResponseEntity para homologar
    @GetMapping("/search")
    public ResponseEntity<List<ShowResponse>> search(@RequestParam("q") @NotBlank String q) {
        List<ShowResponse> response = showService.search(q);
        return ResponseEntity.ok(response);
    }

    // api/shows/139
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        ShowResponse response = showService.getShowById(id);

        // si no se encontro el show se devuelve mensaje not found 404
        if(response == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Show not found"));
        }
        return ResponseEntity.ok(response);
    }
}
