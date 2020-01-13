package org.yuesi.databridge.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.yuesi.databridge.entity.TradeDate;

public interface ITradeDateService {
	public long count();

	public void save(TradeDate tradeDate);
	
	public void saveAll(List<TradeDate> list);

	public void del(Date date);

	public List<TradeDate> queryByTradeDate(String dateStr) throws ParseException;

	public Date getMaxTradeDate();
	
	public Date getLastTradeDate(Date date);
}
