package com.example.devices.service;

import com.example.devices.dto.DeviceCreateDto;
import com.example.devices.dto.DeviceResponseDto;
import com.example.devices.dto.DeviceUpdateDto;
import com.example.devices.model.Device;
import com.example.devices.model.DeviceState;
import com.example.devices.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceResponseDto createDevice(DeviceCreateDto deviceDto) {
        Device device = new Device();
        device.setName(deviceDto.getName());
        device.setBrand(deviceDto.getBrand());
        device.setState(deviceDto.getState());
        Device response = deviceRepository.save(device);
        DeviceResponseDto  deviceResponseDto = new DeviceResponseDto();
        deviceResponseDto.setId(response.getId());
        deviceResponseDto.setName(response.getName());
        deviceResponseDto.setBrand(response.getBrand());
        deviceResponseDto.setState(response.getState());
        deviceResponseDto.setCreationTime(response.getCreationTime());
        return deviceResponseDto;
    }

    public DeviceResponseDto updateDevice(Long id, DeviceUpdateDto deviceDto) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            Optional.ofNullable(deviceDto.getState()).ifPresent(device::setState);
            if (!device.getState().equals(DeviceState.IN_USE)) {
                Optional.ofNullable(deviceDto.getBrand()).ifPresent(device::setBrand);
                Optional.ofNullable(deviceDto.getName()).ifPresent(device::setName);
            }
            Device deviceToReturn = deviceRepository.save(device);
            DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
            deviceResponseDto.setId(deviceToReturn.getId());
            deviceResponseDto.setName(deviceToReturn.getName());
            deviceResponseDto.setBrand(deviceToReturn.getBrand());
            deviceResponseDto.setState(deviceToReturn.getState());
            deviceResponseDto.setCreationTime(deviceToReturn.getCreationTime());
            return deviceResponseDto;
        }
        return null;
    }

    public DeviceResponseDto fetchDevice(Long id) {
        Device response = deviceRepository.findById(id).orElse(null);
        if(response == null)
            return null;
        DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
        deviceResponseDto.setId(response.getId());
        deviceResponseDto.setName(response.getName());
        deviceResponseDto.setBrand(response.getBrand());
        deviceResponseDto.setState(response.getState());
        deviceResponseDto.setCreationTime(response.getCreationTime());
        return deviceResponseDto;
    }

    public List<DeviceResponseDto> fetchAllDevices() {
        List<Device> list = deviceRepository.findAll();
        List<DeviceResponseDto> deviceResponseDtoList = new ArrayList<>();
        for (Device device : list) {
            DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
            deviceResponseDto.setId(device.getId());
            deviceResponseDto.setName(device.getName());
            deviceResponseDto.setBrand(device.getBrand());
            deviceResponseDto.setState(device.getState());
            deviceResponseDto.setCreationTime(device.getCreationTime());
            deviceResponseDtoList.add(deviceResponseDto);
        }
        return deviceResponseDtoList;
    }

    public List<DeviceResponseDto> fetchDevicesByBrand(String brand) {
        List<Device> list = deviceRepository.findByBrand(brand);
        List<DeviceResponseDto> deviceResponseDtoList = new ArrayList<>();
        for (Device device : list) {
            DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
            deviceResponseDto.setId(device.getId());
            deviceResponseDto.setName(device.getName());
            deviceResponseDto.setBrand(device.getBrand());
            deviceResponseDto.setState(device.getState());
            deviceResponseDto.setCreationTime(device.getCreationTime());
            deviceResponseDtoList.add(deviceResponseDto);
        }
        return deviceResponseDtoList;
    }

    public List<DeviceResponseDto> fetchDevicesByState(DeviceState state) {
        List<Device> list = deviceRepository.findByState(state);
        List<DeviceResponseDto> deviceResponseDtoList = new ArrayList<>();
        for (Device device : list) {
            DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
            deviceResponseDto.setId(device.getId());
            deviceResponseDto.setName(device.getName());
            deviceResponseDto.setBrand(device.getBrand());
            deviceResponseDto.setState(device.getState());
            deviceResponseDto.setCreationTime(device.getCreationTime());
            deviceResponseDtoList.add(deviceResponseDto);
        }
        return deviceResponseDtoList;
    }

    public boolean deleteDevice(Long id) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent() && !optionalDevice.get().getState().equals(DeviceState.IN_USE)) {
            deviceRepository.delete(optionalDevice.get());
            return true;
        }
        return false;
    }
}