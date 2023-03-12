package com.sulwep7.tarotpointcounterback.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {
    @GetMapping
    @ApiOperation(value = "Pinging endpoint to ensure the app is up")
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Application is running")
    })
    public ResponseEntity<String> ping() {
        return new ResponseEntity("Application is running", HttpStatus.OK);
    }
}
