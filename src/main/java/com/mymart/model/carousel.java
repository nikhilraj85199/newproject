package com.mymart.model;

import jakarta.persistence.*;


@Entity
@Table(name="carousel")
public class carousel 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String imageFileName;
	
	@ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
	
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	
	public carousel(Long id, String imageFileName, Category category) {
		super();
		this.id = id;
		this.imageFileName = imageFileName;
		this.category = category;
	}
	
	public carousel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "carousel [id=" + id + ", imageFileName=" + imageFileName + "]";
	}
	
	
	
}
