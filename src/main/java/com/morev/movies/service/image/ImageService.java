package com.morev.movies.service.image;

import com.morev.movies.dto.image.ImageDTO;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ImageService {
    String uploadImage(MultipartFile file) throws IOException, SQLException;

    byte[] display(ObjectId id);

    List<ImageDTO> getAll();

    void deleteImage(ObjectId id);
}
