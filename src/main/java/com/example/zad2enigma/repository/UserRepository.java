package com.example.zad2enigma.repository;

import com.example.zad2enigma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
