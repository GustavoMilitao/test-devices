package com.example.devices.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public enum DeviceState {
    @JsonProperty("inactive")
    INACTIVE("inactive"),
    @JsonProperty("available")
    AVAILABLE("available"),
    @JsonProperty("in-use")
    IN_USE("in-use");

    private final String value;

    DeviceState(String value) {
        this.value = value;
    }

    @JsonCreator
    public static DeviceState fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value.replace("-", "_")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid state: '" + value + "'. Allowed values: inactive, available, in-use"
                ));
    }


    public String getValue() {
        return value;
    }
}
