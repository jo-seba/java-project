package com.concertticketing.kafkaconsumer.domain.concert.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertPaymentService {
    private final Random random = new Random();

    public boolean processPayment() {
        return random.nextBoolean();
    }
}
