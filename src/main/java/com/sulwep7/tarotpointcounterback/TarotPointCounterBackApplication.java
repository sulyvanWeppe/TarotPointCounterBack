package com.sulwep7.tarotpointcounterback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TarotPointCounterBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarotPointCounterBackApplication.class, args);
	}

}
