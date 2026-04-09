package com.support.service;

import com.support.dto.AIAnalysisResult;
import com.support.entity.Sentiment;
import com.support.entity.TicketCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
public class GoogleAIService {
    
    private static final Logger log = LoggerFactory.getLogger(GoogleAIService.class);
    private final WebClient webClient;
    private final String apiKey;
    
    public GoogleAIService(@Value("${google.ai.api-key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .build();
    }
    
    @Cacheable(value = "aiAnalysis", key = "#subject + #description")
    public AIAnalysisResult analyzeTicket(String subject, String description) {
        log.info("Analyzing ticket with Google AI: {}", subject);
        
        String prompt = buildAnalysisPrompt(subject, description);
        
        try {
            String response = callGeminiAPI(prompt);
            return parseAIResponse(response);
        } catch (Exception e) {
            log.error("Error analyzing ticket with Google AI", e);
            return AIAnalysisResult.builder()
                    .category(TicketCategory.GENERAL)
                    .sentiment(Sentiment.NEUTRAL)
                    .suggestedResponse("Thank you for contacting us. We've received your request and will respond shortly.")
                    .build();
        }
    }
    
    private String callGeminiAPI(String prompt) {
        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", prompt)
                ))
            )
        );
        
        try {
            Map<String, Object> response = webClient.post()
                    .uri("/models/gemini-2.5-flash:generateContent?key=" + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            
            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                    if (!parts.isEmpty()) {
                        return (String) parts.get(0).get("text");
                    }
                }
            }
            
            return "Error: No response from AI";
        } catch (Exception e) {
            log.error("Error calling Gemini API", e);
            throw new RuntimeException("Failed to call Gemini API", e);
        }
    }
    
    private String buildAnalysisPrompt(String subject, String description) {
        return String.format("""
                Analyze this customer support ticket and provide:
                1. Category (TECHNICAL, BILLING, GENERAL, FEATURE_REQUEST, BUG_REPORT, ACCOUNT, OTHER)
                2. Sentiment (ANGRY, FRUSTRATED, NEUTRAL, SATISFIED, HAPPY)
                3. A professional suggested response (2-3 sentences)
                
                Subject: %s
                Description: %s
                
                Format your response EXACTLY as:
                CATEGORY: [category]
                SENTIMENT: [sentiment]
                RESPONSE: [your suggested response]
                """, subject, description);
    }
    
    private AIAnalysisResult parseAIResponse(String aiResponse) {
        try {
            String[] lines = aiResponse.split("\n");
            TicketCategory category = TicketCategory.OTHER;
            Sentiment sentiment = Sentiment.NEUTRAL;
            String suggestedResponse = "";
            
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("CATEGORY:")) {
                    String cat = line.substring(9).trim();
                    try {
                        category = TicketCategory.valueOf(cat);
                    } catch (IllegalArgumentException e) {
                        log.warn("Invalid category: {}", cat);
                    }
                } else if (line.startsWith("SENTIMENT:")) {
                    String sent = line.substring(10).trim();
                    try {
                        sentiment = Sentiment.valueOf(sent);
                    } catch (IllegalArgumentException e) {
                        log.warn("Invalid sentiment: {}", sent);
                    }
                } else if (line.startsWith("RESPONSE:")) {
                    suggestedResponse = line.substring(9).trim();
                }
            }
            
            if (suggestedResponse.isEmpty()) {
                suggestedResponse = "Thank you for your message. We'll get back to you soon.";
            }
            
            return AIAnalysisResult.builder()
                    .category(category)
                    .sentiment(sentiment)
                    .suggestedResponse(suggestedResponse)
                    .build();
        } catch (Exception e) {
            log.error("Error parsing AI response", e);
            return AIAnalysisResult.builder()
                    .category(TicketCategory.OTHER)
                    .sentiment(Sentiment.NEUTRAL)
                    .suggestedResponse("Thank you for your message. We'll get back to you soon.")
                    .build();
        }
    }
}
