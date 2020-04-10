package org.yuesi.databridge.service;

import java.util.List;

import org.yuesi.databridge.entity.TransData;

public interface ITransDataService {

	public void saveAll(List<TransData> list);
	
	public List<TransData> queryByCode(String code);
}
