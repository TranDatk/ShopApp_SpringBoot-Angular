package com.dat.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{
    private Long id;

    private String name;

    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

}
