const axios = require('axios');

const API_URL = 'http://localhost:8080/api/tickets';

const sampleTickets = [
  {
    customerName: "Sarah Johnson",
    customerEmail: "sarah.johnson@example.com",
    subject: "Cannot login to my account",
    description: "I've been trying to login for the past hour but keep getting an 'Invalid credentials' error. I'm absolutely sure my password is correct. This is very frustrating as I need to access my account urgently for an important meeting!"
  },
  {
    customerName: "Michael Chen",
    customerEmail: "michael.chen@techcorp.com",
    subject: "Charged twice for subscription",
    description: "I noticed that my credit card was charged $99.99 twice this month for my premium subscription. I only authorized one payment. This is unacceptable! I want a refund immediately and an explanation of why this happened."
  },
  {
    customerName: "Emily Rodriguez",
    customerEmail: "emily.r@startup.io",
    subject: "Love the new dashboard feature!",
    description: "Just wanted to say the new dashboard is amazing! The analytics are so clear and the UI is beautiful. Would love to see dark mode added as well. Keep up the great work!"
  },
  {
    customerName: "David Thompson",
    customerEmail: "d.thompson@business.com",
    subject: "Question about pricing plans",
    description: "Can you explain the difference between the Pro and Enterprise plans? I'm trying to decide which one would be best for our team of 15 people. Also, do you offer any discounts for annual subscriptions?"
  },
  {
    customerName: "Lisa Anderson",
    customerEmail: "lisa.anderson@gmail.com",
    subject: "App crashes when uploading files",
    description: "Every time I try to upload a PDF file larger than 5MB, the application crashes completely. I'm using Chrome on Windows 11. This is a critical bug that's preventing me from doing my work. Please fix this ASAP!"
  },
  {
    customerName: "James Wilson",
    customerEmail: "james.w@consulting.com",
    subject: "Request for API documentation",
    description: "I'm a developer trying to integrate your service with our platform. Could you please provide detailed API documentation? Specifically, I need information about authentication, rate limits, and webhook endpoints."
  },
  {
    customerName: "Maria Garcia",
    customerEmail: "maria.garcia@design.studio",
    subject: "Account locked after password reset",
    description: "I reset my password yesterday and now my account is locked. I've tried the 'forgot password' link multiple times but I'm not receiving any emails. I'm getting worried about losing access to my data. Please help!"
  },
  {
    customerName: "Robert Kim",
    customerEmail: "robert.kim@finance.com",
    subject: "Feature request: Export to Excel",
    description: "It would be really helpful if we could export our reports directly to Excel format instead of just CSV. This would save us a lot of time in our monthly reporting process. Is this something you're planning to add?"
  },
  {
    customerName: "Jennifer Lee",
    customerEmail: "jennifer.lee@marketing.agency",
    subject: "Thank you for excellent support!",
    description: "I just wanted to thank your support team for the amazing help I received yesterday. The agent was patient, knowledgeable, and solved my issue quickly. You have a great team!"
  },
  {
    customerName: "Thomas Brown",
    customerEmail: "thomas.brown@enterprise.com",
    subject: "Upgrade to Enterprise plan",
    description: "We're currently on the Pro plan but need to upgrade to Enterprise. What's the process? Do we need to contact sales? Also, will there be any downtime during the migration?"
  }
];

async function createTicket(ticket, index) {
  try {
    console.log(`\n[${index + 1}/${sampleTickets.length}] Creating ticket: "${ticket.subject}"`);
    console.log(`   Customer: ${ticket.customerName}`);
    
    const response = await axios.post(API_URL, ticket);
    
    console.log(`   ✅ Created successfully!`);
    console.log(`   📋 Ticket ID: #${response.data.id}`);
    console.log(`   🏷️  Category: ${response.data.category}`);
    console.log(`   😊 Sentiment: ${response.data.sentiment}`);
    console.log(`   💬 AI Response: "${response.data.aiSuggestedResponse.substring(0, 80)}..."`);
    
    return response.data;
  } catch (error) {
    console.error(`   ❌ Error creating ticket:`, error.response?.data || error.message);
    return null;
  }
}

async function runDemo() {
  console.log('╔════════════════════════════════════════════════════════════╗');
  console.log('║   AI-Powered Ticketing System - Demo Data Generator       ║');
  console.log('╚════════════════════════════════════════════════════════════╝');
  console.log('\n🚀 Starting demo data creation...');
  console.log(`📊 Will create ${sampleTickets.length} sample tickets\n`);
  
  const results = [];
  
  for (let i = 0; i < sampleTickets.length; i++) {
    const result = await createTicket(sampleTickets[i], i);
    if (result) {
      results.push(result);
    }
    // Wait 2 seconds between requests to avoid overwhelming the AI API
    if (i < sampleTickets.length - 1) {
      await new Promise(resolve => setTimeout(resolve, 2000));
    }
  }
  
  console.log('\n╔════════════════════════════════════════════════════════════╗');
  console.log('║                    Demo Complete!                          ║');
  console.log('╚════════════════════════════════════════════════════════════╝');
  console.log(`\n✅ Successfully created ${results.length} out of ${sampleTickets.length} tickets`);
  console.log('\n📊 Summary:');
  
  const categories = {};
  const sentiments = {};
  
  results.forEach(ticket => {
    categories[ticket.category] = (categories[ticket.category] || 0) + 1;
    sentiments[ticket.sentiment] = (sentiments[ticket.sentiment] || 0) + 1;
  });
  
  console.log('\n🏷️  Categories:');
  Object.entries(categories).forEach(([cat, count]) => {
    console.log(`   ${cat}: ${count}`);
  });
  
  console.log('\n😊 Sentiments:');
  Object.entries(sentiments).forEach(([sent, count]) => {
    console.log(`   ${sent}: ${count}`);
  });
  
  console.log('\n🌐 Open http://localhost:3000 to view the tickets!');
  console.log('📋 Click "View Tickets" to see all the AI-analyzed tickets\n');
}

// Check if backend is running
async function checkBackend() {
  try {
    await axios.get('http://localhost:8080/api/tickets');
    return true;
  } catch (error) {
    console.error('❌ Error: Backend is not running!');
    console.error('Please start the backend first:');
    console.error('   mvn spring-boot:run -Dspring-boot.run.profiles=h2\n');
    return false;
  }
}

// Main execution
(async () => {
  const backendRunning = await checkBackend();
  if (backendRunning) {
    await runDemo();
  }
})();
