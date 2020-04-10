package org.yuesi.databridge.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.yuesi.databridge.entity.TradeDate;

public interface ITradeDateRepo extends JpaRepository<TradeDate, Integer>, CrudRepository<TradeDate, Integer> {

	List<TradeDate> queryByTradeDate(Date tradeDate);
	
	List<TradeDate> queryByTradeDateBetween(Date begin, Date end);
	
	@Query(value = "SELECT max(tradeDate) FROM TradeDate")
	public Date maxTradeDate();
	

	@Query(value = "SELECT max(tradeDate) FROM TradeDate where isopen = true and tradedate <= ?1")
	public Date lastTradeDate(Date date);
	
}
