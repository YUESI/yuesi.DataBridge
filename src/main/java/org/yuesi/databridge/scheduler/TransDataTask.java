package org.yuesi.databridge.scheduler;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yuesi.databridge.biz.TransDataBiz;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransDataTask {

	@Autowired
	private TransDataBiz transBiz;
	
	@Scheduled(cron = "35 4 16 * * ? ")
	public void saveDailyTrans() throws ParseException {
		log.info("Begin 更新每日行情 task");
		transBiz.addDailyTrans();
		log.info("End 更新每日行情 task");
	}
	
	@Scheduled(cron = "35 34 3 * * 6 ")
	public void checkWeekly() throws ParseException {
		log.info("Begin 更新当前周每日行情 task");
		transBiz.checkWeekly();
		log.info("End 更新当前周每日行情 task");
	}
}
