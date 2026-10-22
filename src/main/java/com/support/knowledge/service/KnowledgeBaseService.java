package com.support.knowledge.service;

import com.support.knowledge.entity.KnowledgeArticle;
import com.support.knowledge.repository.KnowledgeArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KnowledgeBaseService {

    private final KnowledgeArticleRepository articleRepository;

    public KnowledgeBaseService(KnowledgeArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<KnowledgeArticle> listPublished() {
        return articleRepository.findByStatus(KnowledgeArticle.Status.PUBLISHED);
    }

    public KnowledgeArticle getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge article not found with id: " + id));
    }

    public KnowledgeArticle createArticle(KnowledgeArticle article) {
        return articleRepository.save(article);
    }

    public KnowledgeArticle updateArticle(Long id, KnowledgeArticle article) {
        KnowledgeArticle existing = getArticle(id);
        existing.setTitle(article.getTitle());
        existing.setContent(article.getContent());
        existing.setCategory(article.getCategory());
        existing.setStatus(article.getStatus());
        existing.setVersion(existing.getVersion() + 1);
        return articleRepository.save(existing);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
