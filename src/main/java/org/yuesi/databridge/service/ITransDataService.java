package org.yuesi.databridge.service;

import java.text.ParseException;
import java.util.List;

import org.yuesi.databridge.entity.TransData;

public interface ITransDataService {

	public void saveAll(List<TransData> list);
	
	public List<TransData> queryByCode(String code);
	
	public List<TransData> queryByCodeAndTradeDateBetween(String code, String begin, String end) throws ParseException;
}
