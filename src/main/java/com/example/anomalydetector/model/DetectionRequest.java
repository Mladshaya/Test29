package com.example.anomalydetector.model;

import lombok.Data;

@Data
public class DetectionRequest {
    private String dataSourceType;
    private String fileType;
    private String filePath;
    private String anomalyMetric;
}