package com.mymart.model;

import jakarta.persistence.*;


@Entity
@Table(name = "cardImage")
public class cardImage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	private String imageFileName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public cardImage(int id, String imageFileName) {
		super();
		this.id = id;
		this.imageFileName = imageFileName;
	}

	public cardImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "cardImage [id=" + id + ", imageFileName=" + imageFileName + "]";
	}
	
	
	
}
