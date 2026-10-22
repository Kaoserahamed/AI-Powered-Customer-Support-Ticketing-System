package com.support.ai.dto;

import com.support.shared.entity.Sentiment;
import com.support.shared.entity.TicketCategory;

public class AIAnalysisResult {
    private TicketCategory category;
    private Sentiment sentiment;
    private String suggestedResponse;

    public AIAnalysisResult() {}

    public AIAnalysisResult(TicketCategory category, Sentiment sentiment, String suggestedResponse) {
        this.category = category;
        this.sentiment = sentiment;
        this.suggestedResponse = suggestedResponse;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TicketCategory category;
        private Sentiment sentiment;
        private String suggestedResponse;

        public Builder category(TicketCategory category) {
            this.category = category;
            return this;
        }

        public Builder sentiment(Sentiment sentiment) {
            this.sentiment = sentiment;
            return this;
        }

        public Builder suggestedResponse(String suggestedResponse) {
            this.suggestedResponse = suggestedResponse;
            return this;
        }

        public AIAnalysisResult build() {
            return new AIAnalysisResult(category, sentiment, suggestedResponse);
        }
    }

    public TicketCategory getCategory() { return category; }
    public void setCategory(TicketCategory category) { this.category = category; }
    public Sentiment getSentiment() { return sentiment; }
    public void setSentiment(Sentiment sentiment) { this.sentiment = sentiment; }
    public String getSuggestedResponse() { return suggestedResponse; }
    public void setSuggestedResponse(String suggestedResponse) { this.suggestedResponse = suggestedResponse; }
}
