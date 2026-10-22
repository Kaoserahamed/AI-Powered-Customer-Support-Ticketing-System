package com.support.auth.dto;

public class LoginResponse {
    private String token;
    private String email;
    private String role;
    private String fullName;
    private boolean mfaEnabled;

    public LoginResponse() {}

    public LoginResponse(String token, String email, String role, String fullName, boolean mfaEnabled) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.mfaEnabled = mfaEnabled;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public boolean isMfaEnabled() { return mfaEnabled; }
    public void setMfaEnabled(boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; }
}
