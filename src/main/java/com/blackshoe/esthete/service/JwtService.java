package com.blackshoe.esthete.service;

import io.jsonwebtoken.SignatureException;

import java.util.UUID;

public interface JwtService {
    public UUID extractUserId(String token) throws SignatureException;
}
