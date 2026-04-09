package com.support.service;

import com.support.dto.AIAnalysisResult;
import com.support.dto.TicketRequest;
import com.support.dto.TicketResponse;
import com.support.entity.Ticket;
import com.support.entity.TicketStatus;
import com.support.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final GoogleAIService googleAIService;
    
    public TicketService(TicketRepository ticketRepository, GoogleAIService googleAIService) {
        this.ticketRepository = ticketRepository;
        this.googleAIService = googleAIService;
    }
    
    @Transactional
    public TicketResponse createTicket(TicketRequest request) {
        log.info("Creating ticket for: {}", request.getCustomerEmail());
        
        // Analyze ticket with AI
        AIAnalysisResult aiAnalysis = googleAIService.analyzeTicket(
                request.getSubject(), 
                request.getDescription()
        );
        
        // Create ticket entity
        Ticket ticket = new Ticket();
        ticket.setCustomerName(request.getCustomerName());
        ticket.setCustomerEmail(request.getCustomerEmail());
        ticket.setSubject(request.getSubject());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(aiAnalysis.getCategory());
        ticket.setSentiment(aiAnalysis.getSentiment());
        ticket.setAiSuggestedResponse(aiAnalysis.getSuggestedResponse());
        ticket.setStatus(TicketStatus.OPEN);
        
        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Ticket created with ID: {}, Category: {}, Sentiment: {}", 
                savedTicket.getId(), savedTicket.getCategory(), savedTicket.getSentiment());
        
        return mapToResponse(savedTicket);
    }
    
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return mapToResponse(ticket);
    }
    
    @Transactional
    public TicketResponse updateTicketStatus(Long id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        ticket.setStatus(status);
        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToResponse(updatedTicket);
    }
    
    private TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .customerName(ticket.getCustomerName())
                .customerEmail(ticket.getCustomerEmail())
                .subject(ticket.getSubject())
                .description(ticket.getDescription())
                .category(ticket.getCategory())
                .status(ticket.getStatus())
                .sentiment(ticket.getSentiment())
                .aiSuggestedResponse(ticket.getAiSuggestedResponse())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}
