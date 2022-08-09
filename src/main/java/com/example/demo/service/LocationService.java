package com.example.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.HawkerCentre;
import com.example.demo.models.Feature;
import com.example.demo.models.FeatureCollection;
import com.example.demo.models.HawkerCentreDTO;
import com.example.demo.repository.LocationRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    public static final String JSON_FILE_PATH = "src/main/resources/hawker-centres-geojson.geojson";
    public static final String NAME_ELEMENT = "th:contains(NAME)";
    public static final String PHOTO_ELEMENT = "th:contains(PHOTOURL)";

    Logger logger = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    private LocationRepository locationRepository;

    public void addCentres() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            FeatureCollection featureCollection = objectMapper
                    .readValue(new File(JSON_FILE_PATH), FeatureCollection.class);
            for (Feature feature : featureCollection.getFeatures()) {
                String description = feature.getProperties().getDescription();

                Document document = Jsoup.parse(description);

                /* Fetching name element from description */
                Element nameElement = document.select(NAME_ELEMENT).get(0);
                String name = nameElement.nextElementSibling().ownText();

                logger.info("Centre name {}", name);

                /* Fetching photo url element from description */
                Element photoUrlElement = document.select(PHOTO_ELEMENT).get(0);
                String photoUrl = photoUrlElement.nextElementSibling().ownText();

                logger.info("Centre photo url {}", photoUrl);

                locationRepository.save(new HawkerCentre(feature.getProperties().getName(),
                        name,
                        photoUrl,
                        feature.getGeometry().getCoordinates().get(1),
                        feature.getGeometry().getCoordinates().get(0)));
            }
            logger.info("Saving centres into database");

        } catch (Exception e) {
            logger.error("Exception occured", e);
        }
    }

    public List<HawkerCentreDTO> fetchCentres(Double latitude, Double longitude) {
        logger.info("fetching nearest centres from database  for latitude: {} and longitude: {}", latitude, longitude);
        List<HawkerCentreDTO> hawkerCentres = locationRepository.findNearestCentres(latitude,
                longitude);
        if (!hawkerCentres.isEmpty()) {
            return hawkerCentres.subList(0, 5);
        } else {
            return new ArrayList<>();
        }
    }
}
