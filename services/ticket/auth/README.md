# AI-Powered-Customer-Support-Ticketing-System Authentication Module

## Overview
Authentication system for customer/agent/admin roles with OAuth2, JWT, and MFA.

## Files
- `services/ticket/auth.py` - Authentication logic
- `services/ticket/models.py` - User and role models
- `services/ticket/middleware.py` - Auth middleware and RBAC
- `services/ticket/routes/auth.py` - Auth API routes
- `services/ticket/mfa.py` - Multi-factor authentication

## Features
- Customer, agent, and admin account registration/login
- JWT-based session management
- OAuth2 provider integration
- Role-Based Access Control (RBAC)
- Multi-factor authentication (MFA)
- Password hashing with bcrypt
- Session timeout handling

## Tech Stack
- Java/Spring Boot 3.2
- PostgreSQL/H2
- JWT (jjwt)
- OAuth2 (Spring Security OAuth2)
- BCrypt for password hashing

## Day 5 Outcome
Authentication module implemented. 1 PR created targeting development.