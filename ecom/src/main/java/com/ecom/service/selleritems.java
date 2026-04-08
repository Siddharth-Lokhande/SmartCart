package com.ecom.service;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ecom.model.Corder;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.Orderrepo;
import com.ecom.repository.Productrepo;

@Service
public class selleritems {
	private final Productrepo productrepo;
	private final Orderrepo orderrepo;
	private final UserSession userSession;
	private Product p=new Product();
	public selleritems(Productrepo productrepo, UserSession userSession,Orderrepo orderrepo) {
		this.productrepo = productrepo;
		this.userSession = userSession;
		this.orderrepo = orderrepo;
	}

	public List<Product> getproducts() {
		return productrepo.findBySellerId(userSession.getUser().getId());
	}
	public List<Corder> getorders() {
	List<Corder>cod= orderrepo.findBySellerId(userSession.getUser().getId());
	cod.sort(Comparator.comparing(Corder::getOrderDate).reversed());
	for (Corder order : cod) {
		Product p = productrepo.findById((long) order.getProductId()).orElse(null);
		if (p != null) {
			order.setProductName(p.getPname());
		}
	}
	return cod;
	}
}
