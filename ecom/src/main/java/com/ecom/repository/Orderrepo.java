package com.ecom.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.model.Corder;
@Repository
public interface Orderrepo extends JpaRepository<Corder, Long> {
	Corder findById(long id);
	List<Corder> findByCustomerId(Long customerId);
	List<Corder> findBySellerId(Long sellerId);
}