package com.example.demo.service;

import com.example.demo.dto.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Service
@RequiredArgsConstructor
public class SnsPublisherService {

    private final SnsClient snsClient;

    @Value("${aws.sns.topic-arn}")
    private String topicArn;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String publishOrderEvent(OrderEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);

            PublishRequest request = PublishRequest.builder()
                    .topicArn(topicArn)
                    .message(message)
                    .subject("Order Event Notification")
                    .build();
            subscribeEmailIfNotExists(topicArn,event.getEmail());
                /*SubscribeRequest request1 = SubscribeRequest.builder()
                        .topicArn(topicArn)
                        .protocol("email")
                        .endpoint(event.getEmail())
                        .build();

                snsClient.subscribe(request1);*/

            PublishResponse response = snsClient.publish(request);
            return response.messageId();

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish SNS message", e);
        }
    }

    public void subscribeEmailIfNotExists(String topicArn, String email) {

        // 1️⃣ List existing subscriptions for topic
        ListSubscriptionsByTopicRequest listRequest =
                ListSubscriptionsByTopicRequest.builder()
                        .topicArn(topicArn)
                        .build();

        ListSubscriptionsByTopicResponse response =
                snsClient.listSubscriptionsByTopic(listRequest);

        boolean alreadySubscribed = response.subscriptions().stream()
                .anyMatch(sub ->
                        sub.endpoint().equalsIgnoreCase(email)
                                && sub.protocol().equals("email"));

        if (alreadySubscribed) {
            System.out.println("Email already subscribed.");
            return;
        }

        // 2️⃣ Subscribe only if not found
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(topicArn)
                .protocol("email")
                .endpoint(email)
                .build();

        snsClient.subscribe(subscribeRequest);
        System.out.println("Subscription request sent. Please confirm via email.");
    }
}
