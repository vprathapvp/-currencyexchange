package com.example.currency.exchange.and.discount.calculation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	private int productId;
	private String name;
	private double price;
	private boolean isGrocery;

}
