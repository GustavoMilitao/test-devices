package com.example.devices.dto;

import com.example.devices.model.DeviceState;

public class DeviceUpdateDto {
    private String name;
    private String brand;
    private DeviceState state;

    public DeviceUpdateDto() {
    }

    public DeviceUpdateDto(String name, String brand, DeviceState state) {
        this.name = name;
        this.brand = brand;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }
}