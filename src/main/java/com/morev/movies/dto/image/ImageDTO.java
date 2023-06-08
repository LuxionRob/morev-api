package com.morev.movies.dto.image;

import com.morev.movies.model.Image;
import com.morev.movies.utils.GlobalConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {
    @Id
    private String id;

    private String title;

    private String url;

    public ImageDTO(Image image) {
        this.id = image.getId();
        this.title = image.getTitle();
        this.url = GlobalConstant.BASE_URL.getUrl() + "/images/" + image.getId();
    }
}