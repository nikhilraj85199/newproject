package com.mymart.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mymart.model.cardImage;
import com.mymart.model.cardImageDto;
import com.mymart.repository.cardImageRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Admin")

public class cardImageController {
	
	@Autowired
	private cardImageRepository repo;
	
	@GetMapping("/AdmincardImage")
	public String showcardImageList(Model model){
	List<cardImage> cardImage = repo.findAll();
		model.addAttribute("cardImage",cardImage);
		return "admin/AdmincardImage";
	}
	
	@GetMapping("/createcardImage")
	public String showCreatecardImage(Model model)
	{
		cardImageDto cardImageDto = new cardImageDto();
		model.addAttribute("cardImageDto", cardImageDto);
		return "admin/CreatecardImage";
	}

	@PostMapping("/createcardImage")
	public String createcardImage(
			@Valid @ModelAttribute cardImageDto cardImageDto,
			BindingResult result			
			)

	{
			if(cardImageDto.getImageFile().isEmpty())
			{
				result.addError(new FieldError("cardImageDto","imageFile","The image file is required"));
			}

		if(result.hasErrors())
		{
			return "admin/CreatecardImage";
		}

		MultipartFile image = cardImageDto.getImageFile();
		String storageFileName = image.getOriginalFilename();
		try
	{
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);

			if(!Files.exists(uploadPath))
			{
				Files.createDirectories(uploadPath);
			}

		try(InputStream inputStream = image.getInputStream())
		{
			Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
					StandardCopyOption.REPLACE_EXISTING);
		}			

	}catch(Exception ex)

		{
			System.out.println("Exception : " +ex.getMessage());
		}

		cardImage cardImage = new cardImage();
		cardImage.setImageFileName(storageFileName);
		repo.save(cardImage);
		
		return "redirect:/Admin/AdmincardImage";
	}
	
	@GetMapping("/editcardImage")
	public String showEditcardImage(
			Model model,
			@RequestParam Integer id

			) {
		
		try {
			cardImage cardImage = repo.findById(id).get();
			model.addAttribute("cardImage",cardImage);
			cardImageDto cardImageDto = new cardImageDto();
			model.addAttribute("cardImageDto", cardImageDto);
		}
		catch(Exception e) {
			System.out.println("Exception : " +e.getMessage());
			return "redirect:/Admin/AdmincardImage";
		}
	return "admin/EditcardImage";	
	}
	
	@PostMapping("/editcardImage")
	public String updatecardImage(
			Model model,
			@RequestParam Integer id,
			@Valid @ModelAttribute cardImageDto cardImageDto,
			BindingResult result
			) {
		try {
			cardImage cardImage = repo.findById(id).get();
			model.addAttribute("cardImage", cardImage);
			if(result.hasErrors()) {
				return "admin/EditcardImage";
			}
			if(!cardImageDto.getImageFile().isEmpty()) {

				//for deleting the old images
				
				String uploadDir = "public/images/";
				Path oldImagePath = Paths.get(uploadDir  + cardImage.getImageFileName());
				try {
					Files.delete(oldImagePath);
				}
				catch(Exception e) {
					System.out.println("Exception: " +e.getMessage());
				}
				//save the new image
				
				MultipartFile image = cardImageDto.getImageFile();
				String storageFileName = image.getOriginalFilename();
				try(InputStream inputStream = image.getInputStream()){
					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}
				cardImage.setImageFileName(storageFileName);
				}
			
			repo.save(cardImage);
		}
		catch(Exception e) {
			System.out.println("Exception: " +e.getMessage());
		}
		return "redirect:/Admin/AdmincardImage";

	}
	
	@GetMapping("/deletecardImage")

	public String deletecardImage(
			@RequestParam Integer id
			) {
		try {
			cardImage cardImage = repo.findById(id).get();

			//for deleting the product image

			Path imagePath = Paths.get("public/images/" + cardImage.getImageFileName());
			try {
				Files.delete(imagePath);

			}
			catch(Exception e) {
				System.out.println("Exception: " +e.getMessage());
			}

			//to delete the product

			repo.delete(cardImage);

		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

		}

		return "redirect:/Admin/AdmincardImage";

	}
	
	

}
