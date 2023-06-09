package com.morev.movies.service.image;

import com.morev.movies.dto.image.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ImageService {
    ImageDTO uploadImage(MultipartFile file) throws IOException, SQLException;

    byte[] display(String id);

    List<ImageDTO> getAll();

    void deleteImage(String id);
}
