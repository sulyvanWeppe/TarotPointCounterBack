package com.sulwep7.tarotpointcounterback.controller;

import com.sulwep7.metricscommon.model.ApiMetric;
import com.sulwep7.tarotpointcounterback.annotation.metrics.EnableApiMetric;
import com.sulwep7.tarotpointcounterback.kafka.producer.KafkaProducer;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ping")
public class PingController {

    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping
    @ApiOperation(value = "Pinging endpoint to ensure the app is up")
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Application is running")
    })
    @EnableApiMetric
    public ResponseEntity<String> ping(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>("Application is running", HttpStatus.OK);
    }
}
