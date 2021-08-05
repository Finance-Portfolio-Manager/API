package dev.team4.portfoliotracker;

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import dev.team4.portfoliotracker.services.UserDetailsService;
import dev.team4.portfoliotracker.util.UpdateUtil;
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
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class PortfolioTrackerApplication {

	@Autowired
	UpdateUtil updateUtil;

//	private static UserDetailsService userDetailsService;

	public static void main(String[] args) {

		SpringApplication.run(PortfolioTrackerApplication.class, args);

//		Stock stock = null;
//		try {
//			stock = YahooFinance.get("AAPL");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		BigDecimal price = stock.getQuote().getPrice();
//		BigDecimal change = stock.getQuote().getChangeInPercent();
//		BigDecimal peg = stock.getStats().getPeg();
//		BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
//
//		stock.print();
//		System.out.println("price is " + price);
//		System.out.println("change is " + change);
//		System.out.println("peg is" + peg);
//		System.out.println("dividend is " + dividend);
//
//		Calendar from = Calendar.getInstance();
//		Calendar to = Calendar.getInstance();
//		from.add(Calendar.YEAR, -5); // from 5 years ago
//
//		try {
//			Stock google = YahooFinance.get("GOOG", from, to, Interval.WEEKLY);
//			System.out.println(google);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		//updateUtil.update();
//
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("send email at: " + new java.util.Date());
//			}
//		}, 0, 1000);
	}

//	@Bean
//	public void helper(){
//		userDetailsService.checkAllUser();
//	}



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
