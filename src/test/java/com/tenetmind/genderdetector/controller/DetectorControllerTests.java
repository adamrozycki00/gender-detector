package com.tenetmind.genderdetector.controller;

import com.google.gson.Gson;
import com.tenetmind.genderdetector.service.DetectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DetectorController.class)
class DetectorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetectorService detectorService;

    @Test
    public void shouldCallServiceMethodDetectGenderWithProperArguments() throws Exception {
        //given
        RequestDto requestDto = new RequestDto("Jan Maria Rokita", "first");
        when(detectorService.detectGender(requestDto.getSourceStringToCheck(), requestDto.getDetectorVariantName()))
                .thenReturn("MALE");
        String jsonContent = new Gson().toJson(requestDto);

        //when & then
        mockMvc.perform(post("/detect")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("MALE"));
    }

    @Test
    public void shouldCallServiceMethodGetTokensWithProperArguments() throws Exception {
        //given
        String gender = "female";
        long page = 2L;
        long size = 3L;
        when(detectorService.getTokens(gender, page, size))
                .thenReturn(List.of("Anna", "Monika", "Natalia"));
        String url = "/tokens?gender=" + gender + "&page=" + page + "&size=" + size;

        //when & then
        mockMvc.perform(get(url)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

}