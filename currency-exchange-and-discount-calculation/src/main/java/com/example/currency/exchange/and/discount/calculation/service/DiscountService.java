package com.example.currency.exchange.and.discount.calculation.service;
import com.example.currency.exchange.and.discount.calculation.dto.BillRequest;

public interface DiscountService {

	public double calculateDiscount(BillRequest billRequest);
		

}
