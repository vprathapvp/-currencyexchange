package com.example.currency.exchange.and.discount.calculation.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.currency.exchange.and.discount.calculation.dto.ExchangeRateResponse;
import com.example.currency.exchange.and.discount.calculation.service.CurrencyService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService{

	private final String API_KEY = "3751c42cede1845b9a2d96df"; 
	private final String BASE_URL = "https://open.er-api.com/v6/latest/";
	private final RestTemplate restTemplate;

	@Override 
	public double getExchangeRate(String baseCurrency, String targetCurrency) {
		String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + baseCurrency).queryParam("apikey", API_KEY)
				.toUriString();
		ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
		if (response != null && response.getRates() != null && response.getRates().containsKey(targetCurrency)) {
			return response.getRates().get(targetCurrency);
		} else {
			throw new RuntimeException("Failed to fetch exchange rate for " + targetCurrency);
		}  
	} 
} 
