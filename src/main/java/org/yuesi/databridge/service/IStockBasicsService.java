package org.yuesi.databridge.service;

import java.text.ParseException;
import java.util.List;

import org.yuesi.databridge.entity.StockBasics;

public interface IStockBasicsService {
	public long count();

	public void save(StockBasics stockBasics);

	public void saveAll(List<StockBasics> list);

	public void del(String code);

	public List<StockBasics> queryByCode(String code) throws ParseException;

	public List<StockBasics> findAll();

}