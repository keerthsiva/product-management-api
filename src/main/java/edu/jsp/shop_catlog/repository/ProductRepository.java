package edu.jsp.shop_catlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.jsp.shop_catlog.enitity.Product;



public interface ProductRepository extends JpaRepository<Product, Integer> {

	// Query methods (SQL derived from method name)
	List<Product> findByBrand(String brand);

	List<Product> findByPriceBetween(double st, double end);

	// Custom JPQL query
	@Query(value = "select p from Product p where p.rating between :start and :end")
	List<Product> fetchByRatingBetween(@Param("start") double st, @Param("end") double end);
}
