package com.ecom.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Corder;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.Orderrepo;
import com.ecom.repository.Productrepo;

@Service
public class catalougue {
	@Autowired
	private final Productrepo productrepo;
	private final Orderrepo orderrepo;
	private final UserSession userSession;
	private Corder corder=new Corder();

	public catalougue(Productrepo productrepo,UserSession userSession,Orderrepo orderrepo) {
		this.productrepo = productrepo;
		this.userSession=userSession;
		this.orderrepo=orderrepo;
	}

	public List<Product> getproducts() {
		return productrepo.findAll();
	}

	public List<Product> getcart(List<Long> productIds) {
		return productrepo.findAllById(productIds);
	}
	public void orderplaced(List<Long> productIds,List<Integer> quantities) {
		User user=userSession.getUser();
		for(Long id:productIds) {
			Product product=productrepo.findById(id).orElse(null);
			product.setStock(product.getStock()-quantities.get(productIds.indexOf(id)));
			productrepo.save(product);
			corder.setProductId(product.getId());
			corder.setCustomerId(user.getId());
			corder.setQuantity(quantities.get(productIds.indexOf(id)));
			corder.setTotalPrice(product.getPrice()*quantities.get(productIds.indexOf(id)));
			corder.setStatus("Placed");
			corder.setOrderDate(java.time.LocalDate.now().toString());
			corder.setDeliveryDate(java.time.LocalDate.now().plusDays(7).toString());
			corder.setShippingAddress(user.getAddress());
			corder.setPaymentMethod("COD");
			corder.setSellerId(product.getSellerId());
			orderrepo.save(corder);
		}
	}
}
