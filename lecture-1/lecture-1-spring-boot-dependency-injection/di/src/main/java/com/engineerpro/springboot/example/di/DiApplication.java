package com.engineerpro.springboot.example.di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.engineerpro.springboot.example.di.exception.InvalidStatusCodeException;
import com.engineerpro.springboot.example.di.scrapper.Scrapper;
import com.engineerpro.springboot.example.di.service.CrawlerService;

@SpringBootApplication
public class DiApplication {

	public static void main(String[] args) throws InvalidStatusCodeException {
		// container
		ApplicationContext context = SpringApplication.run(DiApplication.class, args);
		displayAllBeans(context);
		// CrawlerService crawlerService = context.getBean(CrawlerService.class);
		// System.out.println("CrawlerService " + crawlerService);
		// crawlerService.showStatusCodeOrRaiseException("https://google.com");
		// System.out.println("Scrapper " + context.getBean(Scrapper.class));
	}

	public static void displayAllBeans(ApplicationContext context) {
		String[] allBeanNames = context.getBeanDefinitionNames();
		System.out.println("All beans:");
		for (String beanName : allBeanNames) {
			System.out.println("bean name=" + beanName);
		}
	}

}
