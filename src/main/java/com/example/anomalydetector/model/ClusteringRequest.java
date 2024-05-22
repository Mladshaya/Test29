package com.example.anomalydetector.model;

import lombok.Data;

@Data
public class ClusteringRequest {
    private String dataSourceType;
    private String fileType;
    private String filePath;
    private String outputType;
    private String outputDestination;
    private String kafkaBrokers;
    private String kafkaTopic;
    private String algorithm;
    private String anomalyMetric;
    private String[] selectedFeatures;
}