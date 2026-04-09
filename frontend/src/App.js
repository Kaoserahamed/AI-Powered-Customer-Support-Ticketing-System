import React, { useState, useEffect } from 'react';
import './App.css';
import TicketForm from './components/TicketForm';
import TicketList from './components/TicketList';
import TicketDetails from './components/TicketDetails';
import axios from 'axios';

function App() {
  const [tickets, setTickets] = useState([]);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [loading, setLoading] = useState(false);
  const [view, setView] = useState('form');

  useEffect(() => {
    if (view === 'list') {
      fetchTickets();
    }
  }, [view]);

  const fetchTickets = async () => {
    setLoading(true);
    try {
      const response = await axios.get('/api/tickets');
      setTickets(response.data);
    } catch (error) {
      console.error('Error fetching tickets:', error);
      alert('Failed to fetch tickets');
    } finally {
      setLoading(false);
    }
  };

  const handleTicketCreated = (newTicket) => {
    setSelectedTicket(newTicket);
    setView('details');
  };

  const handleTicketSelect = (ticket) => {
    setSelectedTicket(ticket);
    setView('details');
  };

  const handleBackToList = () => {
    setView('list');
    setSelectedTicket(null);
  };

  const handleBackToForm = () => {
    setView('form');
    setSelectedTicket(null);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>🤖 AI-Powered Support Ticketing</h1>
        <p>Intelligent ticket categorization & sentiment analysis</p>
      </header>

      <nav className="nav-tabs">
        <button 
          className={view === 'form' ? 'active' : ''} 
          onClick={() => setView('form')}
        >
          Create Ticket
        </button>
        <button 
          className={view === 'list' || view === 'details' ? 'active' : ''} 
          onClick={() => setView('list')}
        >
          View Tickets
        </button>
      </nav>

      <main className="main-content">
        {view === 'form' && (
          <TicketForm onTicketCreated={handleTicketCreated} />
        )}
        
        {view === 'list' && (
          <TicketList 
            tickets={tickets} 
            loading={loading}
            onTicketSelect={handleTicketSelect}
          />
        )}
        
        {view === 'details' && selectedTicket && (
          <TicketDetails 
            ticket={selectedTicket}
            onBack={handleBackToList}
          />
        )}
      </main>

      <footer className="App-footer">
        <p>Powered by Spring Boot + Spring AI + React</p>
      </footer>
    </div>
  );
}

export default App;
