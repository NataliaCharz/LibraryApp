package com.bookcase.demo.service;

import com.bookcase.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final UserRepository userRepository;

    public Boolean checkIfRead(Long userId, Long bookId) {
        return userRepository.getReadStatus(userId, bookId);
    }

    public void markAsRead(Long userId, Long bookId) {
        userRepository.updateReadStatus(userId, bookId, true);
    }

    public void markAsUnread(Long userId, Long bookId) {
        userRepository.updateReadStatus(userId, bookId, false);
    }
}

