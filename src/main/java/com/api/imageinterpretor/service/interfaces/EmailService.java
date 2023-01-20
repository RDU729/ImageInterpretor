package com.api.imageinterpretor.service.interfaces;

public interface EmailService {
    void sendActivationEmail(String recipient, String activationCode);
}
