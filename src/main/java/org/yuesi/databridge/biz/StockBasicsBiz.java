package org.yuesi.databridge.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yuesi.databridge.entity.StockBasics;
import org.yuesi.databridge.service.IStockBasicsService;
import org.yuesi.databridge.service.ITradeDateService;

import lombok.extern.slf4j.Slf4j;
import waditu.tushare.entity.StockBasicsData;
import waditu.tushare.stock.Fundamental;

@Component
@Slf4j
public class StockBasicsBiz {

	@Autowired
	private IStockBasicsService stockBasicsService;
	
	@Autowired
	private ITradeDateService tradeDateService;

	public void updateStockBasicsData() throws ParseException {
		if (stockBasicsService.count() == 0) {
			initStockBasics();
		} else {
			updateStockBasics();
		}
	}

	private void updateStockBasics() {
		// TODO Auto-generated method stub

	}

	private void initStockBasics() throws ParseException {
		log.info("Start init StockBasics.");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date lastTradeDate = tradeDateService.getLastTradeDate(new Date());
		List<StockBasicsData> listData = Fundamental.getStockBasics(format.format(lastTradeDate));
		log.info("Get StockBasics " + listData.size() + " records from tushare.");
		saveListOfStockBasics(listData);
		log.info("End init StockBasics " + listData.size() + " records.");

	}

	private void saveListOfStockBasics(List<StockBasicsData> list) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		List<StockBasics> listInput = new ArrayList<StockBasics>();
		for (StockBasicsData data : list) {
			StockBasics stockBasics = new StockBasics();
			stockBasics.setCode(data.code);
			stockBasics.setName(data.name);
			stockBasics.setIndustry(data.industry);
			stockBasics.setArea(data.area);
			if (data.timeToMarket.equalsIgnoreCase("0")) {

			} else {
				stockBasics.setTimeToMarket(format.parse(data.timeToMarket));
			}
			listInput.add(stockBasics);
		}
		stockBasicsService.saveAll(listInput);
	}

}
