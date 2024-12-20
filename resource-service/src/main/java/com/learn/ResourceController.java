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

import com.learn.responses.ResourceCreationResponseDTO;
import com.learn.responses.ResourceRemoveResponseDTO;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(@PathVariable("id") Long id) {

        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    @PostMapping(consumes = "audio/mpeg", produces = "application/json")
    public ResponseEntity<ResourceCreationResponseDTO> uploadMusicFile(@RequestBody byte[] audioData) {

        return ResponseEntity.ok(resourceService.saveFile(audioData));

    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<ResourceRemoveResponseDTO> removeResources(@RequestParam("id") String ids) {

        return ResponseEntity.ok(resourceService.deleteResourcesByIds(ids));
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {

        return ResponseEntity.ok("Resource service works.");
    }
}
