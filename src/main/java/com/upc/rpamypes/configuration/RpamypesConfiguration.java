package com.upc.rpamypes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.upc.rpamypes.web.conversion.DateFormatter;

@Configuration
public class RpamypesConfiguration {
	@Bean
	public DateFormatter dateFormatter() {
		return new DateFormatter();
	}

}
