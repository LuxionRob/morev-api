package com.morev.movies.controller;

import com.morev.movies.dto.image.ImageDTO;
import com.morev.movies.service.image.impl.ImageServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageServiceImpl imageService;

    @Autowired
    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(imageService.uploadImage(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable ObjectId id) {
        byte[] imageData = imageService.display(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageData);
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAll() {
        return ResponseEntity.ok().body(imageService.getAll());
    }
}
