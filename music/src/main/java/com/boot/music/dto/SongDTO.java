package com.boot.music.dto;

import com.boot.music.entity.Category;
import com.boot.music.entity.Region;

public class SongDTO {
	private int songID;
	private int artistID;
	private String title;
	private String songIMG;
	private String mp3_link;
	private String lyric;
	private Category cateID;
	private Region regionID;
	private String artistName;
	public SongDTO() {
		super();
	}
	public SongDTO(int artistID, String artistName, int songID,  String title, String songIMG, String mp3_link, String lyric, Category cateID,
			Region regionID) {
		super();
		this.songID = songID;
		this.artistID = artistID;
		this.title = title;
		this.songIMG = songIMG;
		this.mp3_link = mp3_link;
		this.lyric = lyric;
		this.cateID = cateID;
		this.regionID = regionID;
		this.artistName = artistName;
	}
	public int getSongID() {
		return songID;
	}
	public void setSongID(int songID) {
		this.songID = songID;
	}
	public int getArtistID() {
		return artistID;
	}
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSongIMG() {
		return songIMG;
	}
	public void setSongIMG(String songIMG) {
		this.songIMG = songIMG;
	}
	public String getMp3_link() {
		return mp3_link;
	}
	public void setMp3_link(String mp3_link) {
		this.mp3_link = mp3_link;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public Category getCateID() {
		return cateID;
	}
	public void setCateID(Category cateID) {
		this.cateID = cateID;
	}
	public Region getRegionID() {
		return regionID;
	}
	public void setRegionID(Region regionID) {
		this.regionID = regionID;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
}
