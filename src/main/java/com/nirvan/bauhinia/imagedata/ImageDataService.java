package com.nirvan.bauhinia.imagedata;

import com.nirvan.bauhinia.item.Item;
import com.nirvan.bauhinia.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageDataService {

    private final ImageDataRepository repository;
    private final ItemRepository itemRepository;
    private final String folder = "C:\\Users\\Shalin\\Documents\\1_Java\\Projects\\Bauhinia\\src\\main\\resources\\static\\Images\\";

    public ImageData uploadImage(MultipartFile file) throws IOException {
        String filePath = folder + file.getOriginalFilename();
        ImageData image = repository.save(ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .build());

        file.transferTo(new File(filePath));
        return image;
    }


    public byte[] downloadImage(String name) throws IOException {
        ImageData image = repository.findByName(name).orElseThrow(() -> new IllegalStateException(""));
        String filePath = image.getFilePath();
        byte[] imageData = Files.readAllBytes(new File(filePath).toPath());
        return imageData;
    }

    public List<String> downloadImages(int itemId) throws IOException {
        Item item = itemRepository.getById(itemId);
        List<ImageData> images = item.getImages();
        List<String> result = new ArrayList<>();

        for(ImageData file : images) {
            ImageData image = repository.findByName(file.getName()).orElseThrow(() -> new IllegalStateException("Image not found"));
//            String filePath = image.getFilePath();
            result.add(image.getName());
        }
       return result;
    }

}
