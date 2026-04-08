package com.ecom.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.ecom.model.User;
import com.ecom.repository.Userrepo;
import com.ecom.service.UserSession;
import com.ecom.service.loginvalid;
@Controller
@SessionAttributes("nuser")
public class signupc {
	@Autowired
	Userrepo userrepo;
	@Autowired
	loginvalid loginvalid;
	@ModelAttribute("nuser")
	public User user() {
	    return new User();
	}
	private final UserSession userSession;
	signupc(UserSession userSession) {
		this.userSession = userSession;
	}
	
	@GetMapping("/signup")
	public String signup() {
	    return "signup";
	}
	@PostMapping("/register")
	public String register(@ModelAttribute("nuser") User sessionUser, @ModelAttribute User formUser) {
		if (loginvalid.sameuser(formUser.getUname())) {
		sessionUser.setIsseller(formUser.isIsseller());
		sessionUser.setUname(formUser.getUname());
		sessionUser.setPwd(formUser.getPwd());
	    return "form";
		}
		else {
			return "signup";
		}
	}
	@PostMapping("/form")
	public String form(@ModelAttribute("nuser") User sessionUser, @ModelAttribute User formUser, SessionStatus status) {
		sessionUser.setName(formUser.getName());
		sessionUser.setAddress(formUser.getAddress());
		sessionUser.setPhone(formUser.getPhone());
		sessionUser.setAge(formUser.getAge());
		sessionUser.setPincode(formUser.getPincode());
		sessionUser.setEmail(formUser.getEmail());
		sessionUser.setIsmale(formUser.isIsmale());
	    userrepo.save(sessionUser);
	    userSession.login(sessionUser);
	    status.setComplete();
	    if (userSession.getUser().isIsseller()) {
	    	return "redirect:/salesboard";
	    }
	    else {
	    return "redirect:/dashboard";
	    }
	}
	@GetMapping("/login")
	public String login() {
	    return "login";
	}
	@PostMapping("/login")
	public String loggin(@RequestParam String uname, @RequestParam String pwd) {
		String chkvalid=loginvalid.validate(uname, pwd);
		if(chkvalid.equals("seller")) {
			userSession.login(userrepo.findByUname(uname));
			return "redirect:/salesboard";
		}
		else if(chkvalid.equals("buyer")) {
			userSession.login(userrepo.findByUname(uname));
			return "redirect:/dashboard";
		}
		else if(chkvalid.equals("password incorrect")) {
			return "passwordincorrect";
		}
		else {
			return "usernotfound";
		}
	}
	@PostMapping("/logout")
	public String logout() {
		userSession.logout();
		return "redirect:/login";
	}
}
