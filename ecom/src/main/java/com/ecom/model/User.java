package com.ecom.model;
import java.util.List;

import jakarta.persistence.*;
@Entity
public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String uname;
	    private String name;
	    private String email;
	    private String pwd;
	    private int age;
	    private String address;
	    private long phone;
	    private int pincode;
	    private boolean ismale;   
	    private boolean isseller;
	    private List<Long> cartItems;
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPwd() {
			return pwd;
		}
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public long getPhone() {
			return phone;
		}
		public void setPhone(long phone) {
			this.phone = phone;
		}
		public int getPincode() {
			return pincode;
		}
		public void setPincode(int pincode) {
			this.pincode = pincode;
		}
		public boolean isIsseller() {
			return isseller;
		}
		public void setIsseller(boolean isseller) {
			this.isseller = isseller;
		}
		public boolean isIsmale() {
			return ismale;
		}
		public void setIsmale(boolean ismale) {
			this.ismale = ismale;
		}
		@Override
		public String toString() {
			return "User [uname=" + uname + ", name=" + name + ", email=" + email + ", pwd=" + pwd + ", age=" + age
					+ ", address=" + address + ", phone=" + phone + ", pincode=" + pincode +", ismale=" + ismale + ", isseller=" + isseller
					+ "]";
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public List<Long> getCartItems() {
			return cartItems;
		}
		public void setCartItems(List<Long> cartItems) {
			this.cartItems = cartItems;
		}
}
