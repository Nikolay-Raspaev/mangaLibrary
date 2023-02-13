package com.LabWork.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@CrossOrigin
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@CrossOrigin
	@GetMapping("/sum")
	public String Sum(@RequestParam(required = false, defaultValue = "0") double first,
					  @RequestParam(required = false, defaultValue = "0") double second) {
		return Double.toString(first + second);
	}

	@CrossOrigin
	@GetMapping("/difference")
	public String Difference(@RequestParam(required = false, defaultValue = "0") double first,
					  @RequestParam(required = false, defaultValue = "0") double second) {
		return Double.toString(first - second);
	}

	@CrossOrigin
	@GetMapping("/multiplication")
	public String Multiplication(@RequestParam(required = false, defaultValue = "1") double first,
					   @RequestParam(required = false, defaultValue = "1") double second) {
		return Double.toString(first * second);
	}

	@CrossOrigin
	@GetMapping("/division")
	public String Division(@RequestParam(required = false, defaultValue = "1") double first,
					  @RequestParam(required = false, defaultValue = "1") double second) {
		if(second == 0)
		{
			return null;
		}
		return Double.toString(first/second);
	}
}
