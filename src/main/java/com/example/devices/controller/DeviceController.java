package com.example.devices.controller;

import com.example.devices.dto.DeviceCreateDto;
import com.example.devices.dto.DeviceResponseDto;
import com.example.devices.dto.DeviceUpdateDto;
import com.example.devices.model.DeviceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.devices.model.Device;
import com.example.devices.service.DeviceService;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceResponseDto> createDevice(@RequestBody DeviceCreateDto deviceDto) {
        DeviceResponseDto createdDevice = deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDto> getDevice(@PathVariable("id") Long id) {
        DeviceResponseDto device = deviceService.fetchDevice(id);
        return device != null ? ResponseEntity.ok(device) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponseDto>> getAllDevices() {
        List<DeviceResponseDto> devices = deviceService.fetchAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<DeviceResponseDto>> getDevicesByBrand(@PathVariable("brand") String brand) {
        List<DeviceResponseDto> devices = deviceService.fetchDevicesByBrand(brand);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<DeviceResponseDto>> getDevicesByState(@PathVariable("state") String state) {
        List<DeviceResponseDto> devices = deviceService.fetchDevicesByState(DeviceState.fromValue(state));
        return ResponseEntity.ok(devices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDto> updateDevice(@PathVariable("id") Long id, @RequestBody DeviceUpdateDto deviceDto) {
        DeviceResponseDto updatedDevice = deviceService.updateDevice(id, deviceDto);
        return updatedDevice != null ? ResponseEntity.ok(updatedDevice) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable("id") Long id) {
        boolean isDeleted = deviceService.deleteDevice(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}