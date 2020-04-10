package org.yuesi.databridge.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.yuesi.databridge.entity.TransData;
import org.yuesi.databridge.repository.ITransDataRepo;
import org.yuesi.databridge.service.ITransDataService;

@Service
public class TransDataServiceImpl implements ITransDataService {

	@Resource
	private ITransDataRepo transRepo;
	
	@Override
	public void saveAll(List<TransData> list) {
		transRepo.saveAll(list);
	}

	@Override
	public List<TransData> queryByCode(String code) {
		return transRepo.queryByCode(code);
	}

}
