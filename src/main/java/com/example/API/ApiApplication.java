package com.example.API;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}
//@Bean(name = "multipartResolver")
//public CommonsMultipartResolver filterMultipartResolver() {
//	CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//	resolver.setDefaultEncoding("utf-8");
//	resolver.setMaxUploadSize(10000);
//	return resolver;
//}
}
