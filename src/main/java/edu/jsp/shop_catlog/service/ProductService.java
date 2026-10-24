package edu.jsp.shop_catlog.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.jsp.shop_catlog.enitity.Product;
import edu.jsp.shop_catlog.exception.ResourceNotFoundException;
import edu.jsp.shop_catlog.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product saveProduct(Product p) {
		return productRepository.save(p);
	}

	public Product getById(int id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Id Not Found " + id));
	}

	public List<Product> fetchAll() {
		return productRepository.findAll();
	}

	public String deleteById(int id) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Product Id Not Found " + id);
		}
		productRepository.deleteById(id);
		return "Data deleted";
	}

	public void deleteAll() {
		productRepository.deleteAll();
	}

	public Product updateProduct(int id, Product newPrd) {
		Product exprd = getById(id);
		exprd.setName(newPrd.getName());
		exprd.setBrand(newPrd.getBrand());
		exprd.setPrice(newPrd.getPrice());
		exprd.setQuantity(newPrd.getQuantity());
		exprd.setRating(newPrd.getRating());
		return productRepository.save(exprd);
	}

	public String uploadImage(String path, MultipartFile file) throws Exception {
		String originalName = file.getOriginalFilename(); // demo.jpg
		String randomId = UUID.randomUUID().toString(); // AB123
		String fileName = randomId + originalName.substring(originalName.lastIndexOf('.')); // AB123.jpg
		String filepath = path + File.separator + fileName; // images/AB123.jpg

		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		Files.copy(file.getInputStream(), Paths.get(filepath));
		return fileName;
	}

	public Product updateProduct(int id, MultipartFile file) throws Exception {
		Product exprd = getById(id);
		String fileName = uploadImage("images", file);
		exprd.setImage(fileName);
		return productRepository.save(exprd);
	}

	public List<Product> fetchByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	public List<Product> getByPriceBetween(double st, double end) {
		return productRepository.findByPriceBetween(st, end);
	}

	public List<Product> getByRatingBetween(double st, double end) {
		return productRepository.fetchByRatingBetween(st, end);
	}

	public List<Product> fetchAllByPage(int pageNo, int pageSize, String sortBy, String dir) {
		Sort sort = dir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable page = PageRequest.of(pageNo, pageSize, sort);
		return productRepository.findAll(page).getContent();
	}
}
