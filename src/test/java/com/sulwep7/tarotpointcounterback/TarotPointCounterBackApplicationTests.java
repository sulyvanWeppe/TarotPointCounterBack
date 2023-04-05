package com.sulwep7.tarotpointcounterback;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootTest
@Slf4j
@ComponentScan(basePackages = {"com.sulwep7.metricscommon", "com.sulwep7.tarotpointcounterback"})
class TarotPointCounterBackApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		Assert.assertNotNull(applicationContext);
	}
}
