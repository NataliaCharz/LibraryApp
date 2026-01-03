package com.bookcase.demo.controller;

import com.bookcase.demo.entity.ContactMessage;
import com.bookcase.demo.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/contact")
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
}
