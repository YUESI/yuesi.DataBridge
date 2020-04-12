package org.yuesi.databridge.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.yuesi.databridge.entity.TradeDate;

public interface ITradeDateService {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");
	
	public long count();

	public void save(TradeDate tradeDate);

	public void saveAll(List<TradeDate> list);

	public void del(Date date);

	public List<TradeDate> queryByTradeDate(String dateStr) throws ParseException;

	public List<TradeDate> queryByTradeDate(String begin, String end) throws ParseException;

	public Date getMaxTradeDate();

	public Date getLastTradeDate(Date date);

	public boolean isWeekend(Date date);

	public boolean isOpen(String dateStr) throws ParseException;

}
