import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.devices.controller.DeviceController;
import com.example.devices.dto.DeviceCreateDto;
import com.example.devices.dto.DeviceResponseDto;
import com.example.devices.dto.DeviceUpdateDto;
import com.example.devices.model.Device;
import com.example.devices.model.DeviceState;
import com.example.devices.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateDevice() throws Exception {
        DeviceResponseDto device = new DeviceResponseDto(1L, "Device1", "BrandA", DeviceState.AVAILABLE, LocalDateTime.now());
        when(deviceService.createDevice(any(DeviceCreateDto.class))).thenReturn(device);

        mockMvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Device1"));
    }

    @Test
    public void testGetDevice() throws Exception {
        DeviceResponseDto device = new DeviceResponseDto(1L, "Device1", "BrandA", DeviceState.AVAILABLE, LocalDateTime.now());
        when(deviceService.fetchDevice(1L)).thenReturn(device);

        mockMvc.perform(get("/api/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Device1"));
    }

    @Test
    public void testGetAllDevices() throws Exception {
        DeviceResponseDto device1 = new DeviceResponseDto(1L, "Device1", "BrandA", DeviceState.AVAILABLE, LocalDateTime.now());
        DeviceResponseDto device2 = new DeviceResponseDto(2L, "Device2", "BrandB", DeviceState.IN_USE,  LocalDateTime.now());
        List<DeviceResponseDto> devices = Arrays.asList(device1, device2);
        when(deviceService.fetchAllDevices()).thenReturn(devices);

        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdateDevice() throws Exception {
        DeviceResponseDto device = new DeviceResponseDto(1L, "Device1", "BrandA", DeviceState.AVAILABLE, LocalDateTime.now());
        when(deviceService.updateDevice(eq(1L), any(DeviceUpdateDto.class))).thenReturn(device);

        mockMvc.perform(put("/api/devices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Device1"));
    }

    @Test
    public void testDeleteDevice() throws Exception {
        when(deviceService.deleteDevice(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/devices/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetDevicesByBrand() throws Exception {
        DeviceResponseDto device1 = new DeviceResponseDto(1L, "Device1", "BrandA", DeviceState.AVAILABLE, LocalDateTime.now());
        DeviceResponseDto device2 = new DeviceResponseDto(2L, "Device2", "BrandA", DeviceState.IN_USE, LocalDateTime.now());
        List<DeviceResponseDto> devices = Arrays.asList(device1, device2);
        when(deviceService.fetchDevicesByBrand("BrandA")).thenReturn(devices);

        mockMvc.perform(get("/api/devices/brand/BrandA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetDevicesByState() throws Exception {
        DeviceResponseDto device1 = new DeviceResponseDto(1L, "Device1", "BrandA", DeviceState.AVAILABLE,  LocalDateTime.now());
        List<DeviceResponseDto> devices = Arrays.asList(device1);
        when(deviceService.fetchDevicesByState(DeviceState.AVAILABLE)).thenReturn(devices);

        mockMvc.perform(get("/api/devices/state/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}