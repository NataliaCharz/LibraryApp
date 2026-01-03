package com.bookcase.demo.service;

import com.bookcase.demo.entity.AppUser;
import com.bookcase.demo.entity.Book;
import com.bookcase.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final UserRepository userRepository;

    public Set<Book> getUserBooks(Long id) {
        return userRepository.findByIdWithBooks(id)
                .map(AppUser::getUserBooks)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public Set<Book> getUserFavoriteBooks(Long id) {
        return userRepository.findByIdWithFavoriteBooks(id)
                .map(AppUser::getFavoriteBooks)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public Set<Book> getUserWishListBooks(Long id) {
        return userRepository.findByIdWithWishlistBooks(id)
                .map(AppUser::getWishlistBooks)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public void deleteUserBook(Long userId, Long bookId) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        appUser.getUserBooks().removeIf(book -> book.getId().equals(bookId));
        userRepository.save(appUser);
    }

    public void deleteUserFavoriteBook(Long userId, Long bookId) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        appUser.getFavoriteBooks().removeIf(book -> book.getId().equals(bookId));
        userRepository.save(appUser);
    }

    public void deleteUserWishListBook(Long userId, Long bookId) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        appUser.getWishlistBooks().removeIf(book -> book.getId().equals(bookId));
        userRepository.save(appUser);
    }

    public void addUserBook(Long userId, Book book) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        appUser.getUserBooks().add(book);
        userRepository.save(appUser);
    }

    public void addUserFavoriteBook(Long userId, Book book) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        appUser.getFavoriteBooks().add(book);
        userRepository.save(appUser);
    }

    public void addUserWishListBook(Long userId, Book book) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        appUser.getWishlistBooks().add(book);
        userRepository.save(appUser);
    }
}
