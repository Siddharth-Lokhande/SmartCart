package com.ecom.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ecom.model.Corder;
import com.ecom.repository.Orderrepo;
import com.ecom.service.UserSession;

@Controller
public class orderstat {
	private final Orderrepo orderrepo;
	private final UserSession userSession;

	orderstat(Orderrepo orderrepo, UserSession userSession) {
		this.orderrepo = orderrepo;
		this.userSession = userSession;
	}

	@GetMapping("/orders")
	public String getorders(Model model) {
		model.addAttribute("User", userSession.getUser());
		List<Corder> orders = orderrepo.findByCustomerId(userSession.getUser().getId());
		if (orders == null) {
			orders = new ArrayList<>();
		}
		model.addAttribute("Orders", orders);
		return "order";
	}

	@PostMapping("/addorder")
	public String addorder(Corder corder) {
		corder.setCustomerId(userSession.getUser().getId());
		orderrepo.save(corder);
		return "redirect:/orders";
	}

	@PostMapping("/deleteorder")
	public String deleteorder(Corder corder) {
		orderrepo.delete(corder);
		return "redirect:/orders";
	}
}