package com.engineerpro.di2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Di2Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Di2Application.class, args);

		Car car = context.getBean(Car.class);
		car.open();
		car.start();
		// System.out.println("CrawlerService " + crawlerService);
	}

}
