package com.example.currency.exchange.and.discount.calculation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.example.currency.exchange.and.discount.calculation.config.DiscountConstant;
import com.example.currency.exchange.and.discount.calculation.config.UserType;
import com.example.currency.exchange.and.discount.calculation.dto.BillRequest;
import com.example.currency.exchange.and.discount.calculation.dto.Product;
import com.example.currency.exchange.and.discount.calculation.dto.User;
import com.example.currency.exchange.and.discount.calculation.service.impl.DiscountServiceImpl;

public class DiscountServiceImplTest {

	@InjectMocks
	private DiscountServiceImpl discountService;

	@BeforeEach
	public void setUp() {
		discountService = new DiscountServiceImpl();
	}

	@Test
	public void testCalculateTotalBill_WithEmployeeDiscount() {

		User user = new User(0, "Alice", UserType.EMPLOYEE, LocalDate.of(2020, 1, 1));
		Product product1 = new Product(0, "Laptop", 1000, false);
		Product product2 = new Product(0, "Groceries", 200, true);
		BillRequest billRequest = new BillRequest(0, Arrays.asList(product1, product2), user, null, null);

		double totalBill = discountService.calculateDiscount(billRequest);

		double expectedTotal = (1000 * (DiscountConstant.VALUE_ONE - DiscountConstant.EMPLOYEE_DISCOUNT_RATE)) + 200;

		expectedTotal = discountService.applyFlatDiscount(expectedTotal);

		assertEquals(expectedTotal, totalBill);
	}

	@Test
	public void testCalculateTotalBill_WithAffiliateDiscount() {

		User user = new User(0, "Bob", UserType.AFFILIATE, LocalDate.of(2021, 1, 1));
		Product product1 = new Product(0, "Smartphone", 800, false);
		Product product2 = new Product(0, "Groceries", 150, true);
		BillRequest billRequest = new BillRequest(0, Arrays.asList(product1, product2), user, null, null);

		double totalBill = discountService.calculateDiscount(billRequest);

		double expectedTotal = (800 * (1 - DiscountConstant.AFFILIATE_DISCOUNT_RATE)) + 150;
		expectedTotal = discountService.applyFlatDiscount(expectedTotal);
		assertEquals(expectedTotal, totalBill);
	}

	@Test
	public void testCalculateTotalBill_WithLongTermCustomerDiscount() {

		User user = new User(0, "Charlie", UserType.CUSTOMER, LocalDate.of(2018, 1, 1));
		Product product1 = new Product(0, "Tablet", 600, false);
		Product product2 = new Product(0, "Groceries", 100, true);
		BillRequest billRequest = new BillRequest(0, Arrays.asList(product1, product2), user, null, null);

		double totalBill = discountService.calculateDiscount(billRequest);

		double expectedTotal = (600 * (1 - DiscountConstant.LONG_TERM_CUSTOMER_DISCOUNT_RATE)) + 100;
		expectedTotal = discountService.applyFlatDiscount(expectedTotal);
		assertEquals(expectedTotal, totalBill);
	}

	@Test
	public void testCalculateTotalBill_NoDiscount() {

		User user = new User(0, "David", UserType.CUSTOMER, LocalDate.of(2022, 1, 1));
		Product product1 = new Product(0, "Monitor", 300, false);
		Product product2 = new Product(0, "Groceries", 50, true);
		BillRequest billRequest = new BillRequest(0, Arrays.asList(product1, product2), user, null, null);

		double totalBill = discountService.calculateDiscount(billRequest);

		double expectedTotal = 300 + 50;

		expectedTotal = discountService.applyFlatDiscount(expectedTotal);
		assertEquals(expectedTotal, totalBill);
	}
}
