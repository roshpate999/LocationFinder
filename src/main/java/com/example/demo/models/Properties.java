package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Properties {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
