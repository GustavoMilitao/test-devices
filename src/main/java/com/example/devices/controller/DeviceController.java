package com.example.devices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.devices.model.Device;
import com.example.devices.service.DeviceService;
import com.example.devices.dto.DeviceDto;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody DeviceDto deviceDto) {
        Device createdDevice = deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        Device device = deviceService.fetchDevice(id);
        return device != null ? ResponseEntity.ok(device) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.fetchAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Device>> getDevicesByBrand(@PathVariable String brand) {
        List<Device> devices = deviceService.fetchDevicesByBrand(brand);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Device>> getDevicesByState(@PathVariable String state) {
        List<Device> devices = deviceService.fetchDevicesByState(state);
        return ResponseEntity.ok(devices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody DeviceDto deviceDto) {
        Device updatedDevice = deviceService.updateDevice(id, deviceDto);
        return updatedDevice != null ? ResponseEntity.ok(updatedDevice) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        boolean isDeleted = deviceService.deleteDevice(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}