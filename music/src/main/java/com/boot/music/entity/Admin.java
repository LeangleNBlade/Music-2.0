package com.boot.music.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
@Entity
public class Admin extends Account {

	protected Admin(String username, String password, String fullname, String address, @Email String mail, Role role) {
		super(username, password, fullname, address, mail, role);
		// TODO Auto-generated constructor stub
	}

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
