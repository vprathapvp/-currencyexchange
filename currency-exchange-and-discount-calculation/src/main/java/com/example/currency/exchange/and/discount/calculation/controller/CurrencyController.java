package com.example.currency.exchange.and.discount.calculation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.currency.exchange.and.discount.calculation.dto.BillRequest;
import com.example.currency.exchange.and.discount.calculation.service.CurrencyService;
import com.example.currency.exchange.and.discount.calculation.service.DiscountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "CurrencyController API", description = "API for handling bill calculations and discounts")
public class CurrencyController {

	private final CurrencyService currencyService;
	private final DiscountService discountService;

	@PostMapping("/calculate")
	@Operation(summary = "Calculate the final bill after discount and currency conversion", description = "Calculates the total payable amount after applying discounts and converting to the target currency.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully calculated the total"),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Map<String, Double>> calculate(@RequestBody BillRequest billRequest) {
		double discountTotal = discountService.calculateDiscount(billRequest);
		double exchangeRate = currencyService.getExchangeRate(billRequest.getOriginalCurrency(),
				billRequest.getTargetCurrency());
		double total = discountTotal * exchangeRate;
		Map<String, Double> response = new HashMap<>();
		response.put("total", total);
		return ResponseEntity.ok(response);
	}

}
