import React from 'react';
import './TicketList.css';

function TicketList({ tickets, loading, onTicketSelect }) {
  const getSentimentEmoji = (sentiment) => {
    const emojis = {
      ANGRY: '😡',
      FRUSTRATED: '😤',
      NEUTRAL: '😐',
      SATISFIED: '😊',
      HAPPY: '😄'
    };
    return emojis[sentiment] || '😐';
  };

  const getStatusColor = (status) => {
    const colors = {
      OPEN: '#ff9800',
      IN_PROGRESS: '#2196f3',
      RESOLVED: '#4caf50',
      CLOSED: '#9e9e9e'
    };
    return colors[status] || '#9e9e9e';
  };

  const getCategoryColor = (category) => {
    const colors = {
      TECHNICAL: '#e91e63',
      BILLING: '#9c27b0',
      GENERAL: '#3f51b5',
      FEATURE_REQUEST: '#00bcd4',
      BUG_REPORT: '#f44336',
      ACCOUNT: '#ff9800',
      OTHER: '#607d8b'
    };
    return colors[category] || '#607d8b';
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
        <p>Loading tickets...</p>
      </div>
    );
  }

  if (tickets.length === 0) {
    return (
      <div className="empty-state">
        <h2>📭 No Tickets Yet</h2>
        <p>Create your first support ticket to get started!</p>
      </div>
    );
  }

  return (
    <div className="ticket-list-container">
      <h2>All Support Tickets ({tickets.length})</h2>
      
      <div className="tickets-grid">
        {tickets.map((ticket) => (
          <div 
            key={ticket.id} 
            className="ticket-card"
            onClick={() => onTicketSelect(ticket)}
          >
            <div className="ticket-header">
              <span className="ticket-id">#{ticket.id}</span>
              <span 
                className="ticket-status"
                style={{ backgroundColor: getStatusColor(ticket.status) }}
              >
                {ticket.status}
              </span>
            </div>
            
            <h3 className="ticket-subject">{ticket.subject}</h3>
            
            <div className="ticket-meta">
              <span 
                className="ticket-category"
                style={{ backgroundColor: getCategoryColor(ticket.category) }}
              >
                {ticket.category}
              </span>
              <span className="ticket-sentiment">
                {getSentimentEmoji(ticket.sentiment)} {ticket.sentiment}
              </span>
            </div>
            
            <div className="ticket-customer">
              <strong>{ticket.customerName}</strong>
              <span>{ticket.customerEmail}</span>
            </div>
            
            <div className="ticket-date">
              {new Date(ticket.createdAt).toLocaleDateString('en-US', {
                year: 'numeric',
                month: 'short',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
              })}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default TicketList;
