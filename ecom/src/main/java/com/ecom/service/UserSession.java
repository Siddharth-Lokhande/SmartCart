package com.ecom.service;
import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import com.ecom.model.User;
@Component
@SessionScope
public class UserSession implements Serializable {
	    private static final long serialVersionUID = 1L;
	    private User user;
	    public void login(User user) {
	        this.user = user;
	    }
	    public void logout() {
	        this.user = null;
	    }
	    public User getUser() {
	        return user;
	    }
	    public boolean isLoggedIn() {
	        return user != null;
	    }
	}

