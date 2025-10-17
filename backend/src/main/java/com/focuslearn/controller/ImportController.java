package com.focuslearn.controller;

import com.focuslearn.dto.ImportDtos;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/import")
@Validated
public class ImportController {

    @PostMapping("/youtube")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ImportDtos.ImportStatusResponse> importYoutube(@RequestBody ImportDtos.ImportRequest req) {
        ImportDtos.ImportStatusResponse res = new ImportDtos.ImportStatusResponse();
        // create job and return job id
        res.status = "PENDING";
        return ResponseEntity.accepted().body(res);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<ImportDtos.ImportStatusResponse> getJob(@PathVariable UUID jobId) {
        return ResponseEntity.notFound().build();
    }
}
