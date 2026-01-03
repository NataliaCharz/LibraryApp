package com.bookcase.demo.controller;

import com.bookcase.demo.entity.ContactMessage;
import com.bookcase.demo.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {
    private final ContactMessageService contactMessageService;

    @PostMapping
    public ContactMessage sendMessage(@RequestBody Map<String, String> payload) {
        return contactMessageService.saveMessage(payload.get("name"), payload.get("email"), payload.get("message"));
    }

    @GetMapping
    public List<ContactMessage> getMessages() {
        return contactMessageService.getAllMessages();
    }
}
