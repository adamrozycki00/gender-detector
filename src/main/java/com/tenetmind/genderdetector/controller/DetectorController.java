package com.tenetmind.genderdetector.controller;

import com.tenetmind.genderdetector.service.DetectorService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DetectorController {

    private final DetectorService detectorService;

    public DetectorController(DetectorService detectorService) {
        this.detectorService = detectorService;
    }

    @PostMapping(value = "/detect", consumes = APPLICATION_JSON_VALUE)
    public String detectGender(@RequestBody RequestDto requestDto) {
        return detectorService.detectGender(requestDto.getSourceStringToCheck(), requestDto.getDetectorVariantName());
    }

    @GetMapping(value = "/tokens")
    public List<String> getTokens(@RequestParam String gender, @RequestParam long page, @RequestParam long size) {
        try {
            return detectorService.getTokens(gender, page, size);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
