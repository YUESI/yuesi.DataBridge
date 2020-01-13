package org.yuesi.databridge.service;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yuesi.databridge.biz.StockBasicsBiz;
import org.yuesi.databridge.entity.TradeDate;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TradeDateServiceTest {

	@Autowired
	private ITradeDateService tradeDateService;

	private static TradeDate testDate = new TradeDate();
	
	private static SimpleDateFormat format;

	@BeforeAll
	public static void init() {
		format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			testDate.setTradeDate(format.parse("1990-1-1"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		testDate.setIsOpen(false);
	}

	@AfterAll
	public static void done() {
		System.out.println("Test end.");
	}

	@BeforeEach
	public void setUp() {
		tradeDateService.save(testDate);
	}

	@AfterEach
	public void tearDown() {
		tradeDateService.del(testDate.getTradeDate());
	}

	@Test
	void testCount() {
		assertTrue(tradeDateService.count() > 0);
	}
	
	@Test
	void testQueryByTradeDate() {
		try {
			List<TradeDate> result = tradeDateService.queryByTradeDate("1990-1-1");
			assertTrue(result.size() > 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetMaxTradeDate() {
		Date maxDate = tradeDateService.getMaxTradeDate();
		assertTrue(maxDate.compareTo(testDate.getTradeDate()) >= 0);
		System.out.println(format.format(maxDate));
	}

	@Test
	void testGetLastTradeDate() {
		Date lastDate;
		try {
			lastDate = tradeDateService.getLastTradeDate(format.parse("2019-12-28"));
			log.info(format.format(lastDate));
			assertTrue(lastDate.compareTo(testDate.getTradeDate()) >= 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
