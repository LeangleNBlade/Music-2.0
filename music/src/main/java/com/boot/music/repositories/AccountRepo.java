package com.boot.music.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.music.entity.Account;


public interface AccountRepo extends JpaRepository<Account, Integer> {
    boolean existsAccountByUsername(String username); //kiểm tra có tồn tại tên đăng nhập
    boolean existsAccountByMail(String mail); //kiểm tra tồn tại theo mail
    Optional<Account> findByUsername(String username); //tìm tk theo tên đăng nhập
    Optional<Account> findByUsernameAndPassword(String username, String password); //tìm tk theo tên đăng nhập
}
