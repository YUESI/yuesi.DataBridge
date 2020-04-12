package org.yuesi.databridge.scheduler;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yuesi.databridge.biz.StockBasicsBiz;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StockBasicsTask {

	@Autowired
	private StockBasicsBiz stockBasicsBiz;

	@Scheduled(cron = "35 4 3 * * 6 ")
	public void updateStockBasicsData() throws ParseException {
		log.info("Begin update StockBasics data task");
		stockBasicsBiz.updateStockBasicsData();
		log.info("End update StockBasics data task");
	}

}
