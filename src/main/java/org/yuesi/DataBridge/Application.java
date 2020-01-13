package org.yuesi.databridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.yuesi.databridge.scheduler.StockBasicsTask;

@SpringBootApplication
@EnableScheduling
public class Application implements ApplicationRunner {
	
	@Autowired
	StockBasicsTask task;

	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		task.updateStockBasicsData();
	}
}
