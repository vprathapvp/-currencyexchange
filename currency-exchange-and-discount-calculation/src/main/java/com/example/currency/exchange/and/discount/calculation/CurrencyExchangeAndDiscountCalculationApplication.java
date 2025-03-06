package com.example.currency.exchange.and.discount.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CurrencyExchangeAndDiscountCalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeAndDiscountCalculationApplication.class, args);
	}

}
