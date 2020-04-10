package org.yuesi.databridge.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.yuesi.databridge.entity.TradeDate;
import org.yuesi.databridge.repository.ITradeDateRepo;
import org.yuesi.databridge.service.ITradeDateService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradeDateServiceImpl implements ITradeDateService {

	@Resource
	private ITradeDateRepo tradeDateRepo;

	@Override
	@Transactional
	public void save(@NonNull TradeDate tradeDate) {
		tradeDateRepo.save(tradeDate);
	}

	@Override
	public void saveAll(@NonNull List<TradeDate> list) {
		tradeDateRepo.saveAll(list);
	}

	@Override
	public long count() {
		return tradeDateRepo.count();
	}

	@Override
	public void del(@NonNull Date date) {
		TradeDate tradeDate = new TradeDate();
		tradeDate.setTradeDate(date);
		tradeDateRepo.delete(tradeDate);
	}

	@Override
	public List<TradeDate> queryByTradeDate(@NonNull String dateStr) throws ParseException {
		Date date = sdf.parse(dateStr);
		return tradeDateRepo.queryByTradeDate(date);
	}

	@Override
	public List<TradeDate> queryByTradeDate(@NonNull String begin, @NonNull String end) throws ParseException {
		Date beginDate = sdf.parse(begin);
		Date endDate = sdf.parse(end);
		return tradeDateRepo.queryByTradeDateBetween(beginDate, endDate);
	}

	@Override
	public Date getMaxTradeDate() {
		return tradeDateRepo.maxTradeDate();
	}

	@Override
	public Date getLastTradeDate(Date date) {
		return tradeDateRepo.lastTradeDate(date);
	}

	@Override
	public boolean isWeekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	@Override
	public boolean isOpen(String dateStr) throws ParseException {
		Date date = sdf.parse(dateStr);
		List<TradeDate> result = tradeDateRepo.queryByTradeDate(date);
		int size = result.size();
		if(size == 0) {
			log.warn(String.format("%s 该日期不在日期表中，按开盘日处理。", dateStr));
			return true;
		} else {
			TradeDate obj = result.get(0);
			return obj.getIsOpen();
		}
	}

}
