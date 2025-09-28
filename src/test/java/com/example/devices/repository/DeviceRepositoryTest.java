import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.devices.model.Device;
import com.example.devices.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class DeviceRepositoryTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private Device device;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        device = new Device();
        device.setId(1L);
        device.setName("Device1");
        device.setBrand("BrandA");
        device.setState("available");
    }

    @Test
    public void testSaveDevice() {
        when(deviceRepository.save(device)).thenReturn(device);
        Device savedDevice = deviceRepository.save(device);
        assertEquals(device.getId(), savedDevice.getId());
        assertEquals(device.getName(), savedDevice.getName());
    }

    @Test
    public void testFindDeviceById() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        Optional<Device> foundDevice = deviceRepository.findById(1L);
        assertTrue(foundDevice.isPresent());
        assertEquals(device.getId(), foundDevice.get().getId());
    }

    @Test
    public void testDeleteDevice() {
        doNothing().when(deviceRepository).deleteById(1L);
        deviceRepository.deleteById(1L);
        verify(deviceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllDevices() {
        // This test would typically involve a mock for findAll() method
        // and assertions to check the returned list of devices.
    }
}