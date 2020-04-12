package org.yuesi.databridge.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yuesi.databridge.entity.TransData;

@SpringBootTest
class TransDataServiceTest {

	@Autowired
	private ITransDataService transService;

	@Test
	void testQueryByCodeAndTradeDateBetween() {
		try {
			List<TransData> result = transService.queryByCodeAndTradeDateBetween("000001", "2020-04-05", "2020-04-12");
			assertTrue(result.size() > 3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
