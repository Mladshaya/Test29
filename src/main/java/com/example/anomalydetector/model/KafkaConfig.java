package com.example.anomalydetector.model;

import lombok.Data;

@Data
public class KafkaConfig {
    private String brokers;
    private String topic;
}