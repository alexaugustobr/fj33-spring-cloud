package br.com.caelum.eats.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EatsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatsEurekaApplication.class, args);
	}

}
