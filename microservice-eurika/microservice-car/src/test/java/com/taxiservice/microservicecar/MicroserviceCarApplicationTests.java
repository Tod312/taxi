package com.taxiservice.microservicecar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.taxiservice.microservicecar.config.TestConfig;

@SpringBootTest
@Import(TestConfig.class)
class MicroserviceCarApplicationTests {

	@Test
	void contextLoads() {
	}

}
