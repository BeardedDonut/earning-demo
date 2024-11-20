package com.example.earnings_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EarningDto {
  private String symbol;
  private String date;
  private String revenueSurprise;
  private String revenueGrowth;
  private String forecast;
  private String consensus;
}
