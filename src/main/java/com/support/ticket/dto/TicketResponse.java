package com.support.ticket.dto;

import com.support.shared.entity.Sentiment;
import com.support.shared.entity.TicketCategory;
import com.support.shared.entity.TicketStatus;
import java.time.LocalDateTime;

public class TicketResponse {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String subject;
    private String description;
    private TicketCategory category;
    private TicketStatus status;
    private Sentiment sentiment;
    private String aiSuggestedResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String customerName;
        private String customerEmail;
        private String subject;
        private String description;
        private TicketCategory category;
        private TicketStatus status;
        private Sentiment sentiment;
        private String aiSuggestedResponse;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder customerName(String customerName) { this.customerName = customerName; return this; }
        public Builder customerEmail(String customerEmail) { this.customerEmail = customerEmail; return this; }
        public Builder subject(String subject) { this.subject = subject; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder category(TicketCategory category) { this.category = category; return this; }
        public Builder status(TicketStatus status) { this.status = status; return this; }
        public Builder sentiment(Sentiment sentiment) { this.sentiment = sentiment; return this; }
        public Builder aiSuggestedResponse(String aiSuggestedResponse) { this.aiSuggestedResponse = aiSuggestedResponse; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public TicketResponse build() {
            TicketResponse response = new TicketResponse();
            response.id = this.id;
            response.customerName = this.customerName;
            response.customerEmail = this.customerEmail;
            response.subject = this.subject;
            response.description = this.description;
            response.category = this.category;
            response.status = this.status;
            response.sentiment = this.sentiment;
            response.aiSuggestedResponse = this.aiSuggestedResponse;
            response.createdAt = this.createdAt;
            response.updatedAt = this.updatedAt;
            return response;
        }
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public TicketCategory getCategory() { return category; }
    public TicketStatus getStatus() { return status; }
    public Sentiment getSentiment() { return sentiment; }
    public String getAiSuggestedResponse() { return aiSuggestedResponse; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
