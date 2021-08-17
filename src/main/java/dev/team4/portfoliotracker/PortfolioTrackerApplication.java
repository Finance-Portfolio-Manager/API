package dev.team4.portfoliotracker;

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.services.UserDetailsService;
//import dev.team4.portfoliotracker.util.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class PortfolioTrackerApplication {

//	@Autowired
//	UpdateUtil updateUtil;

//	private static UserDetailsService userDetailsService;

	public static void main(String[] args) {


		SpringApplication.run(PortfolioTrackerApplication.class, args);

	}





	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("dev.team4.portfoliotracker.controllers"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("http://localhost:8082");
			}
		};
	}

}
