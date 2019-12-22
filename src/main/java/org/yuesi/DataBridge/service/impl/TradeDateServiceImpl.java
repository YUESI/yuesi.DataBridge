package org.yuesi.databridge.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.yuesi.databridge.entity.TradeDate;
import org.yuesi.databridge.repository.ITradeDateRepo;
import org.yuesi.databridge.service.ITradeDateService;

import lombok.NonNull;

@Service
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dateStr);
		return tradeDateRepo.queryByTradeDate(date);
	}

	@Override
	public Date getMaxTradeDate() {
		return tradeDateRepo.maxTradeDate();
	}

}
