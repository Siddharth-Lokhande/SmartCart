package com.ecom.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecom.model.Corder;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.Userrepo;
import com.ecom.service.UserSession;
import com.ecom.service.catalougue;

@Controller
public class dashboard {
	private final UserSession userSession;
	private final catalougue catalougue;
	private final Userrepo userrepo;

	@Autowired
	dashboard(UserSession userSession, catalougue catalougue, Userrepo userrepo) {
		this.userSession = userSession;
		this.catalougue = catalougue;
		this.userrepo = userrepo;
	}
	@GetMapping("/dashboard")
	public String getdash(Model model) {
		model.addAttribute("User", userSession.getUser());
		List<Product> products = catalougue.getproducts();
		model.addAttribute("Product", products);
	    return "dashboard";
	}
	@GetMapping("/cart")
	public String getcart(Model model) {
		User user = userSession.getUser();
		model.addAttribute("User", user);
		List<Long> cartItems = user.getCartItems();
		if (cartItems == null || cartItems.isEmpty()) {
			model.addAttribute("Product", new ArrayList<>());
		} else {
			List<Product> products = catalougue.getcart(cartItems);
			model.addAttribute("Product", products);
		}
	    return "cart";
	}
	@PostMapping("/addtocart")
	public String addtocart(@ModelAttribute Product product) {
		User user = userSession.getUser();
		if(user.getCartItems()==null) {
	List<Long> cartItems=new ArrayList<>();
	cartItems.add(product.getId());
	user.setCartItems(cartItems);
		}
		else {
		List<Long> cartItems=user.getCartItems();
		cartItems.add(product.getId());
		user.setCartItems(cartItems);
		}

		userrepo.save(user);
		return "redirect:/dashboard";
	}
	@PostMapping("/removefromcart")
	public String removefromcart(@RequestParam("id") Long id) {
	    User user = userSession.getUser();
	    List<Long> cartItems = user.getCartItems();
	    cartItems.remove(Long.valueOf(id));
	    user.setCartItems(cartItems);
	    userrepo.save(user);
	    return "redirect:/cart";
	}
	@PostMapping("/placeorder")
	public String placeorder(@RequestParam("productIds") List<Long> productIds,
		    @RequestParam("quantities") List<Integer> quantities) {
		User user = userSession.getUser();
		List<Long> cartItems=user.getCartItems();
		catalougue.orderplaced(productIds,quantities);
		cartItems.clear();
		user.setCartItems(cartItems);
		userrepo.save(user);
		return ("redirect:/orders");
	}
	@GetMapping("/profile")
	public String getprofile(Model model) {
		model.addAttribute("User", userSession.getUser());
		return "profile";
	}
	@PostMapping("/profile")
	public String updateprofile(Model model) {
		model.addAttribute("User", userSession.getUser());
		return "profile";
	}
	}

