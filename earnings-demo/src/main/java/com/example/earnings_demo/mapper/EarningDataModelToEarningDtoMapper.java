package com.example.earnings_demo.mapper;

import org.mapstruct.Mapper;

import com.example.earnings_demo.dto.EarningDto;
import com.example.earnings_demo.record.EarningDataRecord;

@Mapper(componentModel = "spring")
public interface EarningDataModelToEarningDtoMapper {
  EarningDto mapEarningDataModelToEarningDto(EarningDataRecord earningDataRecord);
}
