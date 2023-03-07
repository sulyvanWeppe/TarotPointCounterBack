package com.sulwep7.tarotpointcounterback;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@SpringBootTest
class TarotPointCounterBackApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void toto() {
		System.out.println(UUID.randomUUID());
		System.out.println(Timestamp.from(Instant.now()));
	}
}
