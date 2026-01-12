package com.bookcase.demo.controller;

import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.entity.AppUser;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.service.AppUserService;
import com.bookcase.demo.service.BookService;
import com.bookcase.demo.service.WishlistService;
import lombok.RequiredArgsConstructor;
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
    private final WishlistService wishlistService;

    @GetMapping("/books")
    public Set<BookDTO> getUserBooks(@AuthenticationPrincipal AppUser user) {
        return appUserService.getUserBooks(user.getId())
                .stream().map(bookMapper::mapBookToDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @PostMapping("/books/add/{bookId}")
    public void addBookToUserBooks(@AuthenticationPrincipal AppUser user, @PathVariable Long bookId) {
        appUserService.addUserBook(user.getId(),
                bookService.getBookByIdService(bookId));
    }

    @DeleteMapping("/books/delete/{bookId}")
    public void deleteBookFromUserBooks(@AuthenticationPrincipal AppUser user, @PathVariable Long bookId) {
        appUserService.deleteUserBook(user.getId(), bookId);
    }

    @GetMapping("/books/favorite")
    public Set<BookDTO> getUserFavoriteBooks(@AuthenticationPrincipal AppUser user) {
        return appUserService.getUserFavoriteBooks(user.getId())
                .stream().map(bookMapper::mapBookToDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @PostMapping("/books/favorite/add/{bookId}")
    public void addBookToUserFavoriteBooks(@AuthenticationPrincipal AppUser user, @PathVariable Long bookId) {
        appUserService.addUserFavoriteBook(user.getId(),
                bookService.getBookByIdService(bookId));
    }

    @DeleteMapping("/books/favorite/delete/{bookId}")
    public void deleteBookFromUserFavoriteBooks(@AuthenticationPrincipal AppUser user, @PathVariable Long bookId) {
        appUserService.deleteUserFavoriteBook(user.getId(), bookId);
    }

    @GetMapping("/books/wishlist")
    public Set<BookDTO> getUserWishlistBooks(@AuthenticationPrincipal AppUser user) {
        return appUserService.getUserWishListBooks(user.getId())
                .stream().map(bookMapper::mapBookToDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @PostMapping("/books/wishlist/add/{bookId}")
    public void addBookToUserWishlistBooks(@AuthenticationPrincipal AppUser user, @PathVariable Long bookId) {
        appUserService.addUserWishListBook(user.getId(),
                bookService.getBookByIdService(bookId));
    }

    @DeleteMapping("/books/wishlist/delete/{bookId}")
    public void deleteBookFromUserWishlistBooks(@AuthenticationPrincipal AppUser user, @PathVariable Long bookId) {
        appUserService.deleteUserWishListBook(user.getId(), bookId);
    }

    @GetMapping("/books/wishlist/read-status")
    public Boolean getReadStatus(@RequestParam AppUser user, @RequestParam Long bookId) {
        return wishlistService.checkIfRead(user.getId(), bookId);
    }

    @PostMapping("/books/wishlist/mark-read")
    public void markAsRead(@RequestParam AppUser user, @RequestParam Long bookId) {
        wishlistService.markAsRead(user.getId(), bookId);
    }

    @PostMapping("/books/wishlist/mark-unread")
    public void markAsUnread(@RequestParam AppUser user, @RequestParam Long bookId) {
        wishlistService.markAsUnread(user.getId(), bookId);
    }

}
