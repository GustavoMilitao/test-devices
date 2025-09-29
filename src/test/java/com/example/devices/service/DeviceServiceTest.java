import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.devices.dto.DeviceCreateDto;
import com.example.devices.dto.DeviceResponseDto;
import com.example.devices.dto.DeviceUpdateDto;
import com.example.devices.model.Device;
import com.example.devices.model.DeviceState;
import com.example.devices.repository.DeviceRepository;
import com.example.devices.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private Device device;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        device = new Device();
        device.setId(1L);
        device.setName("Device1");
        device.setBrand("BrandA");
        device.setState(DeviceState.AVAILABLE);
    }

    @Test
    void testCreateDevice() {
        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        DeviceCreateDto deviceDto = new DeviceCreateDto();
        deviceDto.setName("Device1");
        deviceDto.setBrand("BrandA");
        deviceDto.setState(DeviceState.AVAILABLE);
        DeviceResponseDto createdDevice = deviceService.createDevice(deviceDto);
        assertNotNull(createdDevice);
        assertEquals(device.getName(), createdDevice.getName());
    }

    @Test
    void testFetchDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        DeviceResponseDto fetchedDevice = deviceService.fetchDevice(1L);
        assertNotNull(fetchedDevice);
        assertEquals(device.getId(), fetchedDevice.getId());
    }

    @Test
    void testUpdateDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        device.setName("UpdatedDevice");
        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        DeviceUpdateDto deviceDto = new DeviceUpdateDto();
        deviceDto.setName("UpdatedDevice");
        deviceDto.setBrand("BrandA");
        deviceDto.setState(DeviceState.AVAILABLE);
        DeviceResponseDto updatedDevice = deviceService.updateDevice(1L, deviceDto);
        assertEquals("UpdatedDevice", updatedDevice.getName());
    }

    @Test
    void testDeleteDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        deviceService.deleteDevice(1L);
        verify(deviceRepository, times(1)).delete(device);
    }

    @Test
    void testFetchAllDevices() {
        // Implement test for fetching all devices
    }

    @Test
    void testFetchDevicesByBrand() {
        // Implement test for fetching devices by brand
    }

    @Test
    void testFetchDevicesByState() {
        // Implement test for fetching devices by state
    }

    // Additional tests for domain validations can be added here
}