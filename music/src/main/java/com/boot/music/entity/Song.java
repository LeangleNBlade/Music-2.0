package com.boot.music.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import jakarta.transaction.Transactional;

@Entity
public class Song implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String img;
	private String mp3_link;

	private String lyrics;
	@ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.LAZY)
	 @JoinTable(name = "PERFORM",
			
	 joinColumns = @JoinColumn(name = "song_id", referencedColumnName="id"),
	 inverseJoinColumns = @JoinColumn(name = "artist_id",referencedColumnName="id" ))
	private Set<Artist> artist;
	@ManyToOne(cascade = {CascadeType.MERGE,  CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="CATEGORY_ID")
	
	private Category category;
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="REGION_ID")
	
	private Region reg;
	
	public Region getReg() {
		return reg;
	}
	public void setReg(Region reg) {
		this.reg = reg;
	}
	public Song(String title, String img, String mp3_link, String lyrics, Category category, Region reg) {
		super();
		this.title = title;
		this.img = img;
		this.mp3_link = mp3_link;
		this.artist = new LinkedHashSet<Artist>();
		this.lyrics = lyrics;
		this.category=category;
		this.reg=reg;
	}
	
		public Song() {
		super();
	}
		public String getTitle() {
			return title;
		}	@Transactional
		public void setTitle(String title) {
			this.title = title;
		}
		public String getImg() {
			return img;
		}	@Transactional
		public void setImg(String img) {
			this.img = img;
		}
		public String getMp3_link() {
			return mp3_link;
		}	@Transactional
		public void setMp3_link(String mp3_link) {
			this.mp3_link = mp3_link;
		}
		public Set<Artist> getArtist() {
			return artist;
		}
		@Transactional
		public void setArtist(Set<Artist> artist) {
			this.artist = artist;
		}
			public String getLyrics() {
			return lyrics;
		}
			@Transactional
		public void setLyrics(String lyrics) {
			this.lyrics = lyrics;
		}
		public int getId() {
			return id;
		}
		public void removeArtist(Artist a){
			   this.artist.remove(a);
			   a.getSongs().remove(this);
			}

		
}
