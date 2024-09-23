package com.boot.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.music.entity.Role;

public interface RoleRepo  extends JpaRepository<Role,Integer>{

}
