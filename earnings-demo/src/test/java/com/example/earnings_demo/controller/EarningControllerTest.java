package com.example.earnings_demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.earnings_demo.dto.EarningDto;
import com.example.earnings_demo.mapper.EarningDataModelToEarningDtoMapper;
import com.example.earnings_demo.record.EarningDataRecord;
import com.example.earnings_demo.service.EarningsDataService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.List;

@WebMvcTest(value = EarningsController.class)
public class EarningControllerTest {

  @MockBean
  EarningsDataService earningDataService;

  @MockBean
  EarningDataModelToEarningDtoMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  private final static String GET_EARNING_ENDPOINT = "/earnings/earning-reports";

  @Test
  void when_symbol_and_date_provided() throws Exception {
    // Arrange
    var rec = new EarningDataRecord("AMZN", "1", "2", "3", "4", "5");
    var mappedDto = EarningDto.builder().symbol("AMZN").build(); // TODO: better add those values, but that's ok for now
    when(earningDataService.getEarningsData("some-random-symbol", "some-random-date")).thenReturn(rec);
    when(mapper.mapEarningDataModelToEarningDto(rec)).thenReturn(mappedDto);

    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.get(GET_EARNING_ENDPOINT)
        .queryParam("date", "some-random-date")
        .queryParam("symbol", "some-random-symbol")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].symbol").value("AMZN"));
  }

  void when_only_date() throws Exception {
    // Arrange
    var rec = new EarningDataRecord("AMZN", "1", "2", "3", "4", "5");
    var rec2 = new EarningDataRecord("MSFT", "11", "22", "33", "44", "55");
    var mappedDto = EarningDto.builder().symbol("AMZN").build(); // TODO: better add those values, but that's ok for now
    var mappedDto2 = EarningDto.builder().symbol("MSFT").build();
    when(earningDataService.getReportedCompanies("some-random-date")).thenReturn(List.of(rec, rec2));
    when(mapper.mapEarningDataModelToEarningDto(rec)).thenReturn(mappedDto);
    when(mapper.mapEarningDataModelToEarningDto(rec2)).thenReturn(mappedDto2);

    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.get(GET_EARNING_ENDPOINT)
        .queryParam("date", "some-date")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].symbol").value("AMZN"))
        .andExpect(jsonPath("$[1].symbol").value("MSFT"));
  }

  @Test
  void when_not_found() throws Exception {
    // Arrange
    when(earningDataService.getReportedCompanies("some-random-date")).thenReturn(List.of());
    when(mapper.mapEarningDataModelToEarningDto(null)).thenReturn(null);

    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.get(GET_EARNING_ENDPOINT)
        .queryParam("date", "some-random-date")
        .queryParam("symbol", "some-symbol")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void when_only_symbol() throws Exception {
    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.get(GET_EARNING_ENDPOINT)
        .queryParam("symbol", "some-symbol")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void when_no_symbol_no_date() throws Exception {
    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.get(GET_EARNING_ENDPOINT)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
