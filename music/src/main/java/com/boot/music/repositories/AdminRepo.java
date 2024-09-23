package com.boot.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.music.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer>{

}
