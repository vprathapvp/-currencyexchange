package com.example.currency.exchange.and.discount.calculation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import com.example.currency.exchange.and.discount.calculation.dto.BillRequest;
import com.example.currency.exchange.and.discount.calculation.dto.Product;
import com.example.currency.exchange.and.discount.calculation.dto.User;
import com.example.currency.exchange.and.discount.calculation.service.CurrencyService;
import com.example.currency.exchange.and.discount.calculation.service.DiscountService;
import com.example.currency.exchange.and.discount.calculation.config.UserType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CurrencyService currencyService;

	@MockBean
	private DiscountService discountService;

	private BillRequest billRequest;

	@BeforeEach
	public void setUp() {

		User user = new User();
		user.setUserType(UserType.CUSTOMER);
		user.setRegistrationDate(null);

		Product product1 = new Product(0, "Product 1", 100.0, false);
		Product product2 = new Product(0, "Product 2", 50.0, true);

		billRequest = new BillRequest();
		billRequest.setUser(user);
		billRequest.setProducts(Arrays.asList(product1, product2));
		billRequest.setOriginalCurrency("USD");
		billRequest.setTargetCurrency("EUR");
	}

	@Test
	public void testCalculate_Success() throws Exception {

		when(discountService.calculateDiscount(any(BillRequest.class))).thenReturn(145.0);
		when(currencyService.getExchangeRate("USD", "EUR")).thenReturn(0.85);

		mockMvc.perform(post("/api/calculate").contentType(MediaType.APPLICATION_JSON).content(
				"{\"user\":{\"userType\":\"CUSTOMER\",\"registrationDate\":null},\"products\":[{\"name\":\"Product 1\",\"price\":100.0,\"grocery\":false},{\"name\":\"Product 2\",\"price\":50.0,\"grocery\":true}],\"originalCurrency\":\"USD\",\"targetCurrency\":\"EUR\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.total").value(123.25));
	}
}
