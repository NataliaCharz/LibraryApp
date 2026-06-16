package com.bookcase.demo.controller;

import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.entity.AppUser;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.service.AppUserService;
import com.bookcase.demo.service.BookService;
import com.bookcase.demo.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final BookMapper bookMapper;
    private final BookService bookService;
    private final WishlistService wishlistService;

    private AppUser resolveUser(Jwt jwt) {
        return appUserService.resolveOrCreateUser(
                jwt.getSubject(),
                jwt.getClaimAsString("preferred_username")
        );
    }

    @GetMapping("/books")
    public Set<BookDTO> getUserBooks(@AuthenticationPrincipal Jwt jwt) {
        return appUserService.getUserBooks(resolveUser(jwt).getId())
                .stream().map(bookMapper::mapBookToDto)
                .collect(Collectors.toSet());
    }

    @PostMapping("/books/add/{bookId}")
    public void addBookToUserBooks(@AuthenticationPrincipal Jwt jwt, @PathVariable Long bookId) {
        appUserService.addUserBook(resolveUser(jwt).getId(), bookService.getBookByIdService(bookId));
    }

    @DeleteMapping("/books/delete/{bookId}")
    public void deleteBookFromUserBooks(@AuthenticationPrincipal Jwt jwt, @PathVariable Long bookId) {
        appUserService.deleteUserBook(resolveUser(jwt).getId(), bookId);
    }

    @GetMapping("/books/favorite")
    public Set<BookDTO> getUserFavoriteBooks(@AuthenticationPrincipal Jwt jwt) {
        return appUserService.getUserFavoriteBooks(resolveUser(jwt).getId())
                .stream().map(bookMapper::mapBookToDto)
                .collect(Collectors.toSet());
    }

    @PostMapping("/books/favorite/add/{bookId}")
    public void addBookToUserFavoriteBooks(@AuthenticationPrincipal Jwt jwt, @PathVariable Long bookId) {
        appUserService.addUserFavoriteBook(resolveUser(jwt).getId(), bookService.getBookByIdService(bookId));
    }

    @DeleteMapping("/books/favorite/delete/{bookId}")
    public void deleteBookFromUserFavoriteBooks(@AuthenticationPrincipal Jwt jwt, @PathVariable Long bookId) {
        appUserService.deleteUserFavoriteBook(resolveUser(jwt).getId(), bookId);
    }

    @GetMapping("/books/wishlist")
    public Set<BookDTO> getUserWishlistBooks(@AuthenticationPrincipal Jwt jwt) {
        return appUserService.getUserWishListBooks(resolveUser(jwt).getId())
                .stream().map(bookMapper::mapBookToDto)
                .collect(Collectors.toSet());
    }

    @PostMapping("/books/wishlist/add/{bookId}")
    public void addBookToUserWishlistBooks(@AuthenticationPrincipal Jwt jwt, @PathVariable Long bookId) {
        appUserService.addUserWishListBook(resolveUser(jwt).getId(), bookService.getBookByIdService(bookId));
    }

    @DeleteMapping("/books/wishlist/delete/{bookId}")
    public void deleteBookFromUserWishlistBooks(@AuthenticationPrincipal Jwt jwt, @PathVariable Long bookId) {
        appUserService.deleteUserWishListBook(resolveUser(jwt).getId(), bookId);
    }

    @GetMapping("/books/wishlist/read-status")
    public Boolean getReadStatus(@AuthenticationPrincipal Jwt jwt, @RequestParam Long bookId) {
        return wishlistService.checkIfRead(resolveUser(jwt).getId(), bookId);
    }

    @PostMapping("/books/wishlist/mark-read")
    public void markAsRead(@AuthenticationPrincipal Jwt jwt, @RequestParam Long bookId) {
        wishlistService.markAsRead(resolveUser(jwt).getId(), bookId);
    }

    @PostMapping("/books/wishlist/mark-unread")
    public void markAsUnread(@AuthenticationPrincipal Jwt jwt, @RequestParam Long bookId) {
        wishlistService.markAsUnread(resolveUser(jwt).getId(), bookId);
    }
}
