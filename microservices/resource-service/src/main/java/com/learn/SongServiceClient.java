package com.learn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "song-service", url = "http://song-service:8081")
public interface SongServiceClient {

    @PostMapping("/songs")
    ResponseEntity<String> saveMetadata(@RequestBody SongMetadata metadata);

    @GetMapping("/songs/{id}")
    ResponseEntity<SongMetadata> getSongById(@PathVariable("id") Long id);

    @DeleteMapping("/songs?id={id}")
    ResponseEntity<String> removeMetadata(@RequestParam("id") String ids);
}
