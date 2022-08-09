package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Feature {

    @JsonProperty("properties")
    private Properties properties;

    @JsonProperty("geometry")
    private Geometry geometry;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

}
