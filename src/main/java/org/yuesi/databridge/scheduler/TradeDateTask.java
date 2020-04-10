package org.yuesi.databridge.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yuesi.databridge.biz.TradeDateBiz;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TradeDateTask {

	@Autowired
	private TradeDateBiz tradeDateBiz;

	@Scheduled(cron = "35 4 5 22 11 ? ")
	public void updateTradeDateData() {
		log.info("Begin update tradedate data task");
		//tradeDateBiz.updateTradeDateData();
		tradeDateBiz.addTradeDateFromHoliday();
		log.info("End update tradedate data task");
	}

}
