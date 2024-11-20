package com.example.earnings_demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.earnings_demo.dto.EarningDto;
import com.example.earnings_demo.mapper.EarningDataModelToEarningDtoMapper;
import com.example.earnings_demo.service.EarningsDataService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/earnings")
public class EarningsController {
  private final EarningsDataService earningsDataService;
  private final EarningDataModelToEarningDtoMapper mapper;

  // GET service.com/api/earnings/earning-report?symbol={symbol}&date={date}
  @GetMapping("/earning-reports")
  public ResponseEntity<List<EarningDto>> getEarnings(
      @RequestParam(required = false, name = "symbol") String symbol,
      @RequestParam(required = false, name = "date") String date) {
    List<EarningDto> responseList = null;

    // if date is not provided then response with bad request
    if (Objects.isNull(date))
      return ResponseEntity.badRequest().build();

    // if only symbol is null then check for date
    if (Objects.isNull(symbol)) {

      responseList = earningsDataService
          .getReportedCompanies(date)
          .stream()
          .map(mapper::mapEarningDataModelToEarningDto)
          .collect(Collectors.toList());

    } else { // if both are given then filter for both
      var earningData = earningsDataService.getEarningsData(symbol, date);
      if (!Objects.isNull(earningData)) {
        var mapped = mapper.mapEarningDataModelToEarningDto(earningData);
        responseList = List.of(mapped);
      }
    }

    if (!Objects.isNull(responseList) && !responseList.isEmpty())
      return ResponseEntity.ok(responseList);
    else // if the response list is empty or null then send back with not found
      return ResponseEntity.notFound().build();
  }
}
