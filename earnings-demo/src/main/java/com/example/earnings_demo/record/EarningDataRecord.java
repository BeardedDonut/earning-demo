package com.example.earnings_demo.record;

public record EarningDataRecord(
                String symbol,
                String date,
                String revenueSurprise,
                String revenueGrowth,
                String forecast,
                String consensus) {
};
