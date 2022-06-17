package com.tencoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
