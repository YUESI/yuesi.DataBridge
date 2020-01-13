package org.yuesi.databridge.service.impl;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.yuesi.databridge.entity.StockBasics;
import org.yuesi.databridge.repository.IStockBasicsRepo;
import org.yuesi.databridge.service.IStockBasicsService;

import lombok.NonNull;

@Service
public class StockBasicsServiceImpl implements IStockBasicsService {

	@Resource
	private IStockBasicsRepo stockBasicsRepo;

	@Override
	public long count() {
		return stockBasicsRepo.count();
	}

	@Override
	public void save(@NonNull StockBasics stockBasics) {
		stockBasicsRepo.save(stockBasics);
	}

	@Override
	public void saveAll(@NonNull List<StockBasics> list) {
		stockBasicsRepo.saveAll(list);
	}

	@Override
	public void del(String code) {
		StockBasics stockBasics = new StockBasics();
		stockBasics.setCode(code);
		stockBasicsRepo.delete(stockBasics);

	}

	@Override
	public List<StockBasics> queryByCode(String code) throws ParseException {
		return stockBasicsRepo.queryByCode(code);
	}

}
