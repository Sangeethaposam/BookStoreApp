package com.bookstore;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Component
@Aspect
public class UserAspect {
	Logger log = LoggerFactory.getLogger(UserAspect.class);
	
	@After("execution (* com.bookstore.service.BookService.*(..))")
	public void logg() {
		log.info("Admin is currently Working.");
	}
}
