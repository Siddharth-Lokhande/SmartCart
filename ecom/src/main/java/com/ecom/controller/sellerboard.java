package com.ecom.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ecom.model.Corder;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.Productrepo;
import com.ecom.repository.Userrepo;
import com.ecom.service.UserSession;
import com.ecom.service.selleritems;
@Controller
public class sellerboard {
	@Autowired
	Productrepo productrepo;
	private final UserSession userSession;
	private final selleritems selleritems;
	Product product=new Product();
	Corder corder=new Corder();
	User user=new User();	
		sellerboard(UserSession userSession, selleritems selleritems) {
			this.userSession = userSession;
			this.selleritems = selleritems;
		}
		@GetMapping("/salesboard")
		public String sellerbord(Model model) {
		model.addAttribute("User", userSession.getUser());
		List<Product> sellerProducts = selleritems.getproducts();
		model.addAttribute("Product", sellerProducts);
		List<Corder> sellerOrders = selleritems.getorders();
		model.addAttribute("Orders",sellerOrders);
		    return "salesboard";
}
		@PostMapping("/addproduct")
		public String addproduct(@ModelAttribute Product product) {
			product.setImageUrl("https://png.pngtree.com/png-vector/20191121/ourmid/pngtree-blue-bird-vector-or-color-illustration-png-image_2013004.jpg");
			this.product=product;
			this.product.setSellerId(userSession.getUser().getId());
			productrepo.save(this.product);
			return "redirect:/salesboard";
			}
		@DeleteMapping("/deleteproduct")
		public void deleteproduct(@ModelAttribute Product product) {
			this.product=product;
			productrepo.delete(this.product);
			}
}
