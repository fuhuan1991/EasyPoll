package com.polling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//The @SpringBootApplication triggers the EnableAutoConfiguration and ComponentScan.
// This is a convenience annotation that is equivalent to declaring @Configuration,
// @EnableAutoConfiguration and @Configuration.
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class PollApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollApplication.class, args);
	}

}
