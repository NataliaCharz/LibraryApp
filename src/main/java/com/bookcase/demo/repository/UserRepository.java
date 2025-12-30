package com.bookcase.demo.repository;

import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByUserName(String login);

    Optional<AppUser> findAppUserByUserId(Long id);

    boolean existsByUserName(String username);
}
