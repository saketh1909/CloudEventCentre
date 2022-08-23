package com.cmpe275.term;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAutoConfiguration
public class DemoApplication {
	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("PST"));   // It will set UTC timezone
		//System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
