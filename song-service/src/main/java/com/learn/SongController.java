package com.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.responses.SongCreationResponseDTO;
import com.learn.responses.SongDTO;
import com.learn.responses.SongRemoveResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SongDTO> getSong(@PathVariable("id") Long id) {

        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SongCreationResponseDTO> saveMetadata(@Valid @RequestBody SongMetadata metadata) {

        return ResponseEntity.ok(songService.saveMetadata(metadata));
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<SongRemoveResponseDTO> removeSongs(@RequestParam("id") String ids) {

        return ResponseEntity.ok(songService.deleteSongsByIds(ids));
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {

        return ResponseEntity.ok("Song service works.");
    }
}
