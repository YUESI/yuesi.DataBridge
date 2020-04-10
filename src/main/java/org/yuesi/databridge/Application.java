package org.yuesi.databridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.yuesi.databridge.biz.TransDataBiz;


@SpringBootApplication
@EnableScheduling
public class Application implements ApplicationRunner {
	
	@Autowired
	TransDataBiz transBiz;
	

	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// transBiz.addDailyTrans();
	}
}
