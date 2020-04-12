package org.yuesi.databridge.biz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yuesi.databridge.common.DateUtil;
import org.yuesi.databridge.entity.StockBasics;
import org.yuesi.databridge.entity.TransData;
import org.yuesi.databridge.service.IStockBasicsService;
import org.yuesi.databridge.service.ITradeDateService;
import org.yuesi.databridge.service.ITransDataService;

import lombok.extern.slf4j.Slf4j;
import waditu.tushare.entity.TradeData;
import waditu.tushare.stock.Trading;

@Component
@Slf4j
public class TransDataBiz {

	@Autowired
	private IStockBasicsService stockService;

	@Autowired
	private ITransDataService transService;

	@Autowired
	private ITradeDateService dateService;

	public void addDailyTrans() throws ParseException {
		Date now = new Date();
		String nowStr = ITradeDateService.sdf.format(now);

		if (dateService.isOpen(nowStr)) {
			int pause = (int) (Math.random() * 1000 + 1000);
			List<TradeData> tradeData = Trading.getTodayAll(pause, false);

			List<TransData> saveList = new ArrayList<TransData>();
			for (TradeData trade : tradeData) {
				TransData trans = new TransData();
				trans.setCode(trade.code);
				trans.setTradeDate(ITradeDateService.sdf.parse(nowStr));
				trans.setOpen(new BigDecimal(Double.toString(trade.open)));
				trans.setHigh(new BigDecimal(Double.toString(trade.high)));
				trans.setLow(new BigDecimal(Double.toString(trade.low)));
				trans.setClose(new BigDecimal(Double.toString(trade.close)));
				trans.setVolume(new BigDecimal(Double.toString(trade.volume)));
				trans.setAmount(trade.amount == null ? null : new BigDecimal(Double.toString(trade.amount)));
				trans.setChange(new BigDecimal(Double.toString(trade.price_change)));
				trans.setPChange(new BigDecimal(Double.toString(trade.p_change)));
				saveList.add(trans);
			}
			transService.saveAll(saveList);
		} else {
			log.info(String.format("%s 该日期不是开盘日，无需同步。", ITradeDateService.sdf.format(now)));
		}
	}

	/**
	 * 每月数据修正
	 */
	public void checkWeekly() {
		List<TransData> saveList = new ArrayList<TransData>();
		List<StockBasics> stocks = stockService.findAll();
		for (StockBasics stock : stocks) {
			String code = stock.getCode();
			log.info(String.format("%s开始处理本周历史数据", code));
			List<TradeData> tradeData = new ArrayList<TradeData>();
			try {
				String begin = DateUtil.aweekBefore();
				String end = DateUtil.now();
				tradeData = Trading.getHistData(code, begin, end, "D", 3, (int) (Math.random() * 1000 + 500));
			} catch (Exception e) {
				log.warn(String.format("%s 下载出错：%s", code, e.getMessage()));
			}
			for (TradeData trade : tradeData) {
				TransData trans = new TransData();
				trans.setCode(trade.code);
				trans.setTradeDate(trade.getDate());
				trans.setOpen(new BigDecimal(Double.toString(trade.open)));
				trans.setHigh(new BigDecimal(Double.toString(trade.high)));
				trans.setLow(new BigDecimal(Double.toString(trade.low)));
				trans.setClose(new BigDecimal(Double.toString(trade.close)));
				trans.setVolume(new BigDecimal(Double.toString(trade.volume)));
				trans.setAmount(trade.amount == null ? null : new BigDecimal(Double.toString(trade.amount)));
				trans.setChange(new BigDecimal(Double.toString(trade.price_change)));
				trans.setPChange(new BigDecimal(Double.toString(trade.p_change)));
				saveList.add(trans);
			}
			transService.saveAll(saveList);
			saveList.clear();
			log.info(String.format("%s处理本周历史数据结束", code));
		}
	}

}
