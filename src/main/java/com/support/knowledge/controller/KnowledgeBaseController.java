package com.support.knowledge.controller;

import com.support.knowledge.dto.KnowledgeArticleResponse;
import com.support.knowledge.service.KnowledgeBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/kb/articles")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @GetMapping
    public ResponseEntity<List<KnowledgeArticleResponse>> listArticles() {
        List<KnowledgeArticleResponse> articles = knowledgeBaseService.listPublished().stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeArticleResponse> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(mapToResponse(knowledgeBaseService.getArticle(id)));
    }

    @PostMapping
    public ResponseEntity<KnowledgeArticleResponse> createArticle(@RequestBody KnowledgeArticleRequest request) {
        com.support.knowledge.entity.KnowledgeArticle article = new com.support.knowledge.entity.KnowledgeArticle();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setCategory(request.getCategory());
        article.setStatus(com.support.knowledge.entity.KnowledgeArticle.Status.DRAFT);
        return ResponseEntity.ok(mapToResponse(knowledgeBaseService.createArticle(article)));
    }

    private KnowledgeArticleResponse mapToResponse(com.support.knowledge.entity.KnowledgeArticle article) {
        return KnowledgeArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .category(article.getCategory())
                .status(article.getStatus())
                .version(article.getVersion())
                .build();
    }
}
