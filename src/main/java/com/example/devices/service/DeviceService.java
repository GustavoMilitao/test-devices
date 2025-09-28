package com.example.devices.service;

import com.example.devices.dto.DeviceDto;
import com.example.devices.model.Device;
import com.example.devices.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device createDevice(DeviceDto deviceDto) {
        Device device = new Device();
        device.setName(deviceDto.getName());
        device.setBrand(deviceDto.getBrand());
        device.setState("available");
        return deviceRepository.save(device);
    }

    public Device updateDevice(Long id, DeviceDto deviceDto) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            if (!device.getState().equals("in-use")) {
                device.setBrand(deviceDto.getBrand());
                device.setName(deviceDto.getName());
            }
            return deviceRepository.save(device);
        }
        return null;
    }

    public Device fetchDevice(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public List<Device> fetchAllDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> fetchDevicesByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    public List<Device> fetchDevicesByState(String state) {
        return deviceRepository.findByState(state);
    }

    public boolean deleteDevice(Long id) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent() && !optionalDevice.get().getState().equals("in-use")) {
            deviceRepository.delete(optionalDevice.get());
            return true;
        }
        return false;
    }
}