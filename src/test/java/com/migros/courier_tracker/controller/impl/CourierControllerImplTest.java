package com.migros.courier_tracker.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.courier_tracker.controller.request.CourierLocationRequest;
import com.migros.courier_tracker.mapper.CourierMapper;
import com.migros.courier_tracker.service.CourierService;
import com.migros.courier_tracker.service.dto.LocationDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierControllerImpl.class)
class CourierControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourierService courierService;

    @MockitoBean
    private CourierMapper courierMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateCourierLocation_success() throws Exception {

        Mockito.doNothing().when(courierService).updateCourierLocation(eq(1L), any());
        when(courierMapper.toDTO(any())).thenReturn(new LocationDTO());
        CourierLocationRequest request = new CourierLocationRequest();
        request.setLatitude(40.99233);
        request.setLongitude(29.12442);

        mockMvc.perform(post("/api/v1/courier/1/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());


        Mockito.verify(courierService, Mockito.times(1)).updateCourierLocation(eq(1L), any());
    }

    @Test
    void updateCourierLocation_throwsIOException() throws Exception {


        Mockito.doThrow(new IOException("Test Exception")).when(courierService).updateCourierLocation(eq(1L), any());
        when(courierMapper.toDTO(any())).thenReturn(new LocationDTO());
        CourierLocationRequest request = new CourierLocationRequest();
        request.setLatitude(40.99233);
        request.setLongitude(29.12442);

        mockMvc.perform(post("/api/v1/courier/1/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getTotalDistance_success() throws Exception {
        when(courierService.getTotalDistance(1L)).thenReturn(12.34);

        mockMvc.perform(get("/api/v1/courier/1/total-distance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("12.34"));

        Mockito.verify(courierService, Mockito.times(1)).getTotalDistance(1L);
    }
}
