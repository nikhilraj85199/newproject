package com.mymart.model;

import org.springframework.web.multipart.MultipartFile;

public class cardImageDto {
	
	private MultipartFile imageFile;

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	
	

}
