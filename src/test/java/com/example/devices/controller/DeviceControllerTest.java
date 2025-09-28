import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.devices.controller.DeviceController;
import com.example.devices.dto.DeviceDto;
import com.example.devices.model.Device;
import com.example.devices.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateDevice() throws Exception {
        Device device = new Device(1L, "Device1", "BrandA", "available");
        when(deviceService.createDevice(any(DeviceDto.class))).thenReturn(device);

        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Device1"));
    }

    @Test
    public void testGetDevice() throws Exception {
        Device device = new Device(1L, "Device1", "BrandA", "available");
        when(deviceService.fetchDevice(1L)).thenReturn(device);

        mockMvc.perform(get("/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Device1"));
    }

    @Test
    public void testGetAllDevices() throws Exception {
        Device device1 = new Device(1L, "Device1", "BrandA", "available");
        Device device2 = new Device(2L, "Device2", "BrandB", "in-use");
        List<Device> devices = Arrays.asList(device1, device2);
        when(deviceService.fetchAllDevices()).thenReturn(devices);

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdateDevice() throws Exception {
        Device device = new Device(1L, "Device1", "BrandA", "available");
        when(deviceService.updateDevice(eq(1L), any(DeviceDto.class))).thenReturn(device);

        mockMvc.perform(put("/devices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Device1"));
    }

    @Test
    public void testDeleteDevice() throws Exception {
        doNothing().when(deviceService).deleteDevice(1L);

        mockMvc.perform(delete("/devices/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetDevicesByBrand() throws Exception {
        Device device1 = new Device(1L, "Device1", "BrandA", "available");
        Device device2 = new Device(2L, "Device2", "BrandA", "in-use");
        List<Device> devices = Arrays.asList(device1, device2);
        when(deviceService.fetchDevicesByBrand("BrandA")).thenReturn(devices);

        mockMvc.perform(get("/devices/brand/BrandA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetDevicesByState() throws Exception {
        Device device1 = new Device(1L, "Device1", "BrandA", "available");
        List<Device> devices = Arrays.asList(device1);
        when(deviceService.fetchDevicesByState("available")).thenReturn(devices);

        mockMvc.perform(get("/devices/state/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}