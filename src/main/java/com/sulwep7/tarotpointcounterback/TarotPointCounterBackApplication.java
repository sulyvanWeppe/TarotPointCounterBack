package com.sulwep7.tarotpointcounterback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.sulwep7.metricscommon", "com.sulwep7.tarotpointcounterback"})
public class TarotPointCounterBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarotPointCounterBackApplication.class, args);
	}

}
