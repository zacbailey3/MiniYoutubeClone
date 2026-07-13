package com.minitube.auth;

// Represents what auth endpoints return to frontend


public record AuthResponse( 
    String token,
    Long userId,
    String username,
    String email) {
    }


