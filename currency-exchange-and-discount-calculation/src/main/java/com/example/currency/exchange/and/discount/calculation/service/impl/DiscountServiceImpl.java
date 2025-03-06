package com.example.currency.exchange.and.discount.calculation.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.currency.exchange.and.discount.calculation.config.DiscountConstant;
import com.example.currency.exchange.and.discount.calculation.config.UserType;
import com.example.currency.exchange.and.discount.calculation.dto.BillRequest;
import com.example.currency.exchange.and.discount.calculation.dto.Product;
import com.example.currency.exchange.and.discount.calculation.dto.User;
import com.example.currency.exchange.and.discount.calculation.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Override
	public double calculateDiscount(BillRequest billRequest) {
		Map<Boolean, Double> totals = billRequest.getProducts().stream()
				.collect(Collectors.partitioningBy(Product::isGrocery, Collectors.summingDouble(Product::getPrice)));
		double groceryTotal = totals.getOrDefault(true, DiscountConstant.VALUE_ZERO);
		double nonGroceryTotal = totals.getOrDefault(false, DiscountConstant.VALUE_ZERO);
		double discountedNonGroceryTotal = applyPercentageDiscount(billRequest.getUser(), nonGroceryTotal);
		double totalBeforeFlatDiscount = groceryTotal + discountedNonGroceryTotal;
		double finalTotal = applyFlatDiscount(totalBeforeFlatDiscount);
		return finalTotal; 
	}

	public double applyFlatDiscount(double total) {
		int discountUnits = (int) (total / DiscountConstant.VALUE_HUNDRED);
		return total - (discountUnits * DiscountConstant.FLAT_DISCOUNT_RATE);
	}

	private double applyPercentageDiscount(User user, double total) {
	    if (user.getUserType() == UserType.EMPLOYEE) {
	        return total * (DiscountConstant.VALUE_ONE - DiscountConstant.EMPLOYEE_DISCOUNT_RATE);
	    } else if (user.getUserType() == UserType.AFFILIATE) {
	        return total * (DiscountConstant.VALUE_ONE - DiscountConstant.AFFILIATE_DISCOUNT_RATE);
	    } else if (customerTenure(user)) {
	        return total * (DiscountConstant.VALUE_ONE - DiscountConstant.LONG_TERM_CUSTOMER_DISCOUNT_RATE);
	    }
	    return total; 
	}

	private boolean customerTenure(User user) {
		// based on unique id get user getRegistrationDate details from database
		if (user.getRegistrationDate() == null) {
			return false;
		}
		return ChronoUnit.YEARS.between(user.getRegistrationDate(), LocalDate.now()) > DiscountConstant.LONG_TERM_CUSTOMER_TENURE;
	}

}
