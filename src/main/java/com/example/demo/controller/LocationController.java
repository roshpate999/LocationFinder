package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.HawkerCentreDTO;
import com.example.demo.service.LocationService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class LocationController {

    Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @GetMapping(value = "/fetchCentres")
    public List<HawkerCentreDTO> fetchCentres(@RequestParam Double latitude, @RequestParam Double longitude) {
        logger.info("fetchCentres endpoint called");
        return locationService.fetchCentres(latitude, longitude);
    }

    @GetMapping(value = "/addCentres")
    public void addCentres() {
        try {
            logger.info("addCentres endpoint called");
            locationService.addCentres();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }
    }
}
