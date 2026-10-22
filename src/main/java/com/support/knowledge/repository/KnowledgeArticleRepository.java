package com.support.knowledge.repository;

import com.support.knowledge.entity.KnowledgeArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle, Long> {
    List<KnowledgeArticle> findByStatus(KnowledgeArticle.Status status);
    List<KnowledgeArticle> findByCategory(String category);
}
