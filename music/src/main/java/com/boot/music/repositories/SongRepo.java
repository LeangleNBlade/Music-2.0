package com.boot.music.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.boot.music.entity.Song;

public interface SongRepo extends JpaRepository<Song, Integer>{
 Optional<Song> findById(long id);
}
