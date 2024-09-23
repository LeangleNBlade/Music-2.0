package com.boot.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.music.entity.Artist;



public interface ArtistRepo extends JpaRepository<Artist, Integer>{

}
