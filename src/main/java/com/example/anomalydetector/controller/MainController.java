package com.example.anomalydetector.controller;

import com.example.anomalydetector.model.ClusteringRequest;
import com.example.anomalydetector.model.DetectionRequest;
import com.example.anomalydetector.model.Result;
import com.example.anomalydetector.service.AnomalyDetectionService;
import com.example.anomalydetector.service.TrainingService;
import org.apache.spark.ml.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private AnomalyDetectionService detectionService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/train")
    public String trainModel(@RequestBody ClusteringRequest request, ModelMap model) {
        Model<?> clusteringModel = trainingService.trainModel(request);
        model.addAttribute("message", "Training completed successfully");
        return "result";
    }

    @PostMapping("/detect")
    public String detectAnomalies(@RequestBody DetectionRequest request, ModelMap model) {
        Result result = detectionService.detectAnomalies(request.getModel(), request);
        model.addAttribute("message", result.getMessage());
        return "result";
    }
}