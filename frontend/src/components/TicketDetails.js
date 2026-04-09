import React, { useState } from 'react';
import axios from 'axios';
import './TicketDetails.css';

function TicketDetails({ ticket, onBack }) {
  const [currentTicket, setCurrentTicket] = useState(ticket);
  const [updating, setUpdating] = useState(false);

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

  const handleStatusUpdate = async (newStatus) => {
    setUpdating(true);
    try {
      const response = await axios.put(
        `/api/tickets/${currentTicket.id}/status?status=${newStatus}`
      );
      setCurrentTicket(response.data);
      alert(`✅ Status updated to ${newStatus}`);
    } catch (error) {
      console.error('Error updating status:', error);
      alert('Failed to update status');
    } finally {
      setUpdating(false);
    }
  };

  return (
    <div className="ticket-details-container">
      <button className="back-btn" onClick={onBack}>
        ← Back to List
      </button>

      <div className="ticket-details-card">
        <div className="details-header">
          <div>
            <h2>Ticket #{currentTicket.id}</h2>
            <p className="ticket-created">
              Created: {new Date(currentTicket.createdAt).toLocaleString()}
            </p>
          </div>
          <span 
            className="status-badge"
            style={{ backgroundColor: getStatusColor(currentTicket.status) }}
          >
            {currentTicket.status}
          </span>
        </div>

        <div className="details-section">
          <h3>Subject</h3>
          <p className="subject-text">{currentTicket.subject}</p>
        </div>

        <div className="details-section">
          <h3>Customer Information</h3>
          <div className="customer-info">
            <div className="info-row">
              <span className="info-label">Name:</span>
              <span className="info-value">{currentTicket.customerName}</span>
            </div>
            <div className="info-row">
              <span className="info-label">Email:</span>
              <span className="info-value">{currentTicket.customerEmail}</span>
            </div>
          </div>
        </div>

        <div className="details-section">
          <h3>Description</h3>
          <p className="description-text">{currentTicket.description}</p>
        </div>

        <div className="ai-analysis-section">
          <h3>🤖 AI Analysis</h3>
          
          <div className="analysis-grid">
            <div className="analysis-item">
              <span className="analysis-label">Category</span>
              <span 
                className="analysis-badge"
                style={{ backgroundColor: getCategoryColor(currentTicket.category) }}
              >
                {currentTicket.category}
              </span>
            </div>
            
            <div className="analysis-item">
              <span className="analysis-label">Sentiment</span>
              <span className="analysis-badge sentiment-badge">
                {getSentimentEmoji(currentTicket.sentiment)} {currentTicket.sentiment}
              </span>
            </div>
          </div>

          <div className="suggested-response">
            <h4>💬 AI Suggested Response</h4>
            <div className="response-box">
              {currentTicket.aiSuggestedResponse}
            </div>
          </div>
        </div>

        <div className="status-update-section">
          <h3>Update Status</h3>
          <div className="status-buttons">
            <button 
              onClick={() => handleStatusUpdate('OPEN')}
              disabled={updating || currentTicket.status === 'OPEN'}
              className="status-btn open"
            >
              Open
            </button>
            <button 
              onClick={() => handleStatusUpdate('IN_PROGRESS')}
              disabled={updating || currentTicket.status === 'IN_PROGRESS'}
              className="status-btn in-progress"
            >
              In Progress
            </button>
            <button 
              onClick={() => handleStatusUpdate('RESOLVED')}
              disabled={updating || currentTicket.status === 'RESOLVED'}
              className="status-btn resolved"
            >
              Resolved
            </button>
            <button 
              onClick={() => handleStatusUpdate('CLOSED')}
              disabled={updating || currentTicket.status === 'CLOSED'}
              className="status-btn closed"
            >
              Closed
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default TicketDetails;
