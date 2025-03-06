package com.example.currency.exchange.and.discount.calculation.dto;

import java.time.LocalDate;

import com.example.currency.exchange.and.discount.calculation.config.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private int userId;
	private String name;
	private UserType userType;
	private LocalDate registrationDate;

}
