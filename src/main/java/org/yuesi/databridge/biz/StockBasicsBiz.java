package org.yuesi.databridge.biz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	private IStockBasicsService stockService;

	@Autowired
	private ITradeDateService tradeDateService;

	public void updateStockBasicsData() {
		List<StockBasics> saveList = new ArrayList<StockBasics>();

		List<StockBasics> stocksInDB = stockService.findAll();
		Map<String, StockBasics> stocksInDbMap = stocksInDB.stream()
				.collect(Collectors.toMap(StockBasics::getCode, Function.identity(), (k1, k2) -> k1));

		Date lastTradeDate = tradeDateService.getLastTradeDate(new Date());
		List<StockBasicsData> stocksInTushare = Fundamental.getStockBasics(ITradeDateService.sdf.format(lastTradeDate));

		for (StockBasicsData data : stocksInTushare) {
			String code = data.code;
			Date marketTime = null;
			try {
				if (!data.timeToMarket.equalsIgnoreCase("0")) {
					marketTime = ITradeDateService.sdfYMD.parse(data.timeToMarket);
				}
			} catch (ParseException e) {
				log.error(String.format("上市时间转换错误：\n%s", e.getMessage()));
			}
			StockBasics stockObj = stocksInDbMap.get(code);
			if (stockObj == null) {
				StockBasics stockBasics = new StockBasics();
				stockBasics.setCode(code);
				stockBasics.setName(data.name);
				stockBasics.setIndustry(data.industry);
				stockBasics.setArea(data.area);
				if (marketTime != null) {
					stockBasics.setTimeToMarket(marketTime);
				}
				saveList.add(stockBasics);
				log.info(String.format("需要添加新股票：%s", code));
			} else {
				boolean needUpdate = false;
				if (!stockObj.getArea().equals(data.area)) {
					stockObj.setArea(data.area);
					log.info(String.format("股票%s地区需要更新，原地区为%s，现地区为%s。", code, stockObj.getArea(), data.area));
					needUpdate = true;
				}
				if (!stockObj.getIndustry().equals(data.industry)) {
					stockObj.setIndustry(data.industry);
					log.info(String.format("股票%s行业需要更新，原行业为%s，现行业为%s。", code, stockObj.getIndustry(), data.industry));
					needUpdate = true;
				}
				if (stockObj.getTimeToMarket() == null && marketTime != null) {
					stockObj.setTimeToMarket(marketTime);
					log.info(String.format("股票%s上市时间需要更新，原时间为%s，现时间为%s。", code, stockObj.getTimeToMarket(),
							data.timeToMarket));
					needUpdate = true;
				}
				if ( marketTime != null && stockObj.getTimeToMarket().compareTo(marketTime) != 0) {
					stockObj.setTimeToMarket(marketTime);
					log.info(String.format("股票%s上市时间需要更新，原时间为%s，现时间为%s。", code, stockObj.getTimeToMarket(),
							data.timeToMarket));
					needUpdate = true;
				}
				if (needUpdate) {
					saveList.add(stockObj);
					log.info(String.format("需要更新股票：%s", code));
				}
			}
		}
		stockService.saveAll(saveList);
		log.info(String.format("更新股票基本信息已完成，更新数量%s", saveList.size()));
	}
}
