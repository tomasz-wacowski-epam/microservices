package com.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        byte[] resource = resourceService.getResourceById(id);
        return resource != null ? ResponseEntity.ok(resource) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "audio/mpeg", produces = "application/json")
    public ResponseEntity<ResourceCreationResponseDTO> uploadMusicFile(@RequestBody byte[] audioData) {

        try {
            ResourceCreationResponseDTO resourceResponse = resourceService.saveFile(audioData);
            return resourceResponse.id() != null ? ResponseEntity.ok(resourceResponse) :
                    ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<ResourceRemoveResponseDTO> removeResources(@RequestParam("id") String ids) {

        ResourceRemoveResponseDTO removedResourcesIds = resourceService.deleteResourcesByIds(ids);
        return ResponseEntity.ok(removedResourcesIds);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {

        return ResponseEntity.ok("Resource service works.");
    }
}
