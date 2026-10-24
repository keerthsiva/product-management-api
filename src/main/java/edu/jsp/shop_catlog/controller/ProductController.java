package edu.jsp.shop_catlog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.jsp.shop_catlog.enitity.Product;
import edu.jsp.shop_catlog.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/product/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	// POST http://localhost:8080/product/api/create
	@PostMapping("/create")
	public Product saveProduct(@Valid @RequestBody Product p) {
		return productService.saveProduct(p);
	}

	// GET http://localhost:8080/product/api/getbyid/3
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Product> getById(@PathVariable int id) {
		return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/fetchAll")
	public List<Product> fetchAll() {
		return productService.fetchAll();
	}

	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable int id) {
		return productService.deleteById(id);
	}

	@DeleteMapping("/deleteAll")
	public String deleteAll() {
		productService.deleteAll();
		return "All products deleted successfully";
	}

	// PUT http://localhost:8080/product/api/update/4
	@PutMapping("/update/{id}")
	public Product updateProduct(@PathVariable int id, @Valid @RequestBody Product newprd) {
		return productService.updateProduct(id, newprd);
	}

	// PUT http://localhost:8080/product/api/updateimage/4 (form-data, key: file)
	@PutMapping("/updateimage/{id}")
	public Product updateProductImage(@PathVariable int id, @RequestParam("file") MultipartFile file) throws Exception {
		return productService.updateProduct(id, file);
	}

	// GET http://localhost:8080/product/api/fetchbrand?brand=Apple
	@GetMapping("/fetchbrand")
	public List<Product> fetchBrand(@RequestParam String brand) {
		return productService.fetchByBrand(brand);
	}

	// GET http://localhost:8080/product/api/getbyprice?st=100&end=100000
	@GetMapping("/getbyprice")
	public List<Product> getByPriceBetween(@RequestParam double st, @RequestParam double end) {
		return productService.getByPriceBetween(st, end);
	}

	// GET http://localhost:8080/product/api/getbyrating?st=1&end=5
	@GetMapping("/getbyrating")
	public List<Product> getByRatingBetween(@RequestParam double st, @RequestParam double end) {
		return productService.getByRatingBetween(st, end);
	}

	// GET
	// http://localhost:8080/product/api/getAllByPage?pageNo=0&pageSize=5&sortBy=price&dir=desc
	@GetMapping("/getAllByPage")
	public List<Product> fetchAllByPage(@RequestParam(required = false, defaultValue = "0") int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			@RequestParam(required = false, defaultValue = "id") String sortBy,
			@RequestParam(required = false, defaultValue = "asc") String dir) {
		return productService.fetchAllByPage(pageNo, pageSize, sortBy, dir);
	}
}
