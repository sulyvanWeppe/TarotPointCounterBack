package com.sulwep7.tarotpointcounterback.controller;

import com.sulwep7.metricscommon.annotation.EnableApiMetric;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ping")
@Slf4j
public class PingController {

    @GetMapping
    @ApiOperation(value = "Pinging endpoint to ensure the app is up")
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Application is running")
    })
    @EnableApiMetric
    public ResponseEntity<String> ping(HttpServletRequest httpServletRequest) {
        log.info("ping controller");
        return new ResponseEntity<>("Application is running", HttpStatus.OK);
    }
}
