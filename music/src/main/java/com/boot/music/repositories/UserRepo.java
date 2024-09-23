package com.boot.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.music.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
