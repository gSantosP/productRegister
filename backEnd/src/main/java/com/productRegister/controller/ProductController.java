package com.productRegister.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.http.*;
import com.productRegister.model.Product;
import org.springframework.web.bind.annotation.*;
import com.productRegister.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/findAllProducts")
	public ResponseEntity<List<Product>> findAllProducts(@RequestParam(required = false) String title) {
		try {
			List<Product> products = new ArrayList<Product>();

			if (title == null)
				productRepository.findAll().forEach(products::add);
			else
				productRepository.findByTitleContainingIgnoreCase(title).forEach(products::add);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findProductById/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable("id") long id) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/createProduct")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		try {
			Product newProduct = productRepository
					.save(new Product(product.getTitle(), product.getDescription(), false));
			return new ResponseEntity<>(product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			Product updatedProduct = productData.get();
			product.setTitle(product.getTitle());
			product.setDescription(product.getDescription());
			product.setPublished(product.isPublished());
			return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteAllProducts")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			productRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/products/published")
	public ResponseEntity<List<Product>> findByPublished() {
		try {
			List<Product> products = productRepository.findByPublished(true);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}