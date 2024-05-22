package com.example.anomalydetector.service;

import com.example.anomalydetector.model.ClusteringRequest;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.clustering.BisectingKMeansModel;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
    private final SparkSession spark;

    public TrainingService() {
        this.spark = SparkSession.builder()
                .appName("AnomalyDetectorAppAdminGUI")
                .config("spark.master", "local")
                .getOrCreate();
    }

    public Model<?> trainModel(ClusteringRequest request) {
        Dataset<Row> data = loadData(request.getFilePath(), request.getFileType());
        ClusterizerSelector clusterizerSelect = new ClusterizerSelector();
        Clusterizer<?> clusterizer = clusterizerSelect.createClusterizer(request.getAlgorithm());
        return clusterizer.cluster(data, request.getSelectedFeatures());
    }

    private Dataset<Row> loadData(String filePath, String fileType) {
        return spark.read()
                .format(fileType)
                .option("header", "true")
                .load(filePath);
    }
}