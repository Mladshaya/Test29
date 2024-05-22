package com.example.anomalydetector.service;

import com.example.anomalydetector.model.DetectionRequest;
import com.example.anomalydetector.model.Result;
import org.apache.spark.ml.Model;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

@Service
public class AnomalyDetectionService {
    private final SparkSession spark;

    public AnomalyDetectionService() {
        this.spark = SparkSession.builder()
                .appName("AnomalyDetectorAppAdminGUI")
                .config("spark.master", "local")
                .getOrCreate();
    }

    public Result detectAnomalies(Model<?> model, DetectionRequest request) {
        Dataset<Row> data = loadData(request.getFilePath(), request.getFileType());
        // Implement anomaly detection logic using the model and metric
        // Return the result
        AnomalyDetector<?> anomalyDetector = new AnomalyDetector<>(model, new FileResultWriter(request.getFilePath()), DistanceMetricSelector.createMetric(request.getAnomalyMetric()), request.getSelectedFeatures());
        boolean detected = anomalyDetector.detectAnomalies(data);

        Result result = new Result();
        result.setMessage(detected ? "Anomalies detected" : "No anomalies detected");
        result.setSuccess(true);
        return result;
    }

    private Dataset<Row> loadData(String filePath, String fileType) {
        return spark.read()
                .format(fileType)
                .option("header", "true")
                .load(filePath);
    }
}