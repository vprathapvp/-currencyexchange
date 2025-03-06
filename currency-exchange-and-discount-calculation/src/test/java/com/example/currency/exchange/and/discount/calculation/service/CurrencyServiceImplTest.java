package com.example.currency.exchange.and.discount.calculation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.example.currency.exchange.and.discount.calculation.dto.ExchangeRateResponse;
import com.example.currency.exchange.and.discount.calculation.service.impl.CurrencyServiceImpl;

public class CurrencyServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExchangeRate_Success() {
        // Given
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        ExchangeRateResponse response = new ExchangeRateResponse();
        response.setRates(Map.of("EUR", 0.85)); 

        when(restTemplate.getForObject(anyString(), any())).thenReturn(response);

        // When
        double exchangeRate = currencyService.getExchangeRate(baseCurrency, targetCurrency);

        // Then
        assertEquals(0.85, exchangeRate);
    }

    @Test
    public void testGetExchangeRate_Failure() {
      
        String baseCurrency = "USD";
        String targetCurrency = "JPY"; 
        ExchangeRateResponse response = new ExchangeRateResponse();
        response.setRates(Map.of("EUR", 0.85)); 

        when(restTemplate.getForObject(anyString(), any())).thenReturn(response);

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyService.getExchangeRate(baseCurrency, targetCurrency);
        });

     
        assertEquals("Failed to fetch exchange rate for JPY", exception.getMessage());
    }
}

