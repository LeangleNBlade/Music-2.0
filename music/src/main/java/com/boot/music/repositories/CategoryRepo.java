package com.boot.music.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.music.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
 Optional<Category> findByCategoryNameIgnoreCase(String name);
}
