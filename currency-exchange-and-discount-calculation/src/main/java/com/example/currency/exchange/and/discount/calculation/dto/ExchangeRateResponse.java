package com.example.currency.exchange.and.discount.calculation.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

	private String base;
	private Map<String, Double> rates;
}
