package com.bookcase.demo.service;

import com.bookcase.demo.controller.NotificationController;
import com.bookcase.demo.entity.ContactMessage;
import com.bookcase.demo.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;
    private final MqttService mqttService;
    private final NotificationController notificationController;

    public ContactMessage saveMessage(String name, String email, String message) {
        ContactMessage msg = new ContactMessage();
        msg.setName(name);
        msg.setEmail(email);
        msg.setMessage(message);
        notificationController.sendNotification("New contact message from: " + name + ", email: " + email);
        mqttService.publish("New contact message received from " + name);
        return contactMessageRepository.save(msg);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }

    public void deleteMessageById(Long id) {
        contactMessageRepository.findById(id).ifPresent(contactMessageRepository::delete);
        mqttService.publish("Contact message with ID " + id + " has been deleted.");
    }

    public ContactMessage updateMessageService(Long id, String name, String email, String message) {
        Optional<ContactMessage> foundContactMessage = contactMessageRepository.findById(id);
        if (foundContactMessage.isEmpty()) {
            throw new RuntimeException("Contact message not found with id: " + id);
        }
        ContactMessage updatedMessage = foundContactMessage.get();
        updatedMessage.setName(name);
        updatedMessage.setEmail(email);
        updatedMessage.setMessage(message);
        mqttService.publish("Contact message with ID " + id + " has been updated.");
        return contactMessageRepository.save(updatedMessage);
    }
}
