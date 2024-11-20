package com.example.earnings_demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.earnings_demo.record.EarningDataRecord;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EarningsDataService {
                private static final List<EarningDataRecord> EARNINGS = List.of(
                                                new EarningDataRecord("AMZN", "11/15/2024", "2.1%", "2.1%", "2.3%",
                                                                                "22"),
                                                new EarningDataRecord("MSFT", "11/15/2024", "3.1%", "3.1%", "3.3%",
                                                                                "33"));

                public EarningDataRecord getEarningsData(String symbol, String date) {
                                return EARNINGS.stream()
                                                                .filter(earnings -> earnings.symbol().equals(symbol)
                                                                                                && earnings.date().equals(date))
                                                                .findFirst()
                                                                .orElse(null);
                }

                public List<EarningDataRecord> getReportedCompanies(String date) {
                                return EARNINGS.stream()
                                                                .filter(earnings -> earnings.date().equals(date))
                                                                .toList();
                }

}
