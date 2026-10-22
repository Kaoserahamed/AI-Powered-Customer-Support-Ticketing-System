package com.support.knowledge.dto;

import com.support.knowledge.entity.KnowledgeArticle;
import java.time.LocalDateTime;

public class KnowledgeArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private KnowledgeArticle.Status status;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private String category;
        private KnowledgeArticle.Status status;
        private Integer version;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder content(String content) { this.content = content; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder status(KnowledgeArticle.Status status) { this.status = status; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public KnowledgeArticleResponse build() {
            KnowledgeArticleResponse response = new KnowledgeArticleResponse();
            response.id = this.id;
            response.title = this.title;
            response.content = this.content;
            response.category = this.category;
            response.status = this.status;
            response.version = this.version;
            response.createdAt = this.createdAt;
            response.updatedAt = this.updatedAt;
            return response;
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public KnowledgeArticle.Status getStatus() { return status; }
    public Integer getVersion() { return version; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
