package org.yuesi.databridge.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yuesi.databridge.entity.StockBasics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class StockBasicsServiceTest {

	@Autowired
	private IStockBasicsService stockBasicsService;
	
	private static StockBasics testStock = new StockBasics();

	@BeforeAll
	public static void init() {
		log.info("Test begin.");
		testStock.setCode("123");
		testStock.setName("345");
		testStock.setIndustry("1111");
		testStock.setArea("888");
	}

	@AfterAll
	public static void done() {
		log.info("Test end.");
	}
	

	@BeforeEach
	public void setUp() {
//		stockBasicsService.save(testStock);
	}

	@AfterEach
	public void tearDown() {
		stockBasicsService.del(testStock.getCode());
	}

	@Test
//	@Disabled
	void test() {
		stockBasicsService.save(testStock);
	}

}
