package com.morev.movies.service.image.impl;

import com.morev.movies.dto.image.ImageDTO;
import com.morev.movies.model.Image;
import com.morev.movies.repository.image.ImageRepository;
import com.morev.movies.service.image.ImageService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setId(new ObjectId().toHexString());
        image.setTitle(file.getOriginalFilename());
        image.setData(file.getBytes());

        imageRepository.save(image);
        return "file uploaded successfully : " + file.getOriginalFilename();
    }

    @Override
    public byte[] display(String id) {
        Optional<Image> dbImage = imageRepository.findById(id);
        return dbImage.map(Image::getData).orElse(null);
    }

    @Override
    public List<ImageDTO> getAll() {
        List<Image> imageList = imageRepository.findAll();
        List<ImageDTO> result = new ArrayList<>();
        imageList.forEach(image -> result.add(new ImageDTO(image)));
        return result;
    }

    @Override
    public void deleteImage(String id) {
        imageRepository.deleteById(id);
    }

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
}
