package org.yuesi.databridge.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
		if (tradeDateService.count() == 0) {
			initTradeDate();
		} else {
			addMoreTradeDate();
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
		for(TradeDateData data : list) {
			TradeDate tradeDate = new TradeDate();
			tradeDate.setTradeDate(data.date);
			tradeDate.setIsOpen(data.isOpen);
			listInput.add(tradeDate);
		}
		tradeDateService.saveAll(listInput);
	}

}
