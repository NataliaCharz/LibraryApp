package com.bookcase.demo.service;

import com.bookcase.demo.entity.ContactMessage;
import com.bookcase.demo.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    public ContactMessage saveMessage(String name, String email, String message) {
        ContactMessage msg = new ContactMessage();
        msg.setName(name);
        msg.setEmail(email);
        msg.setMessage(message);
        return contactMessageRepository.save(msg);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }
}
