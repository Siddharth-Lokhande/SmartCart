package com.ecom.repository;
import com.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface Userrepo extends JpaRepository<User, Long> {
	User findByUname(String uname);
	User findById(long id);
	}
