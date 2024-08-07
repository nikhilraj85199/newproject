package com.mymart.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class carouselDto {

    @NotNull
    private MultipartFile imageFile;

    @NotNull
    private Category category;

    // Getters and setters
    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
