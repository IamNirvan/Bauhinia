package com.nirvan.bauhinia.imagedata;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v2/images")
@RequiredArgsConstructor
@CrossOrigin
public class ImageDataController {
    private final ImageDataService imageDataService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestPart("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageDataService.uploadImage(file));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> downloadImage(@PathVariable("name") String fileName) {
        byte[] data = new byte[0];
        try {
            data = imageDataService.downloadImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(data);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> downloadImages(@PathVariable("itemId") int itemId) {
        List<String> data = null;
        try {
            data = imageDataService.downloadImages(itemId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
