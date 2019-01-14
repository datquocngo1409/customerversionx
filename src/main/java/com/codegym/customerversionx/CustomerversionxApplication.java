package com.codegym.customerversionx;

import com.codegym.customerversionx.service.CustomerService;
import com.codegym.customerversionx.service.ProvinceService;
import com.codegym.customerversionx.service.impl.CustomerServiceImpl;
import com.codegym.customerversionx.service.impl.ProvinceServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerversionxApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerversionxApplication.class, args);
	}

	@Bean
	public CustomerService customerService(){
		return new CustomerServiceImpl();
	}

	@Bean
	public ProvinceService provinceService(){
		return new ProvinceServiceImpl();
	}

}

