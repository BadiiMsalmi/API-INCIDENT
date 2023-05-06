package com.api.backincdidents;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.api.backincdidents.controller.IncidentController;

import java.io.File;

@SpringBootApplication
public class BackIncdidentsApplication {

	public static void main(String[] args) {
		new File(IncidentController.uploadDirectory).mkdir();
		SpringApplication.run(BackIncdidentsApplication.class, args);
	}

	
}

