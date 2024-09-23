package com.boot.music.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
public class Artist implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(unique = true)
	private String name;

	@ManyToMany(mappedBy = "artist",fetch = FetchType.LAZY)
	private Set<Song> songs;
	
	public Artist(String name) {
		super();
		this.name = name;
	
		this.songs = new LinkedHashSet<Song>();
	}
	public Artist() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getImg() {
//		return img;
//	}
//	public void setImg(String img) {
//		this.img = img;
//	}
	@ManyToMany(mappedBy = "artist")
	public Set<Song> getSongs() {
		return songs;
	}
	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}
	public int getId() {
		return id;
	}
	
}
