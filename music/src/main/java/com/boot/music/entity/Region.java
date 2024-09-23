package com.boot.music.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public Region(String country) {
		super();
		this.country = country;
	}

	public Region() {
		super();
	}
	
}
