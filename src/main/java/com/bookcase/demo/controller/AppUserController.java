package com.bookcase.demo.controller;

import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.service.AppUserService;
import com.bookcase.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final BookMapper bookMapper;
    private final BookService bookService;

    @GetMapping("/books")
    @PreAuthorize("hasRole('USER')")
    public Set<BookDTO> getUserBooks(@AuthenticationPrincipal Long userId) {
        return appUserService.getUserBooks(userId)
                .stream().map(bookMapper::mapBookToDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @GetMapping("/books/favorite")
    @PreAuthorize("hasRole('USER')")
    public Set<BookDTO> getUserFavoriteBooks(@AuthenticationPrincipal Long userId) {
        return appUserService.getUserFavoriteBooks(userId)
                .stream().map(bookMapper::mapBookToDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @GetMapping("books/wishlist")
    @PreAuthorize("hasRole('USER')")
    public Set<BookDTO> getUserWishlistBooks(@AuthenticationPrincipal Long userId) {
        return appUserService.getUserWishListBooks(userId)
                .stream().map(bookMapper::mapBookToDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @PostMapping("/books/add/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public void addBookToUserBooks(@AuthenticationPrincipal Long userId, @PathVariable Long bookId) {
        appUserService.addUserBook(userId,
                bookService.getById(bookId));
    }

    @DeleteMapping("/books/delete/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteBookFromUserBooks(@AuthenticationPrincipal Long userId, @PathVariable Long bookId) {
        appUserService.deleteUserBook(userId, bookId);
    }

    @PostMapping("/books/favorite/add/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public void addBookToUserFavoriteBooks(@AuthenticationPrincipal Long userId, @PathVariable Long bookId) {
        appUserService.addUserFavoriteBook(userId,
                bookService.getById(bookId));
    }

    @DeleteMapping("/books/favorite/delete/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteBookFromUserFavoriteBooks(@AuthenticationPrincipal Long userId, @PathVariable Long bookId) {
        appUserService.deleteUserFavoriteBook(userId, bookId);
    }

    @PostMapping("/books/wishlist/add/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public void addBookToUserWishlistBooks(@AuthenticationPrincipal Long userId, @PathVariable Long bookId) {
        appUserService.addUserWishListBook(userId,
                bookService.getById(bookId));
    }

    @DeleteMapping("/books/wishlist/delete/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteBookFromUserWishlistBooks(@AuthenticationPrincipal Long userId, @PathVariable Long bookId) {
        appUserService.deleteUserWishListBook(userId, bookId);
    }


}
