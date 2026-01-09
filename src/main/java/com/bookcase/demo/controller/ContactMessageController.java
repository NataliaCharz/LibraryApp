package com.bookcase.demo.controller;

import com.bookcase.demo.entity.ContactMessage;
import com.bookcase.demo.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {
    private final ContactMessageService contactMessageService;

    @PostMapping("/send")
    public ContactMessage sendMessage(@RequestBody ContactMessage msg) {
        return contactMessageService.saveMessage(msg.getName(), msg.getEmail(), msg.getMessage());
    }

    @GetMapping
    public List<ContactMessage> getMessages() {
        return contactMessageService.getAllMessages();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@PathVariable Long id) {
        contactMessageService.deleteMessageById(id);

    }
}
