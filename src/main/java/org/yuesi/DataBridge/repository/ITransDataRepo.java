package org.yuesi.databridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.yuesi.databridge.entity.TransData;

public interface ITransDataRepo extends JpaRepository<TransData, Integer>, CrudRepository<TransData, Integer> {
	List<TransData> queryByCode(String code);
}
