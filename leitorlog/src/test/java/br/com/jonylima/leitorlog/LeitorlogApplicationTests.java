package br.com.jonylima.leitorlog;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class LeitorlogApplicationTests {

	@Test
	void contextLoads() {
		 DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		 TemporalAccessor parse = FORMATTER.parse("2021-01-01T01:00");
	}

}
