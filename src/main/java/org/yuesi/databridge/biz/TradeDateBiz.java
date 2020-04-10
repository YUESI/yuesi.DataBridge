package org.yuesi.databridge.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yuesi.databridge.entity.TradeDate;
import org.yuesi.databridge.service.ITradeDateService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import waditu.tushare.entity.TradeDateData;
import waditu.tushare.util.Dateu;

@Component
@Slf4j
public class TradeDateBiz {

	@Autowired
	private ITradeDateService tradeDateService;

	public void updateTradeDateData() {
		long total = tradeDateService.count();

		if (total == 0) {
			log.info("trade date 数据为0，开始初始化数据。");
			initTradeDate();
			log.info("trade date 数据初始化完成");
		} else {
			log.info(String.format("trade date 数据为%s，开始更新数据。", total));
			addMoreTradeDate();
			log.info("trade date 数据更新完成");
		}
	}

	private void initTradeDate() {
		log.info("Start init tradedate.");
		List<TradeDateData> listData = Dateu.tradeCal();
		log.info("Get tradedatedata " + listData.size() + " records from tushare.");
		saveListOfTradeDateData(listData);
		log.info("End init tradedate " + listData.size() + " records.");
	}

	private void addMoreTradeDate() {
		List<TradeDateData> listData = Dateu.tradeCal();
		@NonNull
		TradeDateData maxDateFromTushare = listData.stream().max(Comparator.comparing(t -> t.date)).get();
		@NonNull
		Date maxTradeDateFromDB = tradeDateService.getMaxTradeDate();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String strMaxDate = format.format(maxTradeDateFromDB);
		int count = maxTradeDateFromDB.compareTo(maxDateFromTushare.date);
		if (count == 0) {
			log.info("Current date " + strMaxDate + " need not update.");
		} else if (count > 0) {
			log.info("Maybe sth wrong in tushare.");
		} else {
			log.info("Start update tradedate.");
			List<TradeDateData> listNeedAdd = listData.stream().filter(t -> t.date.compareTo(maxTradeDateFromDB) > 0)
					.collect(Collectors.toList());
			saveListOfTradeDateData(listNeedAdd);
			log.info("End update tradedate " + listNeedAdd.size() + " records.");
		}
	}

	private void saveListOfTradeDateData(@NonNull List<TradeDateData> list) {
		List<TradeDate> listInput = new ArrayList<TradeDate>();
		for (TradeDateData data : list) {
			TradeDate tradeDate = new TradeDate();
			tradeDate.setTradeDate(data.date);
			tradeDate.setIsOpen(data.isOpen);
			listInput.add(tradeDate);
		}
		tradeDateService.saveAll(listInput);
	}

	public void addTradeDateFromHoliday() {
		List<TradeDate> dayarr = new ArrayList<TradeDate>();
		String begin = "2020-01-01";
		String end = "2021-01-01";
		List<TradeDate> dateInDb = new ArrayList<TradeDate>();
		try {
			dateInDb = tradeDateService.queryByTradeDate(begin, end);
			Map<Date, Boolean> dateInDbMap = dateInDb.stream()
					.collect(Collectors.toMap(TradeDate::getTradeDate, TradeDate::getIsOpen, (k1, k2) -> k1));

			Date endDate = ITradeDateService.sdf.parse(end);
			Date tempDate = ITradeDateService.sdf.parse(begin);
			int onedaylength = 86400000;
			while (tempDate.before(endDate)) {
				if (dateInDbMap.get(tempDate) != null) {
					log.info(String.format("%s该日期已经在数据库中", tempDate));
				} else {
					TradeDate tradeDate = new TradeDate();
					tradeDate.setTradeDate(tempDate);
					tradeDate.setIsOpen(!tradeDateService.isWeekend(tempDate));
					dayarr.add(tradeDate);
				}
				long timestamp = tempDate.getTime();
				timestamp += onedaylength;
				tempDate = new Date(timestamp);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		tradeDateService.saveAll(dayarr);
	}

}
