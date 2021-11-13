package com.takakaProducer.takakaProducer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TakakaProducerApplication {
	
	  @RequestMapping("/")
	  public String home() {
	    return "Hello Docker World hahaha";
	  }

	public static void main(String[] args) {
		SpringApplication.run(TakakaProducerApplication.class, args);
	}
	


}
