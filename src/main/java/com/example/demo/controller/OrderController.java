package com.example.demo.controller;

import com.example.demo.dto.OrderEvent;
import com.example.demo.service.SnsPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final SnsPublisherService snsPublisherService;

    @PostMapping("/publish")
    public ResponseEntity<String> publishOrder(@RequestBody OrderEvent orderEvent) {
        String messageId = snsPublisherService.publishOrderEvent(orderEvent);
        return ResponseEntity.ok("Message published with ID: " + messageId);
    }
}
