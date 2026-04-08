package com.ecom.service;
import org.springframework.stereotype.Service;
import com.ecom.model.User;
import com.ecom.repository.Userrepo;

@Service
public class loginvalid {
	private final Userrepo userrepo;

	public loginvalid(Userrepo userrepo) {
		this.userrepo = userrepo;
	}

	public String validate(String username, String password) {
		User user = userrepo.findByUname(username);
		if (user != null && user.getPwd().equals(password) && user.isIsseller()) {
			return "seller";
		}
		else if (user != null && user.getPwd().equals(password) && !user.isIsseller()) {
			return "buyer";
		}
		else if (user != null && !user.getPwd().equals(password)) {
			return "password incorrect";
		}
		else {
			return "user not found";
		}
	}

	public boolean sameuser(String username) {
		User user = userrepo.findByUname(username);
		if (user != null) {
			return false;
		}
		return true;
	}
}