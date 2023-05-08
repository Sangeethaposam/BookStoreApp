package com.bookstore;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class ConfigureClass {
	@Bean
	public SimpleMailMessage mailMessage() {
		return new SimpleMailMessage();
	}
	@Bean
	public ModelMapper model() {
		return new ModelMapper();
	}
}
