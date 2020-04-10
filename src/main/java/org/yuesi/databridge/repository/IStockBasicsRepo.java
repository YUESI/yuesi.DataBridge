package org.yuesi.databridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.yuesi.databridge.entity.StockBasics;

public interface IStockBasicsRepo extends JpaRepository<StockBasics, Integer>, CrudRepository<StockBasics, Integer>  {

	List<StockBasics> queryByCode(String code);
}
