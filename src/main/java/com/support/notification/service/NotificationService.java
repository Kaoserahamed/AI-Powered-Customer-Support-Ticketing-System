package com.support.notification.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendTicketCreatedNotification(Long ticketId, String customerEmail) {
    }

    public void sendStatusChangeNotification(Long ticketId, String customerEmail, String newStatus) {
    }

    public void sendAssignmentNotification(Long ticketId, String customerEmail, String agentName) {
    }
}
