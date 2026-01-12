package com.bookcase.demo.service;

import com.bookcase.demo.controller.NotificationController;
import com.bookcase.demo.entity.AppUser;
import com.bookcase.demo.entity.Book;
import com.bookcase.demo.exception.UserNotFoundException;
import com.bookcase.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final UserRepository userRepository;
    private final NotificationController notificationController;

    public Set<Book> getUserBooks(Long id) {
        return userRepository.findByIdWithBooks(id)
                .map(AppUser::getUserBooks)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Set<Book> getUserFavoriteBooks(Long id) {
        return userRepository.findByIdWithFavoriteBooks(id)
                .map(AppUser::getFavoriteBooks)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Set<Book> getUserWishListBooks(Long id) {
        return userRepository.findByIdWithWishlistBooks(id)
                .map(AppUser::getWishlistBooks)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUserBook(Long userId, Long bookId) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        appUser.getUserBooks().removeIf(book -> book.getId().equals(bookId));
        userRepository.save(appUser);
        notificationController.sendNotification("Book removed from your books");
    }

    public void deleteUserFavoriteBook(Long userId, Long bookId) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        appUser.getFavoriteBooks().removeIf(book -> book.getId().equals(bookId));
        userRepository.save(appUser);
        notificationController.sendNotification("Book removed from your favorite books");
    }

    public void deleteUserWishListBook(Long userId, Long bookId) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        appUser.getWishlistBooks().removeIf(book -> book.getId().equals(bookId));
        userRepository.save(appUser);
        notificationController.sendNotification("Book removed from your wishlist");
    }

    public void addUserBook(Long userId, Book book) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        appUser.getUserBooks().add(book);
        userRepository.save(appUser);
        notificationController.sendNotification("Book added to your books");
    }

    public void addUserFavoriteBook(Long userId, Book book) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        appUser.getFavoriteBooks().add(book);
        userRepository.save(appUser);
        notificationController.sendNotification("Book added to your favorite books");
    }

    public void addUserWishListBook(Long userId, Book book) {
        AppUser appUser = userRepository.findAppUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        appUser.getWishlistBooks().add(book);
        userRepository.save(appUser);
        notificationController.sendNotification("Book added to your wishlist");
    }
}
