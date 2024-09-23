package com.boot.music.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Email;

@Entity
@PrimaryKeyJoinColumn(name = "account_Id")
public class User extends Account {
	@ManyToMany
	@JoinTable(name="PLAYLIST",
			joinColumns=@JoinColumn(name="USER_ID"),
			inverseJoinColumns = @JoinColumn(name="SONG_ID"))
	List<Song> playlist;
	public User(String username, String password, String fullname, String address, @Email String mail, Role role) {
		super(username, password, fullname, address, mail, role);
		// TODO Auto-generated constructor stub
		playlist= new ArrayList<>();
	}
	public List<Song> getPlaylist() {
		return playlist;
	}
	public void setPlaylist(List<Song> playlist) {
		this.playlist = playlist;
	}
	public User() {}

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
