package com.sulwep7.tarotpointcounterback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableSwagger2
@EnableWebMvc
public class TarotPointCounterBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarotPointCounterBackApplication.class, args);
	}

}
